package heartbeatofindia.heartbeatofindia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.coreactivity.MyAbstractActivity;

public class ActivityAdFull extends MyAbstractActivity {
    @BindView(R.id.img_ad)
    AppCompatImageView adImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_full_ad);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("url");
        Picasso.get().load(url).into(adImage);

    }

    @Override
    public void initListeners() {

    }
}
