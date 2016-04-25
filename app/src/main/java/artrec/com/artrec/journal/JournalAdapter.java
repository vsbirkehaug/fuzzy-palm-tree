package artrec.com.artrec.journal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import artrec.com.artrec.R;
import artrec.com.artrec.models.Article;
import artrec.com.artrec.models.Journal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vilde on 23.04.2016.
 */
public class JournalAdapter extends ArrayAdapter<Journal> {

    private ArrayList<Journal> journalArrayList;
    private List<Journal> journalList;

    public JournalAdapter(Context context, int textViewResourceId, ArrayList<Journal> objects) {
        super(context, textViewResourceId, objects);
        this.journalList = objects;
        journalArrayList = new ArrayList<>();
        journalArrayList.addAll(journalList);
    }

    private class ViewHolder {
        private TextView journalTitle;
        private TextView journalPublisher;
        private TextView journalRights;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.journal_list_item, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.journalTitle = (TextView) convertView.findViewById(R.id.journalTitle);
        viewHolder.journalPublisher = (TextView) convertView.findViewById(R.id.journalPublisher);
        viewHolder.journalRights = (TextView) convertView.findViewById(R.id.journalRights);

        viewHolder.journalTitle.setText(getItem(position).getTitle());
        viewHolder.journalPublisher.setText(getItem(position).getPublisher());
        viewHolder.journalRights.setText(getItem(position).getRights());

        convertView.setTag(getItem(position));

        return convertView;
    }

    @Override
    public Journal getItem(int position) {
        return super.getItem(position);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        journalList.clear();
        if (charText.length() == 0) {
            journalList.addAll(journalArrayList);
        }
        else
        {
            for (Journal jr : journalArrayList)
            {
                if (jr.getTitle().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    journalList.add(jr);
                }
            }
        }
        notifyDataSetChanged();
    }
}
