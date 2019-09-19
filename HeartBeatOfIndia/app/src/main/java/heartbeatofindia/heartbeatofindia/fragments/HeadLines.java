package heartbeatofindia.heartbeatofindia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prof.rssparser.Article;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.ActivityAdFull;
import heartbeatofindia.heartbeatofindia.R;
import heartbeatofindia.heartbeatofindia.adapters.HeadLineAdapter;
import heartbeatofindia.heartbeatofindia.adapters.NewsAdapter;
import heartbeatofindia.heartbeatofindia.adapters.RssAdapter;
import heartbeatofindia.heartbeatofindia.coreactivity.MyAbstractFragment;
import heartbeatofindia.heartbeatofindia.modals.Ad;
import heartbeatofindia.heartbeatofindia.modals.AdListModel;
import heartbeatofindia.heartbeatofindia.modals.Category;
import heartbeatofindia.heartbeatofindia.modals.CategoryModal;
import heartbeatofindia.heartbeatofindia.modals.NewsPost;
import heartbeatofindia.heartbeatofindia.modals.NewsPostModal;
import heartbeatofindia.heartbeatofindia.servers.Constant;
import heartbeatofindia.heartbeatofindia.servers.Requestor;
import heartbeatofindia.heartbeatofindia.servers.ServerResponse;
import retrofit2.Call;

public class HeadLines extends MyAbstractFragment implements ServerResponse, HeadLineAdapter.OnHeadLineItemClick {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.state_list)
    RecyclerView stateList;

    @BindView(R.id.img_ad)
    AppCompatImageView imgAD;
    private NewsAdapter newsAdapter;


    Parser parser;

    List<NewsPost> newsPostList = new ArrayList<>();

    public List<String> clickedIDs = new ArrayList<>();

    private int mInterval = 5000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    //
    //url of RSS feed
    // https://news.google.com/rss/search?q=technology&hl=hi&gl=IN&ceid=IN:hi
    private String url = "https://aajtak.intoday.in/rssfeeds/?feed=rss1.0&no_html=1&rsspage=home";
    Runnable mStatusChecker;

    int adPostion = -1;
    List<Ad> adList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment_headline, container, false);
        initViews(view);
        initListeners();
        return view;
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    @Override
    public void initViews(View view) {
        ButterKnife.bind(this, view);
        mHandler = new Handler();

        mStatusChecker = new Runnable() {
            @Override
            public void run() {
                try {
                    //this function can change value of mInterval.
                } finally {
                    // your update method throws an exception
                    mHandler.postDelayed(mStatusChecker, mInterval);
                }
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stateList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        newsAdapter = new NewsAdapter(getActivity(), newsPostList);
        recyclerView.setAdapter(newsAdapter);

        getSideMenus("1");
        if (getArguments() == null) {
            parser = new Parser();

            parser.onFinish(new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(List<Article> list) {
                    if (list != null)
                        recyclerView.setAdapter(new RssAdapter(getActivity(), list));
                }

                @Override
                public void onError(Exception e) {
                    Log.d("tag", e.toString());
                }
            });

            parser.execute(url);
        } else {
            Bundle arguments = getArguments();
            String id = arguments.getString("id");

            getNewsPost(id);

        }


    }

    @Override
    public void initListeners() {

    }

    @Override
    public void success(Object o, int code) {

        switch (code) {
            case Constant.GET_CATEGORY:
                CategoryModal categoryModal = (CategoryModal) o;
                if (categoryModal != null && categoryModal.isStatus()) {
                    List<Category> result = categoryModal.getResult();
                    if (result != null) {
                        stateList.setAdapter(new HeadLineAdapter(getActivity(), result, this));
                    }

                }
                break;
            case Constant.GET_NEWS:
                NewsPostModal
                        newsPostModal = (NewsPostModal) o;
                if (newsPostModal != null && newsPostModal.isStatus()) {
                    List<NewsPost> posts = newsPostModal.getPosts();
                    if (posts != null) {
                        newsAdapter.setList(posts);
                    }
                } else Toast.makeText(getActivity(), "No news found", Toast.LENGTH_SHORT).show();
                break;

            case Constant.AD:
                AdListModel adListModel = (AdListModel) o;
                if (adListModel != null)
                    updateAdView(adListModel);
                else imgAD.setVisibility(View.GONE);
                break;
        }

    }

    private void updateAdView(AdListModel adListModel) {
        if (isVisible()) {
            adPostion++;
            adList = adListModel.getAdList();
            if (adList != null && adPostion < adList.size()) {
                Ad ad = adList.get(adPostion);
                imgAD.setVisibility(View.VISIBLE);
                Picasso.get().load(ad.getImage()).into(imgAD);
                imgAD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ActivityAdFull.class).putExtra("url",ad.getImage()));
                    }
                });
                refreshAD();

            }

        } else return;
    }

    private void refreshAD() {
        adPostion++;

        if (adList != null) {
            if (adPostion < adList.size()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Ad ad = adList.get(adPostion);
                        imgAD.setVisibility(View.VISIBLE);
                        Picasso.get().load(ad.getImage()).into(imgAD);
                        imgAD.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getActivity(), ActivityAdFull.class).putExtra("url",ad.getImage()));
                            }
                        });
                        refreshAD();
                    }
                }, 10 * 1000);

            } else {
                adPostion = -1;
                refreshAD();
            }
        } else {

            imgAD.setVisibility(View.GONE);
        }
    }

    @Override
    public void error(Object o, int code) {
        //   Toast.makeText(getActivity(), o.toString(), Toast.LENGTH_SHORT).show();
    }


    private void getSideMenus(String id) {
        Requestor requestor =
                new Requestor(Constant.GET_CATEGORY, this);
        requestor.setClassOf(CategoryModal.class);
        Call<String> category = Requestor.apis.getCategory(id);
        requestor.requestSendToServer(category);


    }

    @Override
    public void onHeadLineClick(String id) {
        getNewsPost(id);

    }


    private void getNewsPost(String categorId) {
        Requestor requestor
                = new Requestor(Constant.GET_NEWS, this);
        requestor.setClassOf(NewsPostModal.class);

        Call<String> newsPost = Requestor.apis.getNewsPost(categorId);
        requestor.requestSendToServer(newsPost);
        loadCategoryAd(categorId);

    }

    public void loadCategoryAd(String categoryID) {
        Requestor requestor = new Requestor(Constant.AD, this);
        requestor.setClassOf(AdListModel.class);
        Call<String> adByCategory = Requestor.apis.getAdByCategory(categoryID);
        requestor.requestSendToServer(adByCategory);
    }
}
