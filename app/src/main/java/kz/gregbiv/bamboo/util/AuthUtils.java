
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.util;

//~--- non-JDK imports --------------------------------------------------------

import android.util.Base64;

/**
 * For auth specific uses
 */
public class AuthUtils {

    /**
     * Returns Basic authorization with auth token
     */
    public static String getAuthHeader(String authToken) {
        return "Basic " + authToken;
    }

    /**
     * Returns Basic authorization with generated auth token
     */
    public static String getAuthHeader(String username, String password) {
        return "Basic " + getBase64Credentials(username, password);
    }

    /**
     * Generates auth token
     */
    public static String getBase64Credentials(String username, String password) {

        // concatenate username and password with colon for authentication
        String credentials = username + ":" + password;

        return Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    }
}
