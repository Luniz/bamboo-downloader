
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo;

//~--- non-JDK imports --------------------------------------------------------

import android.app.Application;

/**
 * Bamboo application
 */
public abstract class BootstrapApplication extends Application {
    private static BootstrapApplication instance;
    private BootstrapComponent          component;

    /**
     * Create main application
     */
    public BootstrapApplication() {}

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        instance = this;

        // Perform injection
        // Injector.init(this, )
        component = DaggerComponentInitializer.init();
        onAfterInjection();
    }

    public static BootstrapComponent component() {
        return instance.component;
    }

    protected abstract void onAfterInjection();

    protected abstract void init();

    public static BootstrapApplication getInstance() {
        return instance;
    }

    public BootstrapComponent getComponent() {
        return component;
    }

    public final static class DaggerComponentInitializer {
        public static BootstrapComponent init() {
            return DaggerBootstrapComponent.builder().androidModule(new AndroidModule()).bootstrapModule(
                new BootstrapModule()).build();
        }
    }
}
