package heartbeatofindia.heartbeatofindia;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import heartbeatofindia.heartbeatofindia.dbroom.CategoryDao;
import heartbeatofindia.heartbeatofindia.dbroom.RoomManager;
import heartbeatofindia.heartbeatofindia.fragments.FragmentCategoryList;
import heartbeatofindia.heartbeatofindia.fragments.HeadLines;
import heartbeatofindia.heartbeatofindia.modals.AdListModel;
import heartbeatofindia.heartbeatofindia.modals.Category;
import heartbeatofindia.heartbeatofindia.modals.CategoryModal;
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
    public DrawerLayout mDrawerLayout;
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
    @BindView(R.id.ll_ad_post)
    LinearLayout ll_ad_post;

    public static String clickID;


    public static List<Integer> saveClicks = new ArrayList<>();

    MainCategoryViewModal mainCategoryViewModal;

    CategoryDao categoryDao;

    @BindView(R.id.tabLyout1)
    TabLayout tabLayout1;
    @BindView(R.id.tabLyout2)
    TabLayout tabLayout2;


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

        setTitle("Heart Beat of India");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //   recyclerView.setAdapter(new SideMenuAdapter());

        mainCategoryViewModal = ViewModelProviders.of(MainActivity.this).get(MainCategoryViewModal.class);

        mainCategoryViewModal.getMainCategory().observe(this, categories -> {
            // update UI
            if (categories != null) {
                recyclerView.setAdapter(new SideMenuAdapter(MainActivity.this, categories, this));
            }
        });

        categoryDao = RoomManager.getAppDatabase().categoryDao();

        loadFragment(new HeadLines(), R.id.flayout);

        loadAllCategory();

        loadFragment(FragmentCategoryList.getInstace("0"), R.id.fl_category);
    }

    private void loadAllCategory() {
        Requestor requestor = new Requestor(Constant.GET_ALL_CATEGORY, this);
        Call<String> allCategory = Requestor.apis.getAllCategory();
        requestor.requestSendToServer(allCategory);
    }

    @Override
    public void initListeners() {

        tabLayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int cid = (Integer) tab.getTag();
                tabLayout2.removeAllTabs();
                if (cid != 0) {
                    heartbeatofindia.heartbeatofindia.dbroom.Category byId = categoryDao.findById(cid);
                    if (byId != null && byId.getSubCatStatus() == 1) {
                        List<heartbeatofindia.heartbeatofindia.dbroom.Category> allCateByParentID = categoryDao.getAllCateByParentID(byId.getCid());
                        for (heartbeatofindia.heartbeatofindia.dbroom.Category category : allCateByParentID) {
                            TabLayout.Tab tab1 = tabLayout2.newTab().setText(category.getName());
                            tab1.setTag(category.getCid());
                            if (saveClicks.size() > 0 && saveClicks.get(1) != null && saveClicks.get(1).equals(category.getCid()))
                                tab1.select();
                            tabLayout2.addTab(tab1);
                        }
                        tabLayout2.setVisibility(View.VISIBLE);
                    } else {
                        tabLayout2.removeAllTabs();
                        tabLayout2.setVisibility(View.GONE);
                        HeadLines headLines = new HeadLines();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", cid + "");
                        headLines.setArguments(bundle);
                        loadFragment(headLines, false);
                    }

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                HeadLines headLines = new HeadLines();
                Bundle bundle = new Bundle();
                bundle.putString("id", tab.getTag().toString() + "");
                headLines.setArguments(bundle);
                loadFragment(headLines, false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabLayout1.setVisibility(View.GONE);
                tabLayout2.setVisibility(View.GONE);
                loadFragment(new HeadLines(), R.id.flayout);
                mDrawerLayout.closeDrawer(Gravity.START);


            }
        });

        ll_ad_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabLayout1.setVisibility(View.GONE);
                tabLayout2.setVisibility(View.GONE);
                startActivity(new Intent(MainActivity.this, ActivityAdPost.class));
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
    public void onCategoryClicked(boolean isParent, String id, int parentID) {

        saveClicks.add(parentID);
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

    public void setTab1(List<heartbeatofindia.heartbeatofindia.dbroom.Category> list) {
        removeTabs1();
        int postiong = 0;
        int selectedTabPostion = -1;
        for (heartbeatofindia.heartbeatofindia.dbroom.Category category : list) {
            TabLayout.Tab tab = tabLayout1.newTab().setText(category.getName());
            tab.setTag(category.getCid());
            if (clickID.equals(category.getCid() + "")) {
                selectedTabPostion = postiong;
            }
            tabLayout1.addTab(tab);
            postiong++;
        }

        if (selectedTabPostion > -1)
            tabLayout1.getTabAt(selectedTabPostion).select();

        tabLayout1.setVisibility(View.VISIBLE);
    }

    public void removeTabs1() {
        if (tabLayout1 != null && tabLayout1.getTabCount() > 0) tabLayout1.removeAllTabs();
        getSupportFragmentManager().popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        loadFragment(FragmentCategoryList.getInstace("0"), R.id.fl_category);


    }


}
