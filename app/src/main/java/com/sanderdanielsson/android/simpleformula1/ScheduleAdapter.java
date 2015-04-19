package com.sanderdanielsson.android.simpleformula1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sander on 2015-04-13.
 */
public class ScheduleAdapter extends ArrayAdapter<Race> {

    private ArrayList<Race> mList;
    private LayoutInflater inflater;
    private final Context context;
//    private final int textViewResourceId;

    public ScheduleAdapter(Context context, int resource, ArrayList<Race> mlist) {
        super(context, resource, mlist);
        this.mList = mlist;
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getPosition(Race race) {
        return super.getPosition(race);
    }

    @Override
    public Race getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        ViewHolder holder = null;
        Race race = mList.get(position);


        if (itemView == null) {
            itemView = inflater.inflate(R.layout.schedule_row, parent, false);
            holder = new ViewHolder();
            holder.flag = (ImageView) itemView.findViewById(R.id.flag);
            holder.circuitName = (TextView) itemView.findViewById(R.id.circuitName);
            holder.raceName = (TextView) itemView.findViewById(R.id.raceName);
            holder.locality = (TextView) itemView.findViewById(R.id.locality);
            holder.date = (TextView) itemView.findViewById(R.id.date);
            holder.time = (TextView) itemView.findViewById(R.id.time);

            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        Integer round = mList.indexOf(race) + 1;
        int id = context.getResources().getIdentifier(race.getCountry().toLowerCase(), "drawable", context.getPackageName());

        holder.flag.setImageResource(id);
        holder.raceName.setText(race.getRaceName());
        holder.circuitName.setText(race.getCircuitName());
        holder.locality.setText(race.getLocality());
        holder.date.setText(race.getDate());
        holder.time.setText(race.getTime());

        return itemView;
    }

    static class ViewHolder {
        ImageView flag;
        TextView raceName;
        TextView circuitName;
        TextView locality;
        TextView date;
        TextView time;
    }
}
