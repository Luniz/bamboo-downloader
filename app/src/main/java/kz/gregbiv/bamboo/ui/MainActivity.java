
/**
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 *
 * @author  Gregory Kornienko <gregbiv@gmail.com>
 * @license MIT
 */
package kz.gregbiv.bamboo.ui;

//~--- non-JDK imports --------------------------------------------------------

import android.accounts.OperationCanceledException;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.Window;

import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import kz.gregbiv.bamboo.BootstrapApplication;
import kz.gregbiv.bamboo.BootstrapServiceProvider;
import kz.gregbiv.bamboo.R;
import kz.gregbiv.bamboo.authenticator.LogoutService;
import kz.gregbiv.bamboo.core.BootstrapService;
import kz.gregbiv.bamboo.core.Constants;
import kz.gregbiv.bamboo.util.SafeAsyncTask;
import kz.gregbiv.bamboo.util.UIUtils;

//~--- JDK imports ------------------------------------------------------------

import javax.inject.Inject;

/**
 * Initial activity for the application.
 */
public class MainActivity extends BootstrapActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean          userHasAuthenticated = false;
    @Bind(R.id.nav_view)
    protected NavigationView navigationView;
    @Bind(R.id.drawer_layout)
    protected DrawerLayout   drawer;
    @Bind(R.id.tv_nav_username)
    TextView                 username;
    @Inject
    BootstrapServiceProvider serviceProvider;
    @Inject
    LogoutService            logoutService;
    @Inject
    SharedPreferences        sharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        BootstrapApplication.component().inject(this);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        // View injection with Butterknife
        ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                                           R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // Username
        username.setText(sharedPreferences.getString(Constants.Auth.USERNAME, null));

        // Current selected item
        navigationView.getMenu().getItem(0).setChecked(true);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        checkAuth();
    }

    private boolean isTablet() {
        return UIUtils.isTablet(this);
    }

    private void initScreen() {
        if (userHasAuthenticated) {
            final FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.container, new PlanListFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
        case android.R.id.home :

            // menuDrawer.toggleMenu();
            return true;

        default :
            return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_plans) {}
        else if (id == R.id.nav_logout) {
            logoutService.logout(new Runnable() {
                @Override
                public void run() {
                    checkAuth();
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void checkAuth() {
        new SafeAsyncTask<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final BootstrapService svc = serviceProvider.getService(MainActivity.this);

                return svc != null;
            }
            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);

                if (e instanceof OperationCanceledException) {

                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
                    finish();
                }
            }
            @Override
            protected void onSuccess(final Boolean hasAuthenticated) throws Exception {
                super.onSuccess(hasAuthenticated);
                userHasAuthenticated = true;
                initScreen();
            }
        }.execute();
    }
}
