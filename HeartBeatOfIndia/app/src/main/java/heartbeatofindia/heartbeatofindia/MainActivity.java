package heartbeatofindia.heartbeatofindia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.adapters.SideMenuAdapter;
import heartbeatofindia.heartbeatofindia.coreactivity.MyAbstractActivity;
import heartbeatofindia.heartbeatofindia.fragments.HeadLines;
import heartbeatofindia.heartbeatofindia.modals.Category;
import heartbeatofindia.heartbeatofindia.modals.CategoryModal;
import heartbeatofindia.heartbeatofindia.servers.Constant;
import heartbeatofindia.heartbeatofindia.servers.Requestor;
import heartbeatofindia.heartbeatofindia.servers.ServerResponse;
import retrofit2.Call;

public class MainActivity extends MyAbstractActivity implements ServerResponse, SideMenuAdapter.OnCategoryClick {
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<String> saveClicks = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //   recyclerView.setAdapter(new SideMenuAdapter());

        getSideMenus("0");

        loadFragment(new HeadLines(), R.id.flayout);

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
                        recyclerView.setAdapter(new SideMenuAdapter(MainActivity.this, result, this));
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
    public void onCategoryClicked(boolean isParent, String id) {
        if (isParent)
            getSideMenus(id);
        else {
            mDrawerLayout.closeDrawer(Gravity.START);
            getSideMenus("0");
            HeadLines headLines=new HeadLines();
            Bundle bundle=new Bundle();
            bundle.putString("id",id);
            headLines.setArguments(bundle);
            loadFragment(headLines,false);
        }

    }

    public void loadFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (addBackStack)
            fragmentTransaction.addToBackStack("home");
        fragmentTransaction.replace(R.id.flayout, fragment).commit();
    }
}
