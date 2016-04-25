package artrec.com.artrec.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import artrec.com.artrec.R;
import artrec.com.artrec.models.Article;
import artrec.com.artrec.models.Project;

import java.util.ArrayList;

/**
 * Created by Vilde on 23.04.2016.
 */
public class ProjectAdapter extends ArrayAdapter<Project> {

    public ProjectAdapter(Context context, int textViewResourceId, ArrayList<Project> objects) {
        super(context, textViewResourceId, objects);
    }

    private class ViewHolder {
        private TextView projectTitle;
        private TextView projectKeywords;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.project_list_item, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.projectTitle = (TextView) convertView.findViewById(R.id.projectTitle);
        viewHolder.projectKeywords = (TextView) convertView.findViewById(R.id.projectKeywords);

        viewHolder.projectTitle.setText(getItem(position).getTitle());
        viewHolder.projectKeywords.setText(getItem(position).getKeywordsString());

        convertView.setTag(viewHolder);

        return convertView;
    }

    @Override
    public Project getItem(int position) {
        return super.getItem(position);
    }

}
