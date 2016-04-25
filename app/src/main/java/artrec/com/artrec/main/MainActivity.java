package artrec.com.artrec.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import artrec.com.artrec.R;
import artrec.com.artrec.article.ArticleFragment;
import artrec.com.artrec.journal.JournalFragment;
import artrec.com.artrec.login.LoginActivity;
import artrec.com.artrec.models.Article;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static MainActivity INSTANCE;
    private Menu optionsMenu;
    NavigationView navigationView;
    int userId;
    String username;
    private static boolean journalSaveDone = false;
    private static boolean subjectSaveDone = false;
    public static final String APIURL = "http://192.168.0.13:8080/ArtRec/api/v1/";
    FrameLayout contentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        INSTANCE = this;
        Intent intent = getIntent();

        this.userId = intent.getIntExtra("userid", 0);
        this.username = intent.getStringExtra("username");

        if(userId == 0) {
            Intent newIntent = new Intent(this, LoginActivity.class);
            startActivity(newIntent);
            this.finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        contentFrame = (FrameLayout) findViewById(R.id.mainContent);
        contentFrame.addView(getLayoutInflater().inflate(R.layout.main_fragment, null));
        getSupportActionBar().setTitle(getString(R.string.welcome));

    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            ((TextView) navigationView.findViewById(R.id.appNameTextViewMain)).setText(username.toUpperCase());
        } catch (Exception ex) {
            ex.printStackTrace();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        optionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;

        try {
            switch (id) {
                case R.id.nav_articles:
                    fragment = ArticleFragment.getInstance();
                    getSupportActionBar().setTitle(getString(R.string.articles));
                    break;
                case R.id.nav_journals:
                    fragmentClass = JournalFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    getSupportActionBar().setTitle(getString(R.string.journals));
                    break;
                case R.id.nav_manage:
                    fragmentClass = JournalFragment.class;
                    break;
                default:
                    fragmentClass = JournalFragment.class;
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        goToFragment(fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setTitle(Fragment fragment) {
        //FragmentEnum frag;

      if(fragment.getClass().equals(ArticleFragment.class)) {
          //frag = FragmentEnum.ARTICLE;
          getSupportActionBar().setTitle(getString(R.string.articles));
      } else if (fragment.getClass().equals(JournalFragment.class)) {
          getSupportActionBar().setTitle(getString(R.string.journals));
          //frag =  FragmentEnum.JOURNAL;
      }

/*        switch(frag) {
            case ARTICLE: {
                getSupportActionBar().setTitle(getString(R.string.articles));
                break;
            }
            case JOURNAL: {
                getSupportActionBar().setTitle(getString(R.string.journals));
                break;
            }
        }*/
    }


    public void goToFragment(Fragment fragment) {

        setTitle(fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
        if(contentFrame.getChildCount() > 0) {
            contentFrame.removeAllViews();
        }
        fragmentManager.beginTransaction().replace(R.id.mainContent, fragment).commit();
    }

    public static boolean setJournalSaveDone(boolean journalSaveDone) {
        MainActivity.journalSaveDone = journalSaveDone;
        return journalSaveDone&&subjectSaveDone;
    }

    public static boolean setSubjectSaveDone(boolean subjectSaveDone) {
        MainActivity.subjectSaveDone = subjectSaveDone;
        return journalSaveDone&&subjectSaveDone;
    }

}
