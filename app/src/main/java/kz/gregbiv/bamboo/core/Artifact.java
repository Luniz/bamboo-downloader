
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

public class Artifact implements Serializable {
    private static final long serialVersionUID = -7811308937555074587L;
    private String            name;
    private Link              link;
    private String            producerJobKey;
    private boolean           shared;
    private long              size;
    private String            prettySizeDescription;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public String getProducerJobKey() {
        return producerJobKey;
    }

    public void setProducerJobKey(String producerJobKey) {
        this.producerJobKey = producerJobKey;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPrettySizeDescription() {
        return prettySizeDescription;
    }

    public void setPrettySizeDescription(String prettySizeDescription) {
        this.prettySizeDescription = prettySizeDescription;
    }

    /**
     * Used for De-Serialization
     */
    public class ArtifactResponse implements Serializable {
        private static final long serialVersionUID = -5869804340500782962L;
        @SerializedName("artifact")
        private List<Artifact>    artifacts;
        private int               size;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public List<Artifact> getArtifacts() {
            return artifacts;
        }

        public void setArtifacts(List<Artifact> artifacts) {
            this.artifacts = artifacts;
        }
    }
}
