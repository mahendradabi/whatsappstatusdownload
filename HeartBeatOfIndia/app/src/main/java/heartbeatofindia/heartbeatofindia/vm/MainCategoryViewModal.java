package heartbeatofindia.heartbeatofindia.vm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import heartbeatofindia.heartbeatofindia.modals.Category;
import heartbeatofindia.heartbeatofindia.modals.CategoryModal;
import heartbeatofindia.heartbeatofindia.servers.Constant;
import heartbeatofindia.heartbeatofindia.servers.Requestor;
import heartbeatofindia.heartbeatofindia.servers.ServerResponse;
import retrofit2.Call;

public class MainCategoryViewModal extends ViewModel implements ServerResponse {


    MutableLiveData<List<Category>> categorie;

    public MainCategoryViewModal() {
        loadMainCategory();
    }

    public LiveData<List<Category>> getMainCategory() {
        if (categorie == null) {
            categorie = new MutableLiveData<>();
        }
        return categorie;
    }

    private void loadMainCategory() {
        Requestor requestor =
                new Requestor(Constant.GET_CATEGORY, this);
        requestor.setClassOf(CategoryModal.class);
        Call<String> category = Requestor.apis.getCategory("0");
        requestor.requestSendToServer(category);

    }

    @Override
    public void success(Object o, int code) {

        switch (code) {
            case Constant.GET_CATEGORY:
                CategoryModal categoryModal = (CategoryModal) o;
                if (categoryModal != null && categoryModal.isStatus()) {
                    List<Category> result = categoryModal.getResult();
                    if (result != null) {
                        categorie.setValue(result);
                    }

                }
                break;
        }

    }

    @Override
    public void error(Object o, int code) {

    }

}
