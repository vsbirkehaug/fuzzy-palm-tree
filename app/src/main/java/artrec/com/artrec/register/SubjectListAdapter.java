package artrec.com.artrec.register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import artrec.com.artrec.R;
import artrec.com.artrec.models.SubjectListItem;

import java.util.ArrayList;

/**
 * Created by Vilde on 23.04.2016.
 */
public class SubjectListAdapter extends ArrayAdapter<SubjectListItem> {

    CheckedTextView disciplineText;

    public SubjectListAdapter(Context context, int textViewResourceId, ArrayList<SubjectListItem> objects) {
        super(context, textViewResourceId, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.subject_picker_list_item, parent, false);
        }

        disciplineText = (CheckedTextView) convertView.findViewById(R.id.discipline_text);
        disciplineText.setText(getItem(position).getSubject());
        disciplineText.setChecked(getItem(position).getState());

        convertView.setTag(getItem(position));

        return convertView;
    }

    @Override
    public SubjectListItem getItem(int position) {
        return super.getItem(position);
    }
}
