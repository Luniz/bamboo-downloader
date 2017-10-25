
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo;

//~--- non-JDK imports --------------------------------------------------------

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import kz.gregbiv.bamboo.logging.CrashlyticsTree;

import timber.log.Timber;

public class BootstrapApplicationImpl extends BootstrapApplication {
    @Override
    protected void onAfterInjection() {}

    @Override
    protected void init() {

        // Start Crashlytics.
        Fabric.with(this, new Crashlytics());

        // Set the type of logger, crashlytics in release mode
        Timber.plant(new CrashlyticsTree());
    }
}
