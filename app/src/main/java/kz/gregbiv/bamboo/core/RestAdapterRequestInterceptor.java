
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.core;

//~--- non-JDK imports --------------------------------------------------------

import retrofit.RequestInterceptor;

public class RestAdapterRequestInterceptor implements RequestInterceptor {
    private UserAgentProvider userAgentProvider;

    public RestAdapterRequestInterceptor(UserAgentProvider userAgentProvider) {
        this.userAgentProvider = userAgentProvider;
    }

    @Override
    public void intercept(RequestFacade request) {

        // Provide AuthType
        request.addQueryParam(Constants.Auth.AUTH_TYPE_NAME, Constants.Auth.AUTH_TYPE);

        // Add header to set content type of JSON
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Accept", "application/json");

        // Add the user agent to the request.
        request.addHeader("User-Agent", userAgentProvider.get());
    }
}
