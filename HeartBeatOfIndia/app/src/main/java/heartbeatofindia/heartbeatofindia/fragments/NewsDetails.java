package heartbeatofindia.heartbeatofindia.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.R;
import heartbeatofindia.heartbeatofindia.coreactivity.MyAbstractFragment;
import heartbeatofindia.heartbeatofindia.modals.NewsPost;

public class NewsDetails extends MyAbstractFragment {
    @BindView(R.id.tv_title)
    AppCompatTextView tv_title;

    @BindView(R.id.tv_news_des)
    AppCompatTextView tv_news_des;
    @BindView(R.id.img_feature)
    AppCompatImageView img_feature;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void initViews(View view) {
        ButterKnife.bind(this, view);
        Bundle arguments = getArguments();

        if (arguments != null) {
            NewsPost newsPost = (NewsPost) arguments.getSerializable("news");
            if (newsPost != null) {
                tv_title.setText(newsPost.getTitle());
                tv_news_des.setText(newsPost.getDes());
                if (newsPost.getImage()!=null&&newsPost.getImage().length()>0)
                Picasso.get().load(newsPost.getImage()).placeholder(R.mipmap.ic_launcher)
                        .into(img_feature);
            }
        }

    }

    @Override
    public void initListeners() {

    }
}
