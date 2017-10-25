
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
import kz.gregbiv.bamboo.core.Branch;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

public class BranchListAdapter extends AlternatingColorListAdapter<Branch> {

    /**
     * @param inflater
     * @param items
     */
    public BranchListAdapter(final LayoutInflater inflater, final List<Branch> items) {
        super(R.layout.branch_list_item, inflater, items);
    }

    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public BranchListAdapter(final LayoutInflater inflater, final List<Branch> items, final boolean selectable) {
        super(R.layout.branch_list_item, inflater, items, selectable);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_title, R.id.tv_key };
    }

    @Override
    protected void update(final int position, final Branch item) {
        super.update(position, item);
        setText(0, item.getShortName());
        setText(1, item.getShortKey());
    }
}
