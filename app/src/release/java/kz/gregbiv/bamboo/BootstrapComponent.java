
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo;

//~--- non-JDK imports --------------------------------------------------------

import dagger.Component;

import kz.gregbiv.bamboo.authenticator.BootstrapAuthenticatorActivity;
import kz.gregbiv.bamboo.ui.BootstrapActivity;
import kz.gregbiv.bamboo.ui.BootstrapFragmentActivity;
import kz.gregbiv.bamboo.ui.BranchActivity;
import kz.gregbiv.bamboo.ui.BranchListFragment;
import kz.gregbiv.bamboo.ui.BuildListFragment;
import kz.gregbiv.bamboo.ui.MainActivity;
import kz.gregbiv.bamboo.ui.PlanActivity;
import kz.gregbiv.bamboo.ui.PlanListFragment;
import kz.gregbiv.bamboo.ui.ProjectListFragment;

//~--- JDK imports ------------------------------------------------------------

import javax.inject.Singleton;

@Singleton
@Component(modules = { AndroidModule.class, BootstrapModule.class })
public interface BootstrapComponent {
    void inject(BootstrapApplication target);

    void inject(BootstrapAuthenticatorActivity target);

    void inject(MainActivity target);

    void inject(ProjectListFragment target);

    void inject(PlanActivity target);

    void inject(PlanListFragment target);

    void inject(BranchActivity target);

    void inject(BranchListFragment target);

    void inject(BuildListFragment target);

    void inject(BootstrapFragmentActivity target);

    void inject(BootstrapActivity target);
}
