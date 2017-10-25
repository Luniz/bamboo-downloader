
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
import android.accounts.AccountManagerFuture;

import android.content.Context;

import kz.gregbiv.bamboo.core.Constants;
import kz.gregbiv.bamboo.util.SafeAsyncTask;

import timber.log.Timber;

//~--- JDK imports ------------------------------------------------------------

import javax.inject.Inject;

/**
 * Class used for logging a user out.
 */
public class LogoutServiceImpl implements LogoutService {
    protected final Context        context;
    protected final AccountManager accountManager;

    @Inject
    public LogoutServiceImpl(final Context context, final AccountManager accountManager) {
        this.context        = context;
        this.accountManager = accountManager;
    }

    @Override
    public void logout(final Runnable onSuccess) {
        new LogoutTask(context, onSuccess).execute();
    }

    private static class LogoutTask extends SafeAsyncTask<Boolean> {
        private final Context  taskContext;
        private final Runnable onSuccess;

        protected LogoutTask(final Context context, final Runnable onSuccess) {
            this.taskContext = context;
            this.onSuccess   = onSuccess;
        }

        @Override
        public Boolean call() throws Exception {
            final AccountManager accountManagerWithContext = AccountManager.get(taskContext);

            if (accountManagerWithContext != null) {
                final Account[] accounts =
                    accountManagerWithContext.getAccountsByType(Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);

                if (accounts.length > 0) {
                    final AccountManagerFuture<Boolean> removeAccountFuture =
                        accountManagerWithContext.removeAccount(accounts[0], null, null);

                    return removeAccountFuture.getResult();
                }
            } else {
                Timber.w("accountManagerWithContext is null");
            }

            return false;
        }

        @Override
        protected void onSuccess(final Boolean accountWasRemoved) throws Exception {
            super.onSuccess(accountWasRemoved);
            Timber.d("Logout succeeded: %s", accountWasRemoved);
            onSuccess.run();
        }

        @Override
        protected void onException(final Exception e) throws RuntimeException {
            super.onException(e);
            Timber.e(e.getCause(), "Logout failed.");
        }
    }
}
