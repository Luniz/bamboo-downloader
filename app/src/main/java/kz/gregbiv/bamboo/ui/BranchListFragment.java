
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.ui;

//~--- non-JDK imports --------------------------------------------------------

import android.accounts.OperationCanceledException;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.support.v4.content.Loader;

import android.view.View;

import android.widget.ListView;

import kz.gregbiv.bamboo.BootstrapApplication;
import kz.gregbiv.bamboo.BootstrapServiceProvider;
import kz.gregbiv.bamboo.R;
import kz.gregbiv.bamboo.core.Branch;
import kz.gregbiv.bamboo.core.Plan;
import kz.gregbiv.bamboo.util.SingleTypeAdapter;

import static kz.gregbiv.bamboo.core.Constants.Extra.BRANCH_ITEM;
import static kz.gregbiv.bamboo.core.Constants.Extra.PLAN_ITEM;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class BranchListFragment extends ItemListFragment<Branch> {
    @Inject
    protected BootstrapServiceProvider serviceProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText(R.string.no_branches);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);
        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);
        getListAdapter().addHeader(activity.getLayoutInflater().inflate(R.layout.branch_list_item_labels, null));
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);
        super.onDestroyView();
    }

    @Override
    public Loader<List<Branch>> onCreateLoader(int id, Bundle args) {
        final List<Branch> initialItems = items;

        return new ThrowableLoader<List<Branch>>(getActivity(), items) {
            @Override
            public List<Branch> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        Plan plan = (Plan) getActivity().getIntent().getExtras().getSerializable(PLAN_ITEM);

                        if (plan != null) {
                            return serviceProvider.getService(getActivity()).getBranchesForPlan(plan.getKey());
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
    protected SingleTypeAdapter<Branch> createAdapter(List<Branch> items) {
        return new BranchListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), BranchActivity.class);
        Branch branch = ((Branch) l.getItemAtPosition(position));
        Plan   plan   = (Plan) getActivity().getIntent().getExtras().getSerializable(PLAN_ITEM);

        intent.putExtra(BRANCH_ITEM, branch);
        intent.putExtra(PLAN_ITEM, plan);
        startActivity(intent);
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_branches;
    }
}
