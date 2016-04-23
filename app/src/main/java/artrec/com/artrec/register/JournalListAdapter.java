package artrec.com.artrec.register;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import artrec.com.artrec.R;
import artrec.com.artrec.models.JournalListItem;
import artrec.com.artrec.models.SubjectListItem;

import java.util.ArrayList;

/**
 * Created by Vilde on 23.04.2016.
 */
public class JournalListAdapter extends ArrayAdapter<JournalListItem> {

    CheckedTextView journalText;

    public JournalListAdapter(Context context, int textViewResourceId, ArrayList<JournalListItem> objects) {
        super(context, textViewResourceId, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.journal_picker_list_item, parent, false);
        }

        journalText = (CheckedTextView) convertView.findViewById(R.id.journal_text);
        journalText.setText(getItem(position).getJournal().getTitle());
        journalText.setChecked(getItem(position).getState());

        convertView.setTag(getItem(position));

        return convertView;
    }

    @Override
    public JournalListItem getItem(int position) {
        return super.getItem(position);
    }
}
