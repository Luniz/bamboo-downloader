
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
import kz.gregbiv.bamboo.core.Plan;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

public class PlanListAdapter extends AlternatingColorListAdapter<Plan> {

    /**
     * @param inflater
     * @param items
     */
    public PlanListAdapter(final LayoutInflater inflater, final List<Plan> items) {
        super(R.layout.project_list_item, inflater, items);
    }

    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public PlanListAdapter(final LayoutInflater inflater, final List<Plan> items, final boolean selectable) {
        super(R.layout.plan_list_item, inflater, items, selectable);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_title, R.id.tv_key };
    }

    @Override
    protected void update(final int position, final Plan item) {
        super.update(position, item);
        setText(0, item.getName());
        setText(1, item.getKey());
    }
}
