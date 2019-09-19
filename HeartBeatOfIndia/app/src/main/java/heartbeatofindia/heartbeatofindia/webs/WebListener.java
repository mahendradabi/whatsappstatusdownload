package heartbeatofindia.heartbeatofindia.webs;

/**
 * Created by Ashiq on 4/19/2017.
 */

public interface WebListener {

    public void onStart();

    public void onLoaded();

    public void onProgress(int progress);

    public void onNetworkError();

    public void onPageTitle(String title);
}
