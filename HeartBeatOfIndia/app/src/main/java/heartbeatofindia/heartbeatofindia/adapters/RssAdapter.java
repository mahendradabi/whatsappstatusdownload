package heartbeatofindia.heartbeatofindia.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.prof.rssparser.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.MainActivity;
import heartbeatofindia.heartbeatofindia.R;
import heartbeatofindia.heartbeatofindia.WebViewActivity;
import heartbeatofindia.heartbeatofindia.fragments.NewsDetails;
import heartbeatofindia.heartbeatofindia.modals.NewsPost;

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.MyViewHolder> {
    Context mContex;
    MainActivity mainActivity;

    List<Article> list;

    public RssAdapter(Context mContex, List<Article> list) {
        this.mContex = mContex;
        this.list = list;
        mainActivity = (MainActivity) mContex;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_news, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_news_title.setText(list.get(position).getTitle());
        Picasso.get().load(list.get(position).getImage()).into(holder.img_news);

        holder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContex, WebViewActivity.class);
                intent.putExtra("url", list.get(position).getLink());
                mContex.startActivity(intent);
               /* Bundle bundle=new Bundle();
                bundle.putSerializable("news",list.get(position));
                NewsDetails newsDetails = new NewsDetails();
                newsDetails.setArguments(bundle);
                mainActivity.loadFragment(newsDetails, true);*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_top)
        LinearLayout ll_top;
        @BindView(R.id.tv_news_title)
        AppCompatTextView tv_news_title;
        @BindView(R.id.img_news)
        AppCompatImageView img_news;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
