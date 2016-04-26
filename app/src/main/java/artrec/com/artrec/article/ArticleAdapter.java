package artrec.com.artrec.article;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import artrec.com.artrec.R;
import artrec.com.artrec.models.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vilde on 23.04.2016.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    private ArrayList<String> keywords;
    private ArrayList<Article> articleArrayList;
    private List<Article> articleList;
    private String charText = "";
    ViewHolder viewHolder;

    public ArticleAdapter(Context context, int textViewResourceId, ArrayList<Article> objects) {
        super(context, textViewResourceId, objects);
        this.articleList = objects;
        articleArrayList = new ArrayList<>();
        articleArrayList.addAll(articleList);
    }

    public ArticleAdapter(Context context, int i, ArrayList<Article> articles, ArrayList<String> keywords) {
        super(context, i, articles);
        this.articleList = articles;
        articleArrayList = new ArrayList<>();
        articleArrayList.addAll(articleList);
        this.keywords = keywords;
    }

    private class ViewHolder {
        private TextView articleTitle;
        private TextView articlePublicationDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.article_list_item, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.articleTitle = (TextView) convertView.findViewById(R.id.articleTitle);
        viewHolder.articlePublicationDate = (TextView) convertView.findViewById(R.id.articlePublicationDate);

        viewHolder.articleTitle.setText(getItem(position).getTitle());
        try {
            if (keywords != null) {
                for(String s : keywords) {
                    int ofe = viewHolder.articleTitle.getText().toString().indexOf(s, 0);
                    Spannable WordtoSpan = new SpannableString(viewHolder.articleTitle.getText());
                    for (int ofs = 0; ofs < getItem(position).getTitle().length() && ofe != -1; ofs = ofe + 1) {

                        ofe = getItem(position).getTitle().indexOf(s, ofs);
                        if (ofe == -1)
                            break;
                        else {
                            if (WordtoSpan.length() >= ofe + s.length()) {
                                WordtoSpan.setSpan(new BackgroundColorSpan(0xCCCCFF66), ofe, ofe + s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                viewHolder.articleTitle.setText(WordtoSpan, TextView.BufferType.SPANNABLE);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            if (charText.length() > 0) {
                int ofe = viewHolder.articleTitle.getText().toString().indexOf(charText, 0);
                Spannable WordtoSpan = new SpannableString(viewHolder.articleTitle.getText());
                for (int ofs = 0; ofs < getItem(position).getTitle().length() && ofe != -1; ofs = ofe + 1) {

                    ofe = getItem(position).getTitle().indexOf(charText, ofs);
                    if (ofe == -1)
                        break;
                    else {
                        if (WordtoSpan.length() >= ofe + charText.length()) {
                            WordtoSpan.setSpan(new BackgroundColorSpan(0xFFFFFF00), ofe, ofe + charText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            viewHolder.articleTitle.setText(WordtoSpan, TextView.BufferType.SPANNABLE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }



        if(getItem(position).getPublicationDate() == null || getItem(position).getPublicationDate().equalsIgnoreCase("null")) {
            viewHolder.articlePublicationDate.setText("");
        } else {
            viewHolder.articlePublicationDate.setText(getItem(position).getPublicationDate());
        }
        convertView.setTag(getItem(position));

        return convertView;
    }

    @Override
    public Article getItem(int position) {
        return super.getItem(position);
    }

    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());
        articleList.clear();
        if (this.charText.length() == 0) {
            articleList.addAll(articleArrayList);
        }
        else
        {
            for (Article ar : articleArrayList)
            {
                if (ar.getTitle().toLowerCase(Locale.getDefault()).contains(this.charText))
                {
                    articleList.add(ar);
                }
            }
        }

        notifyDataSetChanged();

    }

}
