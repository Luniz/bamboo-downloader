
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.core;

//~--- non-JDK imports --------------------------------------------------------

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * User service for connecting the the REST API and
 * getting the users.
 */
public interface UserService {

    /**
     * The {@link Query} values will be transform into query string parameters
     * via Retrofit
     *
     * @return A login response.
     */
    @GET(Constants.Http.URL_AUTH_FRAG)
    User authenticate(@Header("Authorization") String authorization);
}
