package artrec.com.artrec.project;

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
import artrec.com.artrec.main.MainActivity;
import artrec.com.artrec.models.Article;
import artrec.com.artrec.models.Project;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * Created by Vilde on 23.04.2016.
 */
public class ProjectFragment extends Fragment {

    public static ProjectFragment INSTANCE;
    private ArrayList<Project> projects;
    private final static String url = MainActivity.APIURL+"getProjectsForUser";
    private ListView projectList;

    public ProjectFragment() {
        INSTANCE = this;
    }

    public static Fragment getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ProjectFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.project_fragment, container, false);

        projectList = (ListView) view.findViewById(R.id.projectListView);

        if(projects != null) {
            projectList.setAdapter(new ProjectAdapter(getInstance().getContext(), 0, projects));
        } else {
            getProjectsForUser(getActivity().getIntent().getIntExtra("userid", -1));
        }

        return view;
    }

    public void getProjectsForUser(int userId) {
        new GetProjectsForUserAsyncTask(getActivity(), this).execute(url, String.valueOf(userId));
    }

    void setProjectList(ArrayList<Project> resultArticles) {
        projects = resultArticles;
        if(projects.size() > 0) {
            projectList.setAdapter(new ProjectAdapter(getInstance().getContext(), 0, resultArticles));
        } else {
            projectList.setVisibility(GONE);
            TextView text = new TextView(getContext());
            text.setText("No projects found for this user.");
            text.setTextSize(16f);
            ((LinearLayout)getView()).addView(text);
        }
    }
}