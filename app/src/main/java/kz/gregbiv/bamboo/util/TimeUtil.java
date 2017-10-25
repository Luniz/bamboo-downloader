
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.util;

public final class TimeUtil {
    private TimeUtil() {}

    /**
     * Formats the time to look like "HH:MM:SS"
     *
     * @param millis The number of elapsed milliseconds
     * @return A formatted time value
     */
    public static String formatTime(final long millis) {

        // TODO does not support hour>=100 (4.1 days)
        return String.format("%02d:%02d:%02d", millis / (1000 * 60 * 60), (millis / (1000 * 60)) % 60,
                             (millis / 1000) % 60);
    }
}
