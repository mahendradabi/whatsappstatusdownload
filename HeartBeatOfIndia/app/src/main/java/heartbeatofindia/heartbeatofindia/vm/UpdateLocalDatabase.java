package heartbeatofindia.heartbeatofindia.vm;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import heartbeatofindia.heartbeatofindia.dbroom.CategoryDao;
import heartbeatofindia.heartbeatofindia.dbroom.RoomManager;
import heartbeatofindia.heartbeatofindia.modals.Category;
import heartbeatofindia.heartbeatofindia.modals.CategoryModal;

public class UpdateLocalDatabase extends IntentService {
    private String serverResponse;
    private CategoryDao categoryDao;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateLocalDatabase(String name) {
        super(name);
    }

    public UpdateLocalDatabase() {
        super("UpdateLocalDatabase");

    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.hasExtra("category")) {
            serverResponse = intent.getStringExtra("category");
            try {
                if (categoryDao == null)
                    categoryDao = RoomManager.getAppDatabase().categoryDao();
                Gson gson = new Gson();
                CategoryModal modal = gson.fromJson(serverResponse, CategoryModal.class);
                if (modal != null) {
                    List<Category> result = modal.getResult();
                    if (result != null) {
                        categoryDao.deleteAll();
                        for (Category category : result) {
                            heartbeatofindia.heartbeatofindia.dbroom.Category byId = categoryDao.findById(category.getId());
                            heartbeatofindia.heartbeatofindia.dbroom.Category newCat;
                            if (byId == null) {
                                newCat = new heartbeatofindia.heartbeatofindia.dbroom.Category();
                                newCat.setCid(category.getId());
                                newCat.setParentId(category.getParentId());
                                newCat.setName(category.getName());
                                newCat.setStatus(category.getStatus());
                                newCat.setSubCatStatus(category.getSub_cat_status());
                                categoryDao.insertAll(newCat);
                            }

                        }



                    }
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }


}
