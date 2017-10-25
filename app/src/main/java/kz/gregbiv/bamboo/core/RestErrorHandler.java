
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.core;

//~--- non-JDK imports --------------------------------------------------------

import com.squareup.otto.Bus;

import kz.gregbiv.bamboo.events.NetworkErrorEvent;
import kz.gregbiv.bamboo.events.RestAdapterErrorEvent;
import kz.gregbiv.bamboo.events.UnAuthorizedErrorEvent;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

public class RestErrorHandler implements ErrorHandler {
    public static final int HTTP_UNAUTHORIZED = 401;
    private Bus             bus;

    public RestErrorHandler(Bus bus) {
        this.bus = bus;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {
        if (cause != null) {
            if (cause.isNetworkError()) {
                bus.post(new NetworkErrorEvent(cause));
            } else if (isUnAuthorized(cause)) {
                bus.post(new UnAuthorizedErrorEvent(cause));
            } else {
                bus.post(new RestAdapterErrorEvent(cause));
            }
        }

        // Example of how you'd check for a unauthorized result
        // if (cause != null && cause.getStatus() == 401) {
        // return new UnauthorizedException(cause);
        // }
        // You could also put some generic error handling in here so you can start
        // getting analytics on error rates/etc. Perhaps ship your logs off to
        // Splunk, Loggly, etc
        return cause;
    }

    /**
     * @param cause The initial error.
     * @return
     */
    private boolean isUnAuthorized(RetrofitError cause) {
        return cause.getResponse().getStatus() == HTTP_UNAUTHORIZED;
    }
}
