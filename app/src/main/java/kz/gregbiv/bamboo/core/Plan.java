
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

import java.io.Serializable;

import java.util.List;

public class Plan implements Serializable {
    private static final long serialVersionUID = -8952341817280421536L;
    private String            shortName;
    private String            shortKey;
    private String            type;
    private Boolean           enabled;
    private String            key;
    private String            name;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Used for De-Serialization
     */
    public class PlanResponse implements Serializable {
        private static final long serialVersionUID = 3395006159881377848L;
        @SerializedName("plan")
        private List<Plan>        plans;

        public List<Plan> getPlans() {
            return plans;
        }

        public void setProjects(List<Plan> plans) {
            this.plans = plans;
        }
    }
}
