package heartbeatofindia.heartbeatofindia.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prof.rssparser.Article;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.MainActivity;
import heartbeatofindia.heartbeatofindia.R;
import heartbeatofindia.heartbeatofindia.adapters.HeadLineAdapter;
import heartbeatofindia.heartbeatofindia.adapters.NewsAdapter;
import heartbeatofindia.heartbeatofindia.adapters.RssAdapter;
import heartbeatofindia.heartbeatofindia.adapters.SideMenuAdapter;
import heartbeatofindia.heartbeatofindia.coreactivity.MyAbstractFragment;
import heartbeatofindia.heartbeatofindia.dbroom.CategoryDao;
import heartbeatofindia.heartbeatofindia.dbroom.RoomManager;
import heartbeatofindia.heartbeatofindia.modals.Category;
import heartbeatofindia.heartbeatofindia.modals.CategoryModal;
import heartbeatofindia.heartbeatofindia.modals.NewsPost;
import heartbeatofindia.heartbeatofindia.modals.NewsPostModal;
import heartbeatofindia.heartbeatofindia.servers.Constant;
import heartbeatofindia.heartbeatofindia.servers.Requestor;
import heartbeatofindia.heartbeatofindia.servers.ServerResponse;
import retrofit2.Call;

public class FragmentCategoryList extends MyAbstractFragment implements ServerResponse, SideMenuAdapter.OnCategoryClick {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String categroyId;
    MainActivity mainActivity;
    CategoryDao categoryDao;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    public static Fragment getInstace(String categoryId) {
        Bundle bundle = new Bundle();
        bundle.putString("category", categoryId);
        Fragment frg = new FragmentCategoryList();
        frg.setArguments(bundle);
        return frg;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_common_recylerview, container, false);
        initViews(view);
        initListeners();
        return view;
    }


    @Override
    public void initViews(View view) {
        ButterKnife.bind(this, view);
        categoryDao = RoomManager.getAppDatabase().categoryDao();


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        handleBundle();

    }

    private void handleBundle() {
        Bundle arguments = getArguments();
        if (arguments != null && arguments.getString("category") != null) {
            categroyId = arguments.getString("category");
            getSideMenus(categroyId);
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
                        recyclerView.setAdapter(new SideMenuAdapter(getActivity(), result, FragmentCategoryList.this));
                    }

                }
                break;

        }

    }

    @Override
    public void error(Object o, int code) {
    }


    private void getSideMenus(String id) {
        Requestor requestor =
                new Requestor(Constant.GET_CATEGORY, this);
        requestor.setClassOf(CategoryModal.class);
        Call<String> category = Requestor.apis.getCategory(id);
        requestor.requestSendToServer(category);


    }


    @Override
    public void onCategoryClicked(boolean isParent, String id, int parentID) {


        MainActivity.saveClicks.add(Integer.parseInt(id));
        if (isParent)
            mainActivity.loadFragmentLayoutBack(getInstace(id), R.id.fl_category, true);
        else {
            mainActivity.mDrawerLayout.closeDrawer(Gravity.START);
            HeadLines headLines = new HeadLines();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            headLines.setArguments(bundle);
            mainActivity.loadFragment(headLines, false);
            MainActivity.clickID = id;
            if (parentID>0)
            findParents(parentID);
            else mainActivity.removeTabs1();

           /* if (parentID > 0)
                handleTabs(parentID);
            else mainActivity.removeTabs1();*/

        }
    }

    private void findParents(int pid) {
        heartbeatofindia.heartbeatofindia.dbroom.Category byId = categoryDao.findById(pid);
        List<heartbeatofindia.heartbeatofindia.dbroom.Category> allCateByParentID=null;
        if (byId.getParentId()>0)
        allCateByParentID = categoryDao.getAllCateByParentID(byId.getParentId());
        else allCateByParentID=categoryDao.getAllCateByParentID(pid);
        if (allCateByParentID!=null)
        mainActivity.setTab1(allCateByParentID);


    }


    private void handleTabs(int parentId) {
        mainActivity.removeTabs1();
        List<heartbeatofindia.heartbeatofindia.dbroom.Category> allCateByParentID = categoryDao.getAllCateByParentID(parentId);
        mainActivity.setTab1(allCateByParentID);
        if (MainActivity.saveClicks.size() > 0) {
            MainActivity.saveClicks.clear();

        }
    }


}
