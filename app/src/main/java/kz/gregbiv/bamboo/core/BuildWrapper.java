
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.core;

//~--- non-JDK imports --------------------------------------------------------

import com.google.gson.annotations.SerializedName;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

public class BuildWrapper {
    @SerializedName("results")
    private Build.BuildResponse buildResponse;

    public List<Build> getResults() {
        return buildResponse.getBuilds();
    }
}
