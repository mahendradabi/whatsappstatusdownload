package heartbeatofindia.heartbeatofindia.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.R;
import heartbeatofindia.heartbeatofindia.modals.Category;

public class SideMenuAdapter extends RecyclerView.Adapter<SideMenuAdapter.MyViewHolder> {

    Context mContex;
    List<Category> list;
    OnCategoryClick onCategoryClick;

    public interface OnCategoryClick {
        void onCategoryClicked(boolean isParent, String id,int parentId);
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

        Category category = list.get(position);
        holder.tv_menu_name.setText(category
                .getName());

        if (category.getCatImag() != null&&category.getCatImag().length()>0)
            Picasso.get().load(category.getCatImag())
                    .into(holder.img_category);
        if (category.getSub_cat_status() == 0)
            holder.img_arrow_right.setVisibility(View.GONE);
        else holder.img_arrow_right.setVisibility(View.VISIBLE);


        if (onCategoryClick != null)
            holder.ll_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Category category = list.get(position);
                    if (category.getSub_cat_status() == 1)
                        onCategoryClick.onCategoryClicked(true, category.getId()+"",category.getParentId());
                    else onCategoryClick.onCategoryClicked(false, category.getId()+"",category.getParentId());
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
        @BindView(R.id.img_arrow_right)
        AppCompatImageView img_arrow_right;
        @BindView(R.id.img_category)
        AppCompatImageView img_category;
        @BindView(R.id.ll_category)
        LinearLayout ll_category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
