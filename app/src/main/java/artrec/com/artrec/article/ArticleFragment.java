package artrec.com.artrec.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import artrec.com.artrec.R;
import artrec.com.artrec.main.MainActivity;
import artrec.com.artrec.models.Article;

import java.util.ArrayList;
import java.util.Locale;

import static android.view.View.GONE;

/**
 * Created by Vilde on 23.04.2016.
 */
public class ArticleFragment extends Fragment {

    public static ArticleFragment INSTANCE;
    private ArrayList<Article> articles;
    private final static String url = MainActivity.APIURL+"getAllArticlesForJournal?issn=";
    private ListView articleList;
    private EditText searchFilter;
    private ArticleAdapter adapter;

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

/*
        if(articles != null) {
            articleList.setAdapter(new ArticleAdapter(getInstance().getContext(), 0, articles));
        }
*/

        searchFilter = (EditText) view.findViewById(R.id.articleSearchFilterEditText);
        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = searchFilter.getText().toString().toLowerCase(Locale.getDefault());
                if(adapter != null) {
                    adapter.filter(text);
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {

            if (articles != null && articles.size() > 0) {
                adapter = new ArticleAdapter(getInstance().getContext(), 0, articles);
                articleList.setAdapter(adapter);
            } else {
                articleList.setVisibility(GONE);
                TextView text = new TextView(getContext());
                text.setText("No articles found.");
                text.setTextSize(16f);
                ((LinearLayout) getView()).addView(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getArticlesForJournal(String issn) {
        new GetArticlesForJournalAsyncTask(getActivity(), this).execute(url+issn);
    }

    public void setArticleList(ArrayList<Article> resultArticles) {
        articles = resultArticles;

    }
}
