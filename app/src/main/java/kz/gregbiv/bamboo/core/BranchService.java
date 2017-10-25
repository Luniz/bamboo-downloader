
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
 * Interface for defining the project service
 */
public interface BranchService {
    @GET(Constants.Http.BRANCH_LIST)
    BranchWrapper getBranches();

    @GET(Constants.Http.BRANCH_LIST)
    BranchWrapper getBranchesForPlan(@Header("Authorization") String authorization, @Path("planKey") String planKey);
}
