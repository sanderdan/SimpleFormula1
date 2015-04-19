package com.sanderdanielsson.android.simpleformula1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sander on 2015-04-13.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    private ArrayList<News> mList;
    private LayoutInflater inflater;
    private final Context context;
//    private final int textViewResourceId;

    public NewsAdapter(Context context, int resource, ArrayList<News> mlist) {
        super(context, resource, mlist);
        this.mList = mlist;
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getPosition(News news) {
        return super.getPosition(news);
    }

    @Override
    public News getItem(int position) {
        return mList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ViewHolder holder = null;
        News news = mList.get(position);


        if (itemView == null) {
            itemView = inflater.inflate(R.layout.news_row, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) itemView.findViewById(R.id.title);
            holder.description = (TextView) itemView.findViewById(R.id.description);
            holder.date = (TextView) itemView.findViewById(R.id.date);
            holder.img = (ImageView) itemView.findViewById(R.id.img);

            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        holder.title.setText(news.getTitle());
        holder.description.setText(news.getDescription());
        holder.date.setText(news.getPubDate());
        Picasso.with(context).load(news.getImgUrl()).into(holder.img);
//        holder.url.setText(news.getLink());

        return itemView;
    }

    static class ViewHolder {
        TextView title;
        TextView description;
        TextView date;
        ImageView img;
//        TextView url;
    }

}