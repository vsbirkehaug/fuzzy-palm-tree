package artrec.com.artrec.article;

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

/**
 * Created by Vilde on 23.04.2016.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Context context, int textViewResourceId, ArrayList<Article> objects) {
        super(context, textViewResourceId, objects);
    }

    private class ViewHolder {
        private TextView articleTitle;
        private TextView articlePublicationDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.article_list_item, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.articleTitle = (TextView) convertView.findViewById(R.id.articleTitle);
        viewHolder.articlePublicationDate = (TextView) convertView.findViewById(R.id.articlePublicationDate);

        viewHolder.articleTitle.setText(getItem(position).getTitle());
        if(getItem(position).getPublicationDate() == null || getItem(position).getPublicationDate().equalsIgnoreCase("null")) {
            viewHolder.articlePublicationDate.setText("");
        } else {
            viewHolder.articlePublicationDate.setText(getItem(position).getPublicationDate());
        }
        convertView.setTag(viewHolder);

        return convertView;
    }

    @Override
    public Article getItem(int position) {
        return super.getItem(position);
    }

}
