package heartbeatofindia.heartbeatofindia.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tv_news_des.setText(Html.fromHtml(list.get(position).getDescription().replaceAll("<img.+/(img)*>", ""), Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.tv_news_des.setText(Html.fromHtml(list.get(position).getDescription().replaceAll("<img.+/(img)*>", "")));

        }

        Picasso.get().load(list.get(position).getImage()).into(holder.img_news);

        holder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContex, WebViewActivity.class);
                intent.putExtra("url", list.get(position).getLink());
                mContex.startActivity(intent);


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
        @BindView(R.id.tv_news_des)
        AppCompatTextView tv_news_des;
        @BindView(R.id.img_news)
        AppCompatImageView img_news;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
