package mydemo.com.mydemo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import mydemo.com.mydemo.adapter.SwipeListAdapter;
import mydemo.com.mydemo.commonutils.BusProvider;
import mydemo.com.mydemo.core.MyApplication;
import mydemo.com.mydemo.data.Constants;
import mydemo.com.mydemo.model.Listdata;
import mydemo.com.mydemo.model.Rows;
import mydemo.com.mydemo.network.OttoEvent;
import mydemo.com.mydemo.network.StringVollyRequest;
import mydemo.com.mydemo.network.ThreadUtils;

/**
 * Created by Namita on 7/23/2018.
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    private String TAG = MainActivity.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
private CoordinatorLayout coordinatorLayout;
    private SwipeListAdapter adapter;
    private List<Rows> rowsList;
private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mContext = this;
        BusProvider.getInstance().register(this);
        rowsList = new ArrayList<>();
        adapter = new SwipeListAdapter(this, rowsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        getListData();
                                    }
                                }
        );

    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        coordinatorLayout = coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        getListData();
    }

    private void getListData() {

        if(MyApplication.getInstance().isNetworkAvailable()){
        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        // Volley's json request object
            ThreadUtils.getDefaultExecutorService().submit(new StringVollyRequest(mContext,
                    Constants.URL, StringVollyRequest.ID_GET_ROW_DATA));
        }else{
            showSnackbar(Constants.ERROR_MESSAGE_NO_NETWORK);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void showSnackbar(String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    protected <T> void onResponse(T t, int id) throws Exception {
        Listdata listdata = (Listdata) t;
        rowsList.clear();
        getSupportActionBar().setTitle(listdata.getTitle());
        for (int i = 0; i < listdata.getRows().length; i++) {
            Rows rows =listdata.getRows()[i];

            rowsList.add(rows);
        }
        adapter.notifyDataSetChanged();
        // stopping swipe refresh
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onErrorResponse(String errorMsg) throws Exception {
        super.onErrorResponse(errorMsg);
        showSnackbar(errorMsg);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Subscribe
    public void onOttoEventReceived(OttoEvent event) throws Exception {
        super.onOttoEventReceived(event);
    }
}
