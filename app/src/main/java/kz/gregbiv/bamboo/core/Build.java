
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

import kz.gregbiv.bamboo.core.Artifact.ArtifactResponse;

//~--- JDK imports ------------------------------------------------------------

import java.io.Serializable;

import java.util.List;

public class Build implements Serializable {
    private static final long serialVersionUID = 765543230817543089L;
    private Link              link;
    private Plan              plan;
    private String            planName;
    private String            projectName;
    private String            buildResultKey;
    private String            lifeCycleState;
    private long              id;
    private String            buildStartedTime;
    private String            prettyBuildStartedTime;
    private String            buildCompletedTime;
    private String            buildCompletedDate;
    private String            prettyBuildCompletedTime;
    private int               buildDurationInSeconds;
    private int               buildDuration;
    private String            buildDurationDescription;
    private String            buildRelativeTime;
    private String            vcsRevisionKey;
    private String            buildTestSummary;
    private int               successfulTestCount;
    private int               failedTestCount;
    private int               quarantinedTestCount;
    private int               skippedTestCount;
    private boolean           continuable;
    private boolean           onceOff;
    private boolean           restartable;
    private boolean           notRunYet;
    private boolean           finished;
    private boolean           successful;
    private String            buildReason;
    private String            reasonSummary;
    @SerializedName("artifacts")
    private ArtifactResponse  artifactResponse;
    private String            key;
    private String            state;
    private String            buildState;
    private int               number;
    private int               buildNumber;

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuildResultKey() {
        return buildResultKey;
    }

    public void setBuildResultKey(String buildResultKey) {
        this.buildResultKey = buildResultKey;
    }

    public String getLifeCycleState() {
        return lifeCycleState;
    }

    public void setLifeCycleState(String lifeCycleState) {
        this.lifeCycleState = lifeCycleState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBuildStartedTime() {
        return buildStartedTime;
    }

    public void setBuildStartedTime(String buildStartedTime) {
        this.buildStartedTime = buildStartedTime;
    }

    public String getPrettyBuildStartedTime() {
        return prettyBuildStartedTime;
    }

    public void setPrettyBuildStartedTime(String prettyBuildStartedTime) {
        this.prettyBuildStartedTime = prettyBuildStartedTime;
    }

    public String getBuildCompletedTime() {
        return buildCompletedTime;
    }

    public void setBuildCompletedTime(String buildCompletedTime) {
        this.buildCompletedTime = buildCompletedTime;
    }

    public String getBuildCompletedDate() {
        return buildCompletedDate;
    }

    public void setBuildCompletedDate(String buildCompletedDate) {
        this.buildCompletedDate = buildCompletedDate;
    }

    public String getPrettyBuildCompletedTime() {
        return prettyBuildCompletedTime;
    }

    public void setPrettyBuildCompletedTime(String prettyBuildCompletedTime) {
        this.prettyBuildCompletedTime = prettyBuildCompletedTime;
    }

    public int getBuildDurationInSeconds() {
        return buildDurationInSeconds;
    }

    public void setBuildDurationInSeconds(int buildDurationInSeconds) {
        this.buildDurationInSeconds = buildDurationInSeconds;
    }

    public int getBuildDuration() {
        return buildDuration;
    }

    public void setBuildDuration(int buildDuration) {
        this.buildDuration = buildDuration;
    }

    public String getBuildDurationDescription() {
        return buildDurationDescription;
    }

    public void setBuildDurationDescription(String buildDurationDescription) {
        this.buildDurationDescription = buildDurationDescription;
    }

    public String getBuildRelativeTime() {
        return buildRelativeTime;
    }

    public void setBuildRelativeTime(String buildRelativeTime) {
        this.buildRelativeTime = buildRelativeTime;
    }

    public String getVcsRevisionKey() {
        return vcsRevisionKey;
    }

    public void setVcsRevisionKey(String vcsRevisionKey) {
        this.vcsRevisionKey = vcsRevisionKey;
    }

    public String getBuildTestSummary() {
        return buildTestSummary;
    }

    public void setBuildTestSummary(String buildTestSummary) {
        this.buildTestSummary = buildTestSummary;
    }

    public int getSuccessfulTestCount() {
        return successfulTestCount;
    }

    public void setSuccessfulTestCount(int successfulTestCount) {
        this.successfulTestCount = successfulTestCount;
    }

    public int getFailedTestCount() {
        return failedTestCount;
    }

    public void setFailedTestCount(int failedTestCount) {
        this.failedTestCount = failedTestCount;
    }

    public int getQuarantinedTestCount() {
        return quarantinedTestCount;
    }

    public void setQuarantinedTestCount(int quarantinedTestCount) {
        this.quarantinedTestCount = quarantinedTestCount;
    }

    public int getSkippedTestCount() {
        return skippedTestCount;
    }

    public void setSkippedTestCount(int skippedTestCount) {
        this.skippedTestCount = skippedTestCount;
    }

    public boolean isContinuable() {
        return continuable;
    }

    public void setContinuable(boolean continuable) {
        this.continuable = continuable;
    }

    public boolean isOnceOff() {
        return onceOff;
    }

    public void setOnceOff(boolean onceOff) {
        this.onceOff = onceOff;
    }

    public boolean isRestartable() {
        return restartable;
    }

    public void setRestartable(boolean restartable) {
        this.restartable = restartable;
    }

    public boolean isNotRunYet() {
        return notRunYet;
    }

    public void setNotRunYet(boolean notRunYet) {
        this.notRunYet = notRunYet;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getBuildReason() {
        return buildReason;
    }

    public void setBuildReason(String buildReason) {
        this.buildReason = buildReason;
    }

    public String getReasonSummary() {
        return reasonSummary;
    }

    public void setReasonSummary(String reasonSummary) {
        this.reasonSummary = reasonSummary;
    }

    public ArtifactResponse getArtifactResponse() {
        return artifactResponse;
    }

    public void setArtifactResponse(ArtifactResponse artifactResponse) {
        this.artifactResponse = artifactResponse;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBuildState() {
        return buildState;
    }

    public void setBuildState(String buildState) {
        this.buildState = buildState;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    /**
     * Used for De-Serialization
     */
    public class BuildResponse implements Serializable {
        private static final long serialVersionUID = -7011489009906904411L;
        @SerializedName("result")
        private List<Build>       builds;

        public List<Build> getBuilds() {
            return builds;
        }

        public void setBuilds(List<Build> builds) {
            this.builds = builds;
        }
    }
}
