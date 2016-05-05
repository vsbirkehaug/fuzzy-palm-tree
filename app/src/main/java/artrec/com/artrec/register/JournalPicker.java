package artrec.com.artrec.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import artrec.com.artrec.R;
import artrec.com.artrec.data.DataStore;
import artrec.com.artrec.models.Journal;
import artrec.com.artrec.models.JournalListItem;
import artrec.com.artrec.server.APICallURLs;

import java.util.ArrayList;

/**
 * Created by Vilde on 23.04.2016.
 */
public class JournalPicker extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Journal> selectedJournals;
    private RelativeLayout bottomMenu;
    private ArrayList<Journal> results = null;
    private static JournalPicker INSTANCE;
    private Button nextButton;
    private int[] subjectIds;
    private Menu optionsMenu;
    JournalListAdapter adapter;
    private ArrayList<Journal> journals;

    public static void setResults(ArrayList<Journal> results) {
        INSTANCE.results = results;
        INSTANCE.handleResults(results);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journal_picker_fragment);

        INSTANCE = this;

        Intent intent = getIntent();
        subjectIds = intent.getIntArrayExtra("ids");

        listView = (ListView) findViewById(R.id.journal_list);
        bottomMenu = (RelativeLayout) findViewById(R.id.journal_bottom_menu);
        nextButton = (Button) findViewById(R.id.journal_next_button);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JournalListItem item = (JournalListItem) view.getTag();
                    item.setState(!item.getState());

                    CheckedTextView ctv = (CheckedTextView) view.findViewById(R.id.journal_text);
                    ctv.setChecked(item.getState());
                    Log.i("vilde", "size"+selectedJournals.size());
                    Log.i("vilde", "size"+item.getState());
                    if (item.getState()) {
                        selectedJournals.add(item.getJournal());
                    } else {
                        selectedJournals.remove(item.getJournal());
                    }
                    Log.i("vilde", "size"+selectedJournals.size());
                    setBottomMenuShowing(selectedJournals.size() > 0);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    if(selectedJournals.size() > 0) {
                        getSupportActionBar().setTitle(R.string.journals);
                    } else {
                        getSupportActionBar().setTitle(R.string.please_select_journals);
                    }
                    getSupportActionBar().setSubtitle(String.valueOf(selectedJournals.size()));
                } catch (NullPointerException npex) {
                    npex.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        selectedJournals = new ArrayList<>();
        try {
            getSupportActionBar().setTitle(R.string.please_select_journals);
            getSupportActionBar().setSubtitle(String.valueOf(selectedJournals.size()));
        } catch (NullPointerException npex) {
            npex.printStackTrace();
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSubjectsAndJournals();
            }
        });

        if(results == null) {
            GetJournalsForSubjectsAsyncTask call = new GetJournalsForSubjectsAsyncTask(this);
            call.setParameters(subjectIds);
            call.execute(APICallURLs.getJournalsForSubjects());
        } else {
            handleResults(results);
        }
    }

    private void saveSubjectsAndJournals() {
        PostUserSubjectsAsyncTask uSubjects = new PostUserSubjectsAsyncTask(this);
        uSubjects.setUserAndLinks(getIntent().getStringExtra("username"), getIntent().getIntExtra("userid", 0), subjectIds);
        uSubjects.execute(APICallURLs.postUserSubject());

        DataStore.getInstance().setSelectedJournals(this.selectedJournals);
        PostUserJournalsAsyncTask uJournals = new PostUserJournalsAsyncTask(this);
        uJournals.setUserAndLinks(getIntent().getStringExtra("username"), getIntent().getIntExtra("userid", 0), getIdsFromJournals());
        uJournals.execute(APICallURLs.postUserJournal());
    }

    @Override
    public void onBackPressed() {
        results = null;
        super.onBackPressed();
    }

    private int[] getIdsFromJournals() {
        Log.i("vilde", ""+selectedJournals.size());
        int[] ids = new int[selectedJournals.size()];

        for(int i = 0; i < selectedJournals.size(); i++) {
            ids[i] = selectedJournals.get(i).getId();
            Log.i("vilde", "selected id "+selectedJournals.get(i).getId());
        }
        return ids;
    }

    public void handleResults(ArrayList<Journal> journals) {
        this.journals = journals;
        ArrayList<JournalListItem> items = new ArrayList<>();

        for(Journal s : journals) {
            items.add(new JournalListItem(s, false));
        }

        adapter = new JournalListAdapter(this, 0, items);
        listView.setAdapter(adapter);

    }

    private void setBottomMenuShowing(boolean state) {
        if(state) {
            bottomMenu.setVisibility(View.VISIBLE);
        } else {
            bottomMenu.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.subject_picker, menu);
        optionsMenu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_clear) {
            for(int i = 0; i < adapter.getCount(); i++) {
                adapter.getItem(i).setState(false);
            }
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }
}
