package com.ness.wheather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import structure.rWheather;

/**
 * Created by stud27 on 17/07/16.
 */
public class WheatherAdapter extends RecyclerView.Adapter<WheatherAdapter.RedditViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<rWheather> data;

    public WheatherAdapter(Context context, ArrayList<rWheather> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RedditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.weather_item, parent, false);
        return new RedditViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RedditViewHolder holder, int position) {
        final rWheather weather = data.get(position);


        holder.tvTitle.setText(weather.getDt_txt());
        holder.tvTemp.setText(String.format("%.2f°C", weather.getTemp()));
        holder.tvPressure.setText(String.format("%.2f", weather.getPressure()));
        String fileName = "/fraw/" + weather.getIcon() + ".png";
        int resID = context.getResources().getIdentifier("f"+weather.getIcon(),
                "raw", context.getPackageName());
        //Log.d("fileFile", "f"+weather.getIcon() + ".png " + resID + " ");

        holder.ivReddit.setImageResource(resID);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class RedditViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        View itemView;
        RelativeLayout layout;
        ImageView ivReddit;
        TextView tvTitle;
        TextView tvTemp;
        TextView tvPressure;

        public RedditViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
            ivReddit = (ImageView) itemView.findViewById(R.id.ivReddit);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvTemp = (TextView) itemView.findViewById(R.id.tvTemp);
            tvPressure = (TextView) itemView.findViewById(R.id.tvPressure);
            tvTitle.setOnClickListener(this);
            tvTemp.setOnClickListener(this);
            ivReddit.setOnClickListener(this);
            layout.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.tvTitle);

        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "clicked=" + getAdapterPosition()+" "+view.getResources().getResourceName(view.getId()), Toast.LENGTH_SHORT).show();
        }
    }
}
