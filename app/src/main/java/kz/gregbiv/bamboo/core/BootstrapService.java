
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.core;

//~--- non-JDK imports --------------------------------------------------------

import kz.gregbiv.bamboo.util.AuthUtils;

import retrofit.RestAdapter;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

/**
 * Bootstrap API service
 */
public class BootstrapService {
    private String      authToken;
    private RestAdapter restAdapter;

    /**
     * Create bootstrap service
     * Default CTOR
     */
    public BootstrapService(RestAdapter restAdapter) {
        this.restAdapter = restAdapter;
    }

    /**
     * Create bootstrap service
     *
     * @param restAdapter The RestAdapter that allows HTTP Communication.
     */
    public BootstrapService(RestAdapter restAdapter, String authToken) {
        this.restAdapter = restAdapter;
        this.authToken   = authToken;
    }

    private UserService getUserService() {
        return getRestAdapter().create(UserService.class);
    }

    private ProjectService getProjectService() {
        return getRestAdapter().create(ProjectService.class);
    }

    private PlanService getPlanService() {
        return getRestAdapter().create(PlanService.class);
    }

    private BranchService getBranchService() {
        return getRestAdapter().create(BranchService.class);
    }

    private BuildService getBuildService() {
        return getRestAdapter().create(BuildService.class);
    }

    private RestAdapter getRestAdapter() {
        return restAdapter;
    }

    /**
     * Get all Projects
     */
    public List<Project> getProjects() {
        return getProjectService().getProjects(AuthUtils.getAuthHeader(authToken)).getResults();
    }

    /**
     * Get all Branches for Plan
     */
    public List<Branch> getBranchesForPlan(String planKey) {
        return getBranchService().getBranchesForPlan(AuthUtils.getAuthHeader(authToken), planKey).getResults();
    }

    /**
     * Get all Builds for Plan and Branches
     */
    public List<Build> getBuildsForPlanAndBranch(String planKey, String branchName) {
        return getBuildService().getBuildsForPlanAndBranch(AuthUtils.getAuthHeader(authToken), planKey,
                branchName).getResults();
    }

    /**
     * Get all Builds for Plan
     */
    public List<Build> getBuildsForPlan(String planKey) {
        return getBuildService().getBuildsForPlan(AuthUtils.getAuthHeader(authToken), planKey).getResults();
    }

    /**
     * Get all Plans
     */
    public List<Plan> getPlans() {
        return getPlanService().getPlans(AuthUtils.getAuthHeader(authToken)).getResults();
    }

    /**
     * Authorize user
     */
    public User authenticate(String username, String password) {
        return getUserService().authenticate(AuthUtils.getAuthHeader(username, password));
    }
}
