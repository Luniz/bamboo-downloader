
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
import retrofit.http.Path;

/**
 * Interface for defining the builds service
 */
public interface BuildService {
    @GET(Constants.Http.BUILD_PLAN_LIST)
    BuildWrapper getBuildsForPlan(@Header("Authorization") String authorization, @Path("planKey") String planKey);

    @GET(Constants.Http.BUILD_BRANCH_LIST)
    BuildWrapper getBuildsForPlanAndBranch(@Header("Authorization") String authorization,
            @Path("planKey") String planKey, @Path("branchName") String branchName);
}
