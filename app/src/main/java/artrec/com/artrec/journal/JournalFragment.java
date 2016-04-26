package artrec.com.artrec.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import artrec.com.artrec.R;
import artrec.com.artrec.article.ArticleActivity;
import artrec.com.artrec.article.ArticleFragment;
import artrec.com.artrec.main.MainActivity;
import artrec.com.artrec.models.Article;
import artrec.com.artrec.models.Journal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vilde on 23.04.2016.
 */
public class JournalFragment extends Fragment{

    private final static String url = MainActivity.APIURL + "getJournals";
    private ListView journalListView;
    private EditText searchFilter;
    private JournalAdapter adapter;
    private JournalActivity activity;

    public JournalFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.journal_fragment, container, false);

        journalListView = (ListView) view.findViewById(R.id.journalListView);


        searchFilter = (EditText) view.findViewById(R.id.journalSearchFilterEditText);
        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(adapter != null) {
                    String text = searchFilter.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }
            }
        });

        GetJournalsForUserAsyncTask task = new GetJournalsForUserAsyncTask(getActivity(), this);
        Log.i("vilde", "userid getting journals: " + getActivity().getIntent().getIntExtra("userid", 0));
        task.setUser(getActivity().getIntent().getIntExtra("userid", 0));
        task.execute(url);

        return view;
    }

    void setJournalListView(ArrayList<Journal> journals) {
        adapter = new JournalAdapter(this.getContext(), 0, journals);
        journalListView.setAdapter(adapter);
        journalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                activity.goToArticleActivity(((Journal)view.getTag()));
            }
        });
    }

    public void setActivity(JournalActivity activity) {
        this.activity = activity;
    }
}
