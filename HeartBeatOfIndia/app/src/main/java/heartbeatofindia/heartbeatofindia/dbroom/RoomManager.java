package heartbeatofindia.heartbeatofindia.dbroom;

import android.arch.persistence.room.Room;
import android.content.Context;

public class RoomManager {
    private static AppDatabase appDatabase;

    public static void initRoomManager(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "news").build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
