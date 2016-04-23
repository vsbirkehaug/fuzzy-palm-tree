package artrec.com.artrec.register;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import artrec.com.artrec.R;
import artrec.com.artrec.models.Subject;
import artrec.com.artrec.models.SubjectListItem;

import java.util.ArrayList;

/**
 * Created by Vilde on 23.04.2016.
 */
public class SubjectPicker extends DialogFragment{
    private ListView listView;
    ArrayList<String> selectedSubjects;
    RelativeLayout bottomMenu;
    private static ArrayList<Subject> results;

    public static void setResults(ArrayList<Subject> results) {
        SubjectPicker.results = results;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.subject_picker_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.discipline_list);
        bottomMenu = (RelativeLayout) view.findViewById(R.id.discipline_bottom_menu);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        selectedSubjects = new ArrayList<>();
        try {
            getActivity().getActionBar().setTitle(R.string.please_select_subjects);
            getActivity().getActionBar().setSubtitle(String.valueOf(selectedSubjects.size()));
        } catch (NullPointerException npex) {
            npex.printStackTrace();
        }

        String url = "http://192.168.0.13:8080/ArtRec/api/v1/getAllSubjects";
        new GetSubjectsAsyncTask(getActivity()).execute(url);
    }

    public void handleResults(ArrayList<String> disciplines) {
        ArrayList<SubjectListItem> items = new ArrayList<>();
        for(String s : disciplines) {
            items.add(new SubjectListItem(s, false));
        }

        SubjectListAdapter adapter = new SubjectListAdapter(this.getActivity(), 0, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    SubjectListItem item = (SubjectListItem) view.getTag();
                    item.setState(!item.getState());
                    CheckedTextView ctv = (CheckedTextView) view.findViewById(R.id.discipline_text);
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
                        getActivity().getActionBar().setTitle(R.string.subjects);
                    } else {
                        getActivity().getActionBar().setTitle(R.string.please_select_subjects);
                    }
                    getActivity().getActionBar().setSubtitle(String.valueOf(selectedSubjects.size()));
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
