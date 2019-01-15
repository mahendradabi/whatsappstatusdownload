package heartbeatofindia.heartbeatofindia.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.R;
import heartbeatofindia.heartbeatofindia.modals.Category;

public class HeadLineAdapter extends RecyclerView.Adapter<HeadLineAdapter.MyViewHolder> {

    Context mContex;
    List<Category> list;
    OnHeadLineItemClick headLineItemClick;

    public interface OnHeadLineItemClick {
        public void onHeadLineClick(String id);
    }


    public HeadLineAdapter(Context mContext, List<Category> list, OnHeadLineItemClick headLineItemClick) {
        this.mContex = mContext;
        this.list = list;
        this.headLineItemClick = headLineItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_single_textview, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_state_name.setText(list.get(position)
                .getName());
holder.tv_state_name.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        headLineItemClick.onHeadLineClick(list.get(position).getId());

    }
});

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_state_name)
        AppCompatTextView tv_state_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
