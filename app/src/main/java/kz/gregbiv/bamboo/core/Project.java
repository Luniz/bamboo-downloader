
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

public class Project implements Serializable {
    private static final long serialVersionUID = 6150093435367187483L;
    private String            key;
    private String            name;

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
    public class ProjectResponse implements Serializable {
        private static final long serialVersionUID = -8597337289539417827L;
        @SerializedName("project")
        private List<Project>     projects;

        public List<Project> getProjects() {
            return projects;
        }

        public void setProjects(List<Project> projects) {
            this.projects = projects;
        }
    }
}
