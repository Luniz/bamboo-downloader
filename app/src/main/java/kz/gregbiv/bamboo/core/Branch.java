
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

public class Branch implements Serializable {
    private static final long serialVersionUID = 5459294897974356942L;
    private String            description;
    private String            shortName;
    private String            shortKey;
    private boolean           enabled;
    private String            key;
    private String            name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
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
    public class BranchResponse implements Serializable {
        private static final long serialVersionUID = -5399313299478825235L;
        @SerializedName("branch")
        private List<Branch>      branches;

        public List<Branch> getBranches() {
            return branches;
        }

        public void setProjects(List<Branch> branches) {
            this.branches = branches;
        }
    }
}
