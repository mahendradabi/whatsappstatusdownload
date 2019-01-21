package heartbeatofindia.heartbeatofindia;

import android.app.Application;

import heartbeatofindia.heartbeatofindia.dbroom.RoomManager;
import heartbeatofindia.heartbeatofindia.sharedpreference.PreferenceManger;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManger.initPreference(getApplicationContext());
        RoomManager.initRoomManager(getApplicationContext());
    }
}
