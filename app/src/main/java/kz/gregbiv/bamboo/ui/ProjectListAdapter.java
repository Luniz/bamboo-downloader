
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.ui;

//~--- non-JDK imports --------------------------------------------------------

import android.view.LayoutInflater;

import kz.gregbiv.bamboo.R;
import kz.gregbiv.bamboo.core.Project;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

public class ProjectListAdapter extends AlternatingColorListAdapter<Project> {

    /**
     * @param inflater
     * @param items
     */
    public ProjectListAdapter(final LayoutInflater inflater, final List<Project> items) {
        super(R.layout.project_list_item, inflater, items);
    }

    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public ProjectListAdapter(final LayoutInflater inflater, final List<Project> items, final boolean selectable) {
        super(R.layout.project_list_item, inflater, items, selectable);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_title, R.id.tv_key };
    }

    @Override
    protected void update(final int position, final Project item) {
        super.update(position, item);
        setText(0, item.getName());
        setText(1, item.getKey());
    }
}
