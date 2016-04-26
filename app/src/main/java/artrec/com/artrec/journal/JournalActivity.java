package artrec.com.artrec.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import artrec.com.artrec.R;
import artrec.com.artrec.article.ArticleActivity;
import artrec.com.artrec.article.ArticleFragment;
import artrec.com.artrec.main.MainActivity;
import artrec.com.artrec.models.Journal;

public class JournalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
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

        JournalFragment fragment = (JournalFragment)getSupportFragmentManager().getFragments().get(0);
        fragment.setActivity(this);

    }

    public void goToArticleActivity(Journal journal) {
        try {
            Intent intent = new Intent(this, ArticleActivity.class);
            intent.putExtra("issn", journal.getIssn());
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
