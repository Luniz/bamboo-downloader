
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
import kz.gregbiv.bamboo.core.Plan;
import kz.gregbiv.bamboo.util.SingleTypeAdapter;

import static kz.gregbiv.bamboo.core.Constants.Extra.PLAN_ITEM;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class PlanListFragment extends ItemListFragment<Plan> {
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
        setEmptyText(R.string.no_plans);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);
        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);
        getListAdapter().addHeader(activity.getLayoutInflater().inflate(R.layout.plan_list_item_labels, null));
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);
        super.onDestroyView();
    }

    @Override
    public Loader<List<Plan>> onCreateLoader(int id, Bundle args) {
        final List<Plan> initialItems = items;

        return new ThrowableLoader<List<Plan>>(getActivity(), items) {
            @Override
            public List<Plan> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getPlans();
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
    protected SingleTypeAdapter<Plan> createAdapter(List<Plan> items) {
        return new PlanListAdapter(getActivity().getLayoutInflater(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Plan plan = ((Plan) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), PlanActivity.class).putExtra(PLAN_ITEM, plan));
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_plans;
    }
}
