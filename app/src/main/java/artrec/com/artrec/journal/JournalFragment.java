package artrec.com.artrec.journal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import artrec.com.artrec.R;
import artrec.com.artrec.article.ArticleFragment;
import artrec.com.artrec.main.FragmentEnum;
import artrec.com.artrec.main.MainActivity;
import artrec.com.artrec.models.Article;
import artrec.com.artrec.models.Journal;
import com.pkmmte.pkrss.PkRSS;

import java.util.ArrayList;

/**
 * Created by Vilde on 23.04.2016.
 */
public class JournalFragment extends Fragment{

    private final static String url = "http://192.168.0.13:8080/ArtRec/api/v1/getAllJournals";
    private ListView journalList;

    public JournalFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.journal_fragment, container, false);

        journalList = (ListView) view.findViewById(R.id.journalListView);

        new GetJournalsAsyncTask(getActivity(), this).execute(url);
        return view;
    }

    void setJournalList(ArrayList<Journal> journals) {
        journalList.setAdapter(new JournalAdapter(this.getContext(), 0, journals));
        journalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArticleFragment fragment = new ArticleFragment();
                fragment.getArticlesForJournal(((Journal)view.getTag()).getIssn());
                try {
                    MainActivity.INSTANCE.goToFragment(fragment);
                    MainActivity.INSTANCE.setMenuItemEnabled(FragmentEnum.ARTICLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
