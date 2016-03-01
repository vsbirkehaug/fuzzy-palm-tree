package com.groupb.artrec.views.setup_views;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.groupb.artrec.R;
import com.groupb.artrec.models.DisciplineListItem;
import com.groupb.artrec.server.calls.GetSubjectsAsyncTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by VSB on 23/02/2016.
 */
public class DisciplinePicker extends DialogFragment {
    ListView listView;
    ArrayList<String> selectedDisciplines;
    RelativeLayout bottomMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.discipline_picker_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.discipline_list);
        bottomMenu = (RelativeLayout) view.findViewById(R.id.discipline_bottom_menu);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        selectedDisciplines = new ArrayList<>();
        try {
            getActivity().getActionBar().setTitle(R.string.please_select_disciplines);
            getActivity().getActionBar().setSubtitle(String.valueOf(selectedDisciplines.size()));
        } catch (NullPointerException npex) {
            npex.printStackTrace();
        }

        String url = "http://10.0.2.2:8080/ArtRec/api/v1/getAllSubjects";
        new GetSubjectsAsyncTask(getActivity(), this).execute(url);
    }

    public void handleResults(ArrayList<String> disciplines) {
        ArrayList<DisciplineListItem> items = new ArrayList<>();
        for(String s : disciplines) {
            items.add(new DisciplineListItem(s, false));
        }

        DisciplineListAdapter adapter = new DisciplineListAdapter(this.getActivity(), 0, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    DisciplineListItem item = (DisciplineListItem) view.getTag();
                    item.setState(!item.getState());
                    CheckedTextView ctv = (CheckedTextView) view.findViewById(R.id.discipline_text);
                    ctv.setChecked(item.getState());
                    if (item.getState()) {
                        selectedDisciplines.add(item.getSubject());
                    } else {
                        selectedDisciplines.remove(item.getSubject());
                    }


                    setBottomMenuShowing(selectedDisciplines.size() > 0);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                try {
                    if(selectedDisciplines.size() > 0) {
                        getActivity().getActionBar().setTitle(R.string.disciplines);
                    } else {
                        getActivity().getActionBar().setTitle(R.string.please_select_disciplines);
                    }
                    getActivity().getActionBar().setSubtitle(String.valueOf(selectedDisciplines.size()));
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