
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
import kz.gregbiv.bamboo.core.Plan;

import static kz.gregbiv.bamboo.core.Constants.Extra.PLAN_ITEM;

public class PlanActivity extends BootstrapActivity {
    private Plan planItem;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        if ((getIntent() != null) && (getIntent().getExtras() != null)) {
            planItem = (Plan) getIntent().getExtras().getSerializable(PLAN_ITEM);
        }

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        if (planItem != null) {
            setTitle(planItem.getKey());
        }

        initScreen();
    }

    private void initScreen() {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.container, new PlanCarouselFragment()).commit();
    }
}
