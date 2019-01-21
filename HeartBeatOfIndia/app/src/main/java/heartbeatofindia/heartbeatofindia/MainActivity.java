package heartbeatofindia.heartbeatofindia;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import heartbeatofindia.heartbeatofindia.adapters.SideMenuAdapter;
import heartbeatofindia.heartbeatofindia.coreactivity.MyAbstractActivity;
import heartbeatofindia.heartbeatofindia.fragments.HeadLines;
import heartbeatofindia.heartbeatofindia.modals.Category;
import heartbeatofindia.heartbeatofindia.modals.CategoryModal;
import heartbeatofindia.heartbeatofindia.modals.ResponseModal;
import heartbeatofindia.heartbeatofindia.servers.Constant;
import heartbeatofindia.heartbeatofindia.servers.Requestor;
import heartbeatofindia.heartbeatofindia.servers.ServerResponse;
import heartbeatofindia.heartbeatofindia.sharedpreference.PrefKeys;
import heartbeatofindia.heartbeatofindia.sharedpreference.PreferenceManger;
import heartbeatofindia.heartbeatofindia.vm.MainCategoryViewModal;
import heartbeatofindia.heartbeatofindia.vm.UpdateLocalDatabase;
import retrofit2.Call;

public class MainActivity extends MyAbstractActivity implements ServerResponse, SideMenuAdapter.OnCategoryClick {
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_login)
    AppCompatTextView tv_login;
    @BindView(R.id.tv_menu_home)
    AppCompatTextView tv_menu_home;
    @BindView(R.id.ll_home)
    LinearLayout ll_home;


    List<String> saveClicks = new ArrayList<>();

    MainCategoryViewModal mainCategoryViewModal;

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

        mainCategoryViewModal = ViewModelProviders.of(MainActivity.this).get(MainCategoryViewModal.class);

        mainCategoryViewModal.getMainCategory().observe(this, categories -> {
            // update UI
            if (categories != null) {
                recyclerView.setAdapter(new SideMenuAdapter(MainActivity.this, categories, this));
            }
        });
        loadFragment(new HeadLines(), R.id.flayout);

        loadAllCategory();
    }

    private void loadAllCategory() {
        Requestor requestor = new Requestor(Constant.GET_ALL_CATEGORY, this);
        Call<String> allCategory = Requestor.apis.getAllCategory();
        requestor.requestSendToServer(allCategory);
    }

    @Override
    public void initListeners() {

        ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HeadLines(), R.id.flayout);
                mDrawerLayout.closeDrawer(Gravity.START);


            }
        });

        if (!PreferenceManger.getPreferenceManger().getBoolean(PrefKeys.ISLOGIN))
            tv_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
        else tv_login.setText(PreferenceManger.getPreferenceManger().getString(PrefKeys.USERNAME));
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

            case Constant.GET_ALL_CATEGORY:
                if (o.toString() != null) {
                    Intent intent = new Intent(MainActivity.this, UpdateLocalDatabase.class);
                    intent.putExtra("category", o.toString());
                    startService(intent);
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
            if (mainCategoryViewModal != null) {
                LiveData<List<Category>> mainCategory = mainCategoryViewModal.getMainCategory();
                if (mainCategory != null && mainCategory.getValue() != null)
                    recyclerView.setAdapter(new SideMenuAdapter(MainActivity.this, mainCategory.getValue(), this));


            }
            HeadLines headLines = new HeadLines();
            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            headLines.setArguments(bundle);
            loadFragment(headLines, false);
        }

    }

    public void loadFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (addBackStack)
            fragmentTransaction.addToBackStack("home");
        fragmentTransaction.replace(R.id.flayout, fragment).commit();
    }
}
