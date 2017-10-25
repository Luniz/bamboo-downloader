
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.ui;

//~--- non-JDK imports --------------------------------------------------------

import android.content.res.Resources;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import kz.gregbiv.bamboo.R;

/**
 * Plan Pager adapter
 */
public class PlanPagerAdapter extends FragmentPagerAdapter {
    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public PlanPagerAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment result;

        switch (position) {
        case 0 :
            result = new BranchListFragment();

            break;

        case 1 :
            result = new BuildListFragment();

            break;

        default :
            result = null;

            break;
        }

        if (result != null) {
            result.setArguments(new Bundle());    // TODO do we need this?
        }

        return result;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
        case 0 :
            return resources.getString(R.string.page_branches);

        case 1 :
            return resources.getString(R.string.page_last_builds);

        default :
            return null;
        }
    }
}
