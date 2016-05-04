package artrec.com.artrec.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import artrec.com.artrec.R;
import artrec.com.artrec.models.Subject;
import artrec.com.artrec.models.SubjectListItem;
import artrec.com.artrec.server.APICallURLs;

import java.util.ArrayList;

/**
 * Created by Vilde on 23.04.2016.
 */
public class SubjectPicker extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Subject> selectedSubjects;
    private RelativeLayout bottomMenu;
    private static ArrayList<Subject> results;
    private static SubjectPicker INSTANCE;
    private Button nextButton;

    public static void setResults(ArrayList<Subject> results) {
        SubjectPicker.results = results;
        INSTANCE.handleResults(results);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_picker_fragment);

        INSTANCE = this;

        listView = (ListView) findViewById(R.id.subject_list);
        bottomMenu = (RelativeLayout) findViewById(R.id.subject_bottom_menu);
        nextButton = (Button) findViewById(R.id.subject_next_button);
    }

    @Override
    public void onStart() {
        super.onStart();
        selectedSubjects = new ArrayList<>();
        try {
            getSupportActionBar().setTitle(R.string.please_select_subjects);
            getSupportActionBar().setSubtitle(String.valueOf(selectedSubjects.size()));
        } catch (NullPointerException npex) {
            npex.printStackTrace();
        }

        final SubjectPicker subjectPicker = this;
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(subjectPicker, JournalPicker.class);
                intent.putExtra("userid", getIntent().getIntExtra("userid", 0));
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("ids", getIdsFromSubjects());
                startActivity(intent);
            }
        });

        if(results == null) {
            new GetSubjectsAsyncTask(this).execute(APICallURLs.getSubjects());
        } else {
            handleResults(results);
        }
    }

    private int[] getIdsFromSubjects() {
        Log.i("vilde", ""+selectedSubjects.size());
        int[] ids = new int[selectedSubjects.size()];

        for(int i = 0; i < selectedSubjects.size(); i++) {
            ids[i] = selectedSubjects.get(i).getId();
            Log.i("vilde", "selected id "+selectedSubjects.get(i).getId());
        }
        return ids;
    }

    public void handleResults(ArrayList<Subject> subjects) {
        ArrayList<SubjectListItem> items = new ArrayList<>();

        for(Subject s : subjects) {
            items.add(new SubjectListItem(s, false));
        }

        SubjectListAdapter adapter = new SubjectListAdapter(this, 0, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    SubjectListItem item = (SubjectListItem) view.getTag();
                    item.setState(!item.getState());
                    CheckedTextView ctv = (CheckedTextView) view.findViewById(R.id.subject_text);
                    ctv.setChecked(item.getState());
                    if (item.getState()) {
                        selectedSubjects.add(item.getSubject());
                    } else {
                        selectedSubjects.remove(item.getSubject());
                    }


                    setBottomMenuShowing(selectedSubjects.size() > 0);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    if(selectedSubjects.size() > 0) {
                        getSupportActionBar().setTitle(R.string.subjects);
                    } else {
                        getSupportActionBar().setTitle(R.string.please_select_subjects);
                    }
                    getSupportActionBar().setSubtitle(String.valueOf(selectedSubjects.size()));
                } catch (NullPointerException npex) {
                    npex.printStackTrace();
                }
            }
        });

    }

    private void setBottomMenuShowing(boolean state) {
        if(state) {
            bottomMenu.setVisibility(View.VISIBLE);
        } else {
            bottomMenu.setVisibility(View.GONE);
        }
    }
}
