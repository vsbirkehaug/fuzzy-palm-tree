package artrec.com.artrec.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import artrec.com.artrec.R;
import artrec.com.artrec.article.ArticleActivity;
import artrec.com.artrec.models.Project;

public class ProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
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

        ProjectFragment fragment = (ProjectFragment)getSupportFragmentManager().getFragments().get(0);
        fragment.setActivity(this);
    }

    public void goToArticleActivity(Project project) {
        try {
            Intent intent = new Intent(this, ArticleActivity.class);
            intent.putExtra("projectid", project.getId());
            intent.putExtra("keywords", project.getKeywordsAsStringArrayList());
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
