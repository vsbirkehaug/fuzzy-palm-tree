package artrec.com.artrec.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import artrec.com.artrec.R;
import artrec.com.artrec.models.Keyword;
import artrec.com.artrec.models.SubjectListItem;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Vilde on 23.04.2016.
 */
public class KeywordListAdapter extends ArrayAdapter<Keyword> {

    private final AddProjectFragment fragment;
    private final ArrayList<Keyword> keywords;
    CheckedTextView keyword;
    private String charText;
    ArrayList<Keyword> keywordsArrayList;

    public KeywordListAdapter(Context context, int textViewResourceId, ArrayList<Keyword> objects, AddProjectFragment fragment) {
        super(context, textViewResourceId, objects);
        this.keywords = objects;
        this.fragment = fragment;
        keywordsArrayList = new ArrayList<>();
        keywordsArrayList.addAll(keywords);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.keyword_list_item, parent, false);
        }

        keyword = (CheckedTextView) convertView.findViewById(R.id.keyword_text);
        keyword.setText(getItem(position).getName());
        keyword.setChecked(fragment.hasSelectedItem(getItem(position)));

        convertView.setTag(getItem(position));

        return convertView;
    }


    @Override
    public Keyword getItem(int position) {
        return super.getItem(position);
    }

    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());
        keywords.clear();
        if (this.charText.length() == 0) {
            keywords.addAll(keywordsArrayList);
        }
        else
        {
            for (Keyword jr : keywordsArrayList)
            {
                if (jr.getName().toLowerCase(Locale.getDefault()).contains(this.charText))
                {
                    keywords.add(jr);
                }
            }
        }
        notifyDataSetChanged();
    }
}
