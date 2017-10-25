
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

import android.os.Bundle;

import android.support.v4.content.Loader;

import android.widget.ListView;

import kz.gregbiv.bamboo.BootstrapApplication;
import kz.gregbiv.bamboo.BootstrapServiceProvider;
import kz.gregbiv.bamboo.R;
import kz.gregbiv.bamboo.core.Project;
import kz.gregbiv.bamboo.util.SingleTypeAdapter;

//~--- JDK imports ------------------------------------------------------------

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class ProjectListFragment extends ItemListFragment<Project> {
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
        setEmptyText(R.string.no_projects);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);
        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);
        getListAdapter().addHeader(activity.getLayoutInflater().inflate(R.layout.project_list_item_labels, null));
    }

    @Override
    public void onDestroyView() {
        setListAdapter(null);
        super.onDestroyView();
    }

    @Override
    public Loader<List<Project>> onCreateLoader(int id, Bundle args) {
        final List<Project> initialItems = items;

        return new ThrowableLoader<List<Project>>(getActivity(), items) {
            @Override
            public List<Project> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        return serviceProvider.getService(getActivity()).getProjects();
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
    protected SingleTypeAdapter<Project> createAdapter(List<Project> items) {
        return new ProjectListAdapter(getActivity().getLayoutInflater(), items);
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_projects;
    }
}
