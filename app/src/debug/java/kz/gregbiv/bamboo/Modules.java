
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo;

final class Modules {
    private Modules() {

        // No instances.
    }

    static Object[] list() {
        return new Object[] { new AndroidModule(), new BootstrapModule() };
    }
}
