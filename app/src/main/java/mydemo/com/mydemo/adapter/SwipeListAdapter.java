package mydemo.com.mydemo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import mydemo.com.mydemo.R;
import mydemo.com.mydemo.model.Rows;

/**
 * Created by Namita on 7/23/2018.
 */
public class SwipeListAdapter extends RecyclerView.Adapter<SwipeListAdapter.MyViewHolder> {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Rows> rowsList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, desc;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            desc = (TextView) view.findViewById(R.id.textViewdesc);
            title = (TextView) view.findViewById(R.id.textViewtitle);
            imageView = (ImageView) view.findViewById(R.id.imageView);


        }
    }

    public SwipeListAdapter(Activity activity, List<Rows> rowsList) {
        this.activity = activity;
        this.rowsList = rowsList;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Rows rows = rowsList.get(position);


        holder.desc.setText(rows.getDescription());
        holder.title.setText(rows.getTitle());
        Picasso.with(activity).load(rows.getImageHref()).fit().placeholder(R.mipmap.ic_launcher).into(holder.imageView,
                new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }


    @Override
    public int getItemCount() {
        return rowsList.size();
    }
}

