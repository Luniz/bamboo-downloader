
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

/**
 * Interface for defining the project service
 */
public interface PlanService {
    @GET(Constants.Http.PLAN_LIST)
    PlanWrapper getPlans(@Header("Authorization") String authorization);
}
