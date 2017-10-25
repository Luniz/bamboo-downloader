
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.util;

//~--- non-JDK imports --------------------------------------------------------

import android.content.Context;
import android.content.res.Configuration;

public class UIUtils {

    /**
     * Helps determine if the app is running in a Tablet context.
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
               >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
