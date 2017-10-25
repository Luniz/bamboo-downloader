
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.ui;

//~--- non-JDK imports --------------------------------------------------------

import android.text.Html;

import android.view.LayoutInflater;

import kz.gregbiv.bamboo.R;
import kz.gregbiv.bamboo.core.Build;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

public class BuildListAdapter extends AlternatingColorListAdapter<Build> {
    LayoutInflater layoutInflater;

    /**
     * @param inflater
     * @param items
     */
    public BuildListAdapter(final LayoutInflater inflater, final List<Build> items) {
        super(R.layout.build_list_item, inflater, items);
        layoutInflater = inflater;
    }

    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public BuildListAdapter(final LayoutInflater inflater, final List<Build> items, final boolean selectable) {
        super(R.layout.build_list_item, inflater, items, selectable);
        layoutInflater = inflater;
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_key, R.id.tv_summary, R.id.tv_time };
    }

    @Override
    protected void update(final int position, final Build item) {
        super.update(position, item);
        setText(0, "#" + String.valueOf(item.getBuildNumber()));
        setText(1, Html.fromHtml(item.getReasonSummary()).toString());
        setText(2, item.getBuildRelativeTime());

        if (item.isSuccessful()) {
            setColor(0, layoutInflater.getContext().getResources().getColor(R.color.build_successful));
        } else {
            setColor(0, layoutInflater.getContext().getResources().getColor(R.color.build_failed));
        }
    }
}
