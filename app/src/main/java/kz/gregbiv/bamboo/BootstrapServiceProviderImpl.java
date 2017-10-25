
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo;

//~--- non-JDK imports --------------------------------------------------------

import android.accounts.AccountsException;

import android.app.Activity;

import kz.gregbiv.bamboo.authenticator.ApiKeyProvider;
import kz.gregbiv.bamboo.core.BootstrapService;

import retrofit.RestAdapter;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

/**
 * Provider for a {@link kz.gregbiv.bamboo.core.BootstrapService} instance
 */
public class BootstrapServiceProviderImpl implements BootstrapServiceProvider {
    private RestAdapter    restAdapter;
    private ApiKeyProvider keyProvider;

    public BootstrapServiceProviderImpl(RestAdapter restAdapter, ApiKeyProvider keyProvider) {
        this.restAdapter = restAdapter;
        this.keyProvider = keyProvider;
    }

    /**
     * Get service for configured key provider
     * <p/>
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return bootstrap service
     * @throws IOException
     * @throws AccountsException
     */
    @Override
    public BootstrapService getService(final Activity activity) throws IOException, AccountsException {

        // The call to keyProvider.getAuthKey(...) is what initiates the login screen. Call that now.
        String credentials = keyProvider.getAuthKey(activity);

        return new BootstrapService(restAdapter, credentials);
    }
}
