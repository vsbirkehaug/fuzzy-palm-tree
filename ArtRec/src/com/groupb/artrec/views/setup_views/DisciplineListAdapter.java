package com.groupb.artrec.views.setup_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import com.groupb.artrec.R;
import com.groupb.artrec.models.DisciplineListItem;

import java.util.ArrayList;

/**
 * Created by VSB on 23/02/2016.
 */
public class DisciplineListAdapter extends ArrayAdapter<DisciplineListItem> {

    CheckedTextView disciplineText;

    public DisciplineListAdapter(Context context, int textViewResourceId, ArrayList<DisciplineListItem> objects) {
        super(context, textViewResourceId, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.discipline_picker_list_item, parent, false);
        }

        disciplineText = (CheckedTextView) convertView.findViewById(R.id.discipline_text);
        disciplineText.setText(getItem(position).getSubject());
        disciplineText.setChecked(getItem(position).getState());

        convertView.setTag(getItem(position));

        return convertView;
    }

    @Override
    public DisciplineListItem getItem(int position) {
        return super.getItem(position);
    }
}
