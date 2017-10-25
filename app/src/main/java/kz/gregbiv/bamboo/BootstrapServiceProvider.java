
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo;

//~--- non-JDK imports --------------------------------------------------------

import android.accounts.AccountsException;

import android.app.Activity;

import kz.gregbiv.bamboo.core.BootstrapService;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;

public interface BootstrapServiceProvider {
    BootstrapService getService(Activity activity) throws IOException, AccountsException;
}
