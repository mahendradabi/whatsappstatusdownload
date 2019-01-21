package heartbeatofindia.heartbeatofindia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.coreactivity.MyAbstractActivity;

public class WebViewActivity extends MyAbstractActivity {

    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        setToolbar(toolbar);
        showBackButton();

        if (getIntent() != null && getIntent().hasExtra("url")) {
            String url = getIntent().getStringExtra("url");
            if (url != null) {

                webView.loadUrl(url);
            }
        }
    }

    @Override
    public void initListeners() {

    }
}
