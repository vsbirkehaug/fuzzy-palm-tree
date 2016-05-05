package artrec.com.artrec.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.*;
import artrec.com.artrec.R;
import artrec.com.artrec.models.Keyword;
import artrec.com.artrec.models.Project;
import artrec.com.artrec.server.APICallURLs;

import java.util.ArrayList;

public class AddProjectFragment extends Fragment {

    private ArrayList<Keyword> keywords = new ArrayList<>();
    private ArrayList<Keyword> selectedKeywords = new ArrayList<>();
    private ListView keywordList;
    private KeywordListAdapter adapter;
    private Button nextButton;
    private EditText title;
    private ProjectActivity activity;
    private EditText filterText;
    private RelativeLayout bottomMenu;

    public AddProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadKeywords();
    }

    private void loadKeywords() {
        GetKeywordsAsyncTask task = new GetKeywordsAsyncTask(getActivity(), this);
        task.execute(APICallURLs.getKeywords());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_add_project, container, false);

        title = (EditText) view.findViewById(R.id.newProjectTitle);
        keywordList = (ListView) view.findViewById(R.id.newProjectKeywordsList);
        keywordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if(selectedKeywords.contains((Keyword)view.getTag())) {
                        selectedKeywords.remove((Keyword)view.getTag());
                    } else {
                        selectedKeywords.add((Keyword) view.getTag());
                    }
                    if(selectedKeywords.size() > 0) {
                        bottomMenu.setVisibility(View.VISIBLE);
                    } else {
                        bottomMenu.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        filterText = (EditText) view.findViewById(R.id.keywordFilter);
        filterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.filter(filterText.getText().toString());
            }
        });

        bottomMenu = (RelativeLayout) view.findViewById(R.id.keyword_bottom_menu);
        nextButton = (Button) view.findViewById(R.id.keyword_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProject();
            }
        });

        return view;
    }

    private void createProject() {
        Project project = new Project(title.getText().toString(), getActivity().getIntent().getIntExtra("userid", -1), selectedKeywords);
        PostNewProjectAsyncTask task = new PostNewProjectAsyncTask(getActivity(), this, project, getIdsFromKeywords());
        task.execute(APICallURLs.postProject());
    }

    private int[] getIdsFromKeywords() {
        int[] ids = new int[keywords.size()];

        for(int i = 0; i < keywords.size(); i++) {
            ids[i] = keywords.get(i).getId();
        }
        return ids;
    }


    public void setKeywords(ArrayList<Keyword> keywords) {
        this.keywords = keywords;
        adapter = new KeywordListAdapter(getActivity(), 0, keywords, this);
        keywordList.setAdapter(adapter);
    }

    public boolean hasSelectedItem(Keyword item) {
        for(Keyword k : selectedKeywords) {
            if(item.getId() == k.getId()) {
                return true;
            }
        }
        return false;
    }

    public void doneCreating() {
        activity.loadProjects();
    }

    public void setActivity(ProjectActivity activity) {
        this.activity = activity;
    }
}
