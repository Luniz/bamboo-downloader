
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.authenticator;

//~--- non-JDK imports --------------------------------------------------------

import android.accounts.Account;
import android.accounts.AccountManager;

import android.app.Dialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import kz.gregbiv.bamboo.BootstrapApplication;
import kz.gregbiv.bamboo.R;
import kz.gregbiv.bamboo.R.id;
import kz.gregbiv.bamboo.R.string;
import kz.gregbiv.bamboo.core.BootstrapService;
import kz.gregbiv.bamboo.core.Constants;
import kz.gregbiv.bamboo.core.User;
import kz.gregbiv.bamboo.events.UnAuthorizedErrorEvent;
import kz.gregbiv.bamboo.ui.TextWatcherAdapter;
import kz.gregbiv.bamboo.util.AuthUtils;
import kz.gregbiv.bamboo.util.SafeAsyncTask;
import kz.gregbiv.bamboo.util.Toaster;

import retrofit.RetrofitError;

import timber.log.Timber;

import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

//~--- JDK imports ------------------------------------------------------------

import javax.inject.Inject;

/**
 * Activity to authenticate the user against an API (example API on Parse.com)
 */
public class BootstrapAuthenticatorActivity extends ActionBarAccountAuthenticatorActivity {

    /**
     * PARAM_CONFIRM_CREDENTIALS
     */
    public static final String PARAM_CONFIRM_CREDENTIALS = "confirmCredentials";

    /**
     * PARAM_PASSWORD
     */
    public static final String PARAM_PASSWORD = "password";

    /**
     * PARAM_USERNAME
     */
    public static final String PARAM_USERNAME = "username";

    /**
     * PARAM_AUTHTOKEN_TYPE
     */
    public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";
    private final TextWatcher  watcher              = validationTextWatcher();

    /**
     * If set we are just checking that the user knows their credentials; this
     * doesn't cause the user's password to be changed on the device.
     */
    private Boolean confirmCredentials = false;

    /**
     * Was the original caller asking for an entirely new account?
     */
    protected boolean              requestNewAccount = false;
    @Inject
    protected SharedPreferences    sharedPreferences;
    private AccountManager         accountManager;
    @Inject
    BootstrapService               bootstrapService;
    @Inject
    Bus                            bus;
    @BindView(id.et_username)
    protected EditText             usernameText;
    @BindView(id.et_password)
    protected EditText             passwordText;
    @BindView(id.b_signin)
    protected Button               signInButton;
    private SafeAsyncTask<Boolean> authenticationTask;
    private String                 authToken;
    private String                 authTokenType;
    private String                 username;
    private String                 password;
    private User                   user;

    /**
     * In this instance the token is simply the sessionId returned from Parse.com. This could be a
     * oauth token or some other type of timed token that expires/etc. We're just using the parse.com
     * sessionId to prove the example of how to utilize a token.
     */
    private String token;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        BootstrapApplication.component().inject(this);
        accountManager = AccountManager.get(this);

        final Intent intent = getIntent();

        username           = intent.getStringExtra(PARAM_USERNAME);
        authTokenType      = intent.getStringExtra(PARAM_AUTHTOKEN_TYPE);
        confirmCredentials = intent.getBooleanExtra(PARAM_CONFIRM_CREDENTIALS, false);
        requestNewAccount  = username == null;
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        passwordText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(final View v, final int keyCode, final KeyEvent event) {
                if ((event != null) && (ACTION_DOWN == event.getAction()) && (keyCode == KEYCODE_ENTER)
                        && signInButton.isEnabled()) {
                    handleLogin(signInButton);

                    return true;
                }

                return false;
            }
        });
        passwordText.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                if ((actionId == IME_ACTION_DONE) && signInButton.isEnabled()) {
                    handleLogin(signInButton);

                    return true;
                }

                return false;
            }
        });
        usernameText.addTextChangedListener(watcher);
        passwordText.addTextChangedListener(watcher);

        final TextView signUpText = (TextView) findViewById(id.tv_signup);

        signUpText.setMovementMethod(LinkMovementMethod.getInstance());
        signUpText.setText(Html.fromHtml(getString(string.signup_link)));
    }

    private TextWatcher validationTextWatcher() {
        return new TextWatcherAdapter() {
            public void afterTextChanged(final Editable gitDirEditText) {
                updateUIWithValidation();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
        updateUIWithValidation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    private void updateUIWithValidation() {
        final boolean populated = populated(usernameText) && populated(passwordText);

        signInButton.setEnabled(populated);
    }

    private boolean populated(final EditText editText) {
        return editText.length() > 0;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        final ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage(getText(string.message_signing_in));
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(final DialogInterface dialog) {
                if (authenticationTask != null) {
                    authenticationTask.cancel(true);
                }
            }
        });

        return dialog;
    }

    @Subscribe
    public void onUnAuthorizedErrorEvent(UnAuthorizedErrorEvent unAuthorizedErrorEvent) {

        // Could not authorize for some reason.
        Toaster.showLong(BootstrapAuthenticatorActivity.this, R.string.message_bad_credentials);
    }

    /**
     * Handles onClick event on the Submit button. Sends username/password to
     * the server for authentication.
     * <p/>
     * Specified by android:onClick="handleLogin" in the layout xml
     *
     * @param view
     */
    public void handleLogin(final View view) {
        if (authenticationTask != null) {
            return;
        }

        if (requestNewAccount) {
            username = usernameText.getText().toString();
        }

        password = passwordText.getText().toString();
        showProgress();
        authenticationTask = new SafeAsyncTask<Boolean>() {
            public Boolean call() throws Exception {
                user  = bootstrapService.authenticate(username, password);
                token = AuthUtils.getBase64Credentials(username, password);

                return true;
            }
            @Override
            protected void onException(final Exception e) throws RuntimeException {

                // Retrofit Errors are handled inside of the {
                if (!(e instanceof RetrofitError)) {
                    final Throwable cause = (e.getCause() != null)
                                            ? e.getCause()
                                            : e;

                    Toaster.showLong(BootstrapAuthenticatorActivity.this, cause.getMessage());
                } else {
                    final RetrofitError cause = ((RetrofitError) e);
                    String              errorDescription;

                    if (cause.isNetworkError()) {
                        errorDescription = getString(R.string.message_network_error);
                    } else {
                        if (cause.getResponse() == null) {
                            errorDescription = getString(R.string.message_error_no_response);
                        } else {
                            try {
                                errorDescription = getString(R.string.message_error_network_http_error,
                                                             cause.getResponse().getStatus());
                            } catch (Exception ex2) {
                                Timber.e(ex2.getCause(), "Login failed.");
                                errorDescription = getString(R.string.message_error_unknown);
                            }
                        }
                    }

                    Toaster.showLong(BootstrapAuthenticatorActivity.this, errorDescription);
                }
            }
            @Override
            public void onSuccess(final Boolean authSuccess) {
                onAuthenticationResult(authSuccess);
            }
            @Override
            protected void onFinally() throws RuntimeException {
                hideProgress();
                authenticationTask = null;
            }
        };
        authenticationTask.execute();
    }

    /**
     * Called when response is received from the server for confirm credentials
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller.
     *
     * @param result
     */
    protected void finishConfirmCredentials(final boolean result) {
        final Account account = new Account(username, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);

        accountManager.setPassword(account, password);

        final Intent intent = new Intent();

        intent.putExtra(KEY_BOOLEAN_RESULT, result);
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Called when response is received from the server for authentication
     * request. See onAuthenticationResult(). Sets the
     * AccountAuthenticatorResult which is sent back to the caller. Also sets
     * the authToken in AccountManager for this account.
     */
    protected void finishLogin() {
        final Account account = new Account(username, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);

        authToken = token;

        if (requestNewAccount) {
            accountManager.addAccountExplicitly(account, password, null);
            accountManager.setAuthToken(account, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE, authToken);
        } else {
            accountManager.setPassword(account, password);
        }

        final Intent intent = new Intent();

        intent.putExtra(KEY_ACCOUNT_NAME, username);
        intent.putExtra(KEY_ACCOUNT_TYPE, Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);

        if ((authTokenType != null) && authTokenType.equals(Constants.Auth.AUTHTOKEN_TYPE)) {
            intent.putExtra(KEY_AUTHTOKEN, authToken);
        }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Hide progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void hideProgress() {
        dismissDialog(0);
    }

    /**
     * Show progress dialog
     */
    @SuppressWarnings("deprecation")
    protected void showProgress() {
        showDialog(0);
    }

    /**
     * Called when the authentication process completes (see attemptLogin()).
     *
     * @param result
     */
    public void onAuthenticationResult(final boolean result) {
        if (result) {
            sharedPreferences.edit().putString(Constants.Auth.USERNAME, user.getFullName()).apply();

            if (!confirmCredentials) {
                finishLogin();
            } else {
                finishConfirmCredentials(true);
            }
        } else {
            Timber.d("onAuthenticationResult: failed to authenticate");

            if (requestNewAccount) {
                Toaster.showLong(BootstrapAuthenticatorActivity.this, R.string.message_auth_failed_new_account);
            } else {
                Toaster.showLong(BootstrapAuthenticatorActivity.this, R.string.message_auth_failed);
            }
        }
    }
}
