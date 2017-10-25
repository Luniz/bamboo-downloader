
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.core;

/**
 * Bootstrap constants
 */
public final class Constants {
    private Constants() {}

    public static final class Auth {

        /**
         * Account type id
         */
        public static final String BOOTSTRAP_ACCOUNT_TYPE = "kz.gregbiv.bamboo";

        /**
         * Account name
         */
        public static final String BOOTSTRAP_ACCOUNT_NAME = "Bamboo";

        /**
         * Provider id
         */
        public static final String BOOTSTRAP_PROVIDER_AUTHORITY = "kz.gregbiv.bamboo.sync";

        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = BOOTSTRAP_ACCOUNT_TYPE;
        public static final String USERNAME       = "username";
        public static final String CREDENTIALS    = "credentials";
        public static final String AUTH_TYPE_NAME = "os_authType";
        public static final String AUTH_TYPE      = "basic";

        private Auth() {}
    }


    public static final class Extra {
        public static final String USER_ITEM    = "user_item";
        public static final String PLAN_ITEM    = "plan_item";
        public static final String PROJECT_ITEM = "project_item";
        public static final String BRANCH_ITEM  = "branch_item";

        private Extra() {}
    }


    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for bootstrap!
     */
    public static final class Http {

        /**
         * Base URL for all requests
         */
        public static final String URL_BASE          = "https://bamboo.kolesa-team.org/rest/api/latest/";
        public static final String PROJECT_LIST      = "/project.json";
        public static final String PLAN_LIST         = "/plan.json";
        public static final String BRANCH_LIST       = "/plan/{planKey}/branch.json";
        public static final String BUILD_BRANCH_LIST =
            "/result/{planKey}/branch/{branchName}.json?expand=results.result.artifacts";
        public static final String BUILD_PLAN_LIST = "/result/{planKey}.json?expand=results.result.artifacts";

        /**
         * Authentication URL
         */
        public static final String URL_AUTH_FRAG = "/currentUser.json";
        public static final String URL_AUTH      = URL_BASE + URL_AUTH_FRAG;

        /**
         * PARAMS for auth
         */
        public static final String PARAM_USERNAME    = "username";
        public static final String PARAM_PASSWORD    = "password";
        public static final String COOKIE_SESSION_ID = "JSESSIONID";
        public static final String COOKIE_MTSID      = "mtsid";

        private Http() {}
    }


    public static final class Intent {

        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "kz.gregbiv.bamboo.";

        private Intent() {}
    }


    public static class Notification {
        public static final int TIMER_NOTIFICATION_ID = 1000;    // Why 1000? Why not? :)

        private Notification() {}
    }
}
