package artrec.com.artrec.article;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import artrec.com.artrec.R;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArticleFragment fragment = (ArticleFragment)getSupportFragmentManager().getFragments().get(0);
        fragment.setActivity(this);

        loadArticles(fragment);
    }

    private void loadArticles(ArticleFragment fragment) {
        if(getIntent().hasExtra("issn")) {
            fragment.getArticlesForJournal(getIntent().getStringExtra("issn"));
        } else if (getIntent().hasExtra("projectid")) {
            fragment.getArticlesForProject(getIntent().getIntExtra("projectid", -1));
            if(getIntent().hasExtra("keywords"))
                fragment.setKeywords(getIntent().getStringArrayListExtra("keywords"));
        }
    }

}
