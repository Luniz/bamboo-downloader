
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.ui;

//~--- non-JDK imports --------------------------------------------------------

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.OperationCanceledException;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;

import android.net.Uri;

import android.os.Bundle;

import android.provider.Browser;

import android.support.v4.content.Loader;

import android.view.View;

import android.widget.ListView;

import kz.gregbiv.bamboo.BootstrapApplication;
import kz.gregbiv.bamboo.BootstrapServiceProvider;
import kz.gregbiv.bamboo.R;
import kz.gregbiv.bamboo.authenticator.ApiKeyProvider;
import kz.gregbiv.bamboo.core.Artifact;
import kz.gregbiv.bamboo.core.Branch;
import kz.gregbiv.bamboo.core.Build;
import kz.gregbiv.bamboo.core.Constants;
import kz.gregbiv.bamboo.core.Plan;
import kz.gregbiv.bamboo.util.AuthUtils;
import kz.gregbiv.bamboo.util.SingleTypeAdapter;
import kz.gregbiv.bamboo.util.Toaster;

import timber.log.Timber;

import static kz.gregbiv.bamboo.core.Constants.Extra.BRANCH_ITEM;
import static kz.gregbiv.bamboo.core.Constants.Extra.PLAN_ITEM;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class BuildListFragment extends ItemListFragment<Build> {
    @Inject
    protected BootstrapServiceProvider serviceProvider;
    @Inject
    protected ApiKeyProvider           apiKeyProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText(R.string.no_builds);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);
        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);
        getListAdapter().addHeader(activity.getLayoutInflater().inflate(R.layout.build_list_item_labels, null));
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);
        super.onDestroyView();
    }

    @Override
    public Loader<List<Build>> onCreateLoader(int id, Bundle args) {
        final List<Build> initialItems = items;

        return new ThrowableLoader<List<Build>>(getActivity(), items) {
            @Override
            public List<Build> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        Plan   plan   = (Plan) getActivity().getIntent().getExtras().getSerializable(PLAN_ITEM);
                        Branch branch = (Branch) getActivity().getIntent().getExtras().getSerializable(BRANCH_ITEM);

                        if ((null != branch) && (null != plan)) {
                            return serviceProvider.getService(getActivity()).getBuildsForPlanAndBranch(plan.getKey(),
                                                              branch.getShortName());
                        } else if (null != plan) {
                            return serviceProvider.getService(getActivity()).getBuildsForPlan(plan.getKey());
                        } else {
                            return Collections.emptyList();
                        }
                    } else {
                        return Collections.emptyList();
                    }
                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();

                    if (activity != null) {
                        activity.finish();
                    }

                    return initialItems;
                }
            }
        };
    }

    @Override
    protected SingleTypeAdapter<Build> createAdapter(List<Build> items) {
        return new BuildListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        final Build build = ((Build) l.getItemAtPosition(position));

        // Will check, if we have artifacts to download
        if ((null != build.getArtifactResponse()) && (build.getArtifactResponse().getArtifacts().size() > 0)) {
            final AlertDialog.Builder menuAlert = new AlertDialog.Builder(getActivity());
            String[]                  artifacts = new String[build.getArtifactResponse().getSize()];
            int                       i         = 0;

            // Parse each artifact
            for (final Artifact artifact : build.getArtifactResponse().getArtifacts()) {
                artifacts[i] = artifact.getName();
                i++;
            }

            menuAlert.setTitle(R.string.title_artifacts);
            menuAlert.setItems(artifacts, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    final AccountManager accountManager = AccountManager.get(getContext());
                    final Artifact       artifact       = build.getArtifactResponse().getArtifacts().get(item);

                    // If artifact has Link, trying to get href and auth headers
                    if (null != artifact.getLink()) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(artifact.getLink().getHref()));

                        try {
                            if (accountManager != null) {
                                final Account[] accounts =
                                    accountManager.getAccountsByType(Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);

                                if (accounts.length > 0) {
                                    AccountManagerFuture<Bundle> accountManagerFuture =
                                        accountManager.getAuthToken(accounts[0], Constants.Auth.AUTHTOKEN_TYPE, false,
                                                                    null, null);
                                    Bundle authTokenBundle = accountManagerFuture.getResult();
                                    Object authToken       = authTokenBundle.get(AccountManager.KEY_AUTHTOKEN);
                                    Bundle bundle          = new Bundle();

                                    if (authToken != null) {
                                        bundle.putString("Authorization",
                                                         AuthUtils.getAuthHeader(authToken.toString()));
                                        browserIntent.putExtra(Browser.EXTRA_HEADERS, bundle);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Timber.e(e.getCause(), "Add headers failed.");
                        }

                        startActivity(browserIntent);
                    }
                }
            });

            AlertDialog menuDrop = menuAlert.create();

            menuDrop.show();
        } else {
            Toaster.showLong(getActivity(), R.string.message_no_build_artifacts_to_download);
        }
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_builds;
    }
}
