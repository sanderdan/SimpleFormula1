package com.sanderdanielsson.android.simpleformula1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sander on 2015-04-08.
 */
public class DriverStandingsAdapter extends ArrayAdapter<Driver> {

    private ArrayList<Driver> mList;
    private LayoutInflater inflater;
    private final Context context;
//    private final int textViewResourceId;

    public DriverStandingsAdapter(Context context, int resource, ArrayList<Driver> mlist) {
        super(context, resource, mlist);
        this.mList = mlist;
        this.context = context;
//        this.textViewResourceId = textViewResourceId;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getPosition(Driver driver) {
        return super.getPosition(driver);
    }

    @Override
    public Driver getItem(int position) {
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
        Driver driver = mList.get(position);


        if (itemView == null) {
            itemView = inflater.inflate(R.layout.stanidngs_row, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) itemView.findViewById(R.id.driverName);
            holder.pos = (TextView) itemView.findViewById(R.id.pos);
            holder.team = (TextView) itemView.findViewById(R.id.team);
            holder.points = (TextView) itemView.findViewById(R.id.points);
            holder.wins = (TextView) itemView.findViewById(R.id.wins);
            holder.bgColor = (LinearLayout) itemView.findViewById(R.id.standings_rowBackground);

            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        Integer driverPos = mList.indexOf(driver) + 1;


        holder.name.setText(driver.getFirstName() + " " + driver.getLastName());
        holder.pos.setText(String.valueOf(driverPos));
        holder.team.setText(driver.getTeam());
        holder.points.setText(driver.getPoints());
        holder.wins.setText(driver.getWins());
        if(mList.indexOf(driver) % 2 != 1){
            holder.bgColor.setBackgroundColor(Color.parseColor("#545454"));
        }
        else{
            holder.bgColor.setBackgroundColor(Color.parseColor("#7e7e7e"));
        }




        return itemView;
    }

    static class ViewHolder {
        TextView name;
        TextView pos;
        TextView team;
        TextView wins;
        TextView points;
        LinearLayout bgColor;
    }
}


