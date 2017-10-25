
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.ui;

//~--- non-JDK imports --------------------------------------------------------

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Adapter for {@link TextWatcher} interface
 */
public class TextWatcherAdapter implements TextWatcher {
    public void afterTextChanged(final Editable s) {}

    public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {}

    public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {}
}
