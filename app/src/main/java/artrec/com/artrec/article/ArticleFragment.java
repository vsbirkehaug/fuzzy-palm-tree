package artrec.com.artrec.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import artrec.com.artrec.R;
import artrec.com.artrec.models.Article;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * Created by Vilde on 23.04.2016.
 */
public class ArticleFragment extends Fragment {

    public static ArticleFragment INSTANCE;
    private static ArrayList<Article> articles;
    private final static String url = "http://192.168.0.13:8080/ArtRec/api/v1/getAllArticlesForJournal?issn=";
    private ListView articleList;

    public ArticleFragment() {
        INSTANCE = this;
    }

    public static Fragment getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ArticleFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.article_fragment, container, false);

        articleList = (ListView) view.findViewById(R.id.articleListView);

        if(articles != null) {
            articleList.setAdapter(new ArticleAdapter(getInstance().getContext(), 0, articles));
        }

        return view;
    }

    public void getArticlesForJournal(String issn) {
        new GetArticlesForJournalAsyncTask(getActivity(), this).execute(url+issn);
    }

    void setArticleList(ArrayList<Article> resultArticles) {
        articles = resultArticles;
        if(articles.size() > 0) {
            articleList.setAdapter(new ArticleAdapter(getInstance().getContext(), 0, resultArticles));
        } else {
            articleList.setVisibility(GONE);
            TextView text = new TextView(getContext());
            text.setText("No articles found for this journal.");
            text.setTextSize(16f);
            ((LinearLayout)getView()).addView(text);
        }
    }
}
