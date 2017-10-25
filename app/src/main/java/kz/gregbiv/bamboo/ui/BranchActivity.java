
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.ui;

//~--- non-JDK imports --------------------------------------------------------

import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;

import kz.gregbiv.bamboo.R;
import kz.gregbiv.bamboo.core.Branch;
import kz.gregbiv.bamboo.core.Plan;

import static kz.gregbiv.bamboo.core.Constants.Extra.BRANCH_ITEM;
import static kz.gregbiv.bamboo.core.Constants.Extra.PLAN_ITEM;

public class BranchActivity extends BootstrapActivity {
    private Branch branchItem;
    private Plan   planItem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            branchItem = (Branch) getIntent().getExtras().getSerializable(BRANCH_ITEM);
            planItem   = (Plan) getIntent().getExtras().getSerializable(PLAN_ITEM);
        }

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        setTitle(branchItem.getKey());
        initScreen();
    }

    private void initScreen() {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.container, new BuildListFragment()).commit();
    }
}
