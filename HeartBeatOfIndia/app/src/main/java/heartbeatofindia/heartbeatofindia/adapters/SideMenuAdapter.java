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
import heartbeatofindia.heartbeatofindia.MainActivity;
import heartbeatofindia.heartbeatofindia.R;
import heartbeatofindia.heartbeatofindia.modals.Category;

public class SideMenuAdapter extends RecyclerView.Adapter<SideMenuAdapter.MyViewHolder> {

    Context mContex;
    List<Category> list;
    OnCategoryClick onCategoryClick;

    public interface OnCategoryClick {
        void onCategoryClicked(boolean isParent, String id);
    }

    public SideMenuAdapter(Context mContext, List<Category> list, OnCategoryClick onCategoryClick) {
        this.mContex = mContext;
        this.list = list;
        this.onCategoryClick = onCategoryClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_side_menu, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_menu_name.setText(list.get(position)
                .getName());

        if (onCategoryClick!=null)
        holder.tv_menu_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category category = list.get(position);
                if (category.getSub_cat_status()==1)
                    onCategoryClick.onCategoryClicked(true, category.getId());
                else onCategoryClick.onCategoryClicked(false,category.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_menu_name)
        AppCompatTextView tv_menu_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
