package app.ray.wechatmements.ui.moments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.ray.wechatmements.R;
import app.ray.wechatmements.WeChatMomentsApp;
import app.ray.wechatmements.base.BaseActivity;
import app.ray.wechatmements.base.BasePresenter;
import app.ray.wechatmements.ui.moments.bean.MomentsItem;
import app.ray.wechatmements.ui.moments.bean.Tweet;
import app.ray.wechatmements.ui.moments.bean.UserProfile;
import app.ray.wechatmements.ui.moments.di.DaggerMomentsComponent;
import app.ray.wechatmements.ui.moments.di.MomentsModule;
import app.ray.wechatmements.widgets.LinearSpaceItemDecoration;
import app.ray.wechatmements.widgets.PullToRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MomentsActivity extends BaseActivity implements IMomentsView {

    @Inject
    MomentsPresenter mPresenter;

    @BindView(R.id.loPullRefresh)
    PullToRefreshLayout mLoPullRefresh;
    @BindView(R.id.rvMoments)
    RecyclerView mRrvMoments;
    private MomentsAdapter mAdapter;


    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        DaggerMomentsComponent.builder()
                .appComponent(WeChatMomentsApp.getInstance().getComponent())
                .momentsModule(new MomentsModule(this))
                .build()
                .inject(this);
        mAdapter = new MomentsAdapter(this, null);
        mRrvMoments.setAdapter(mAdapter);
        mRrvMoments.setLayoutManager(new LinearLayoutManager(this));
        mRrvMoments.addItemDecoration(new LinearSpaceItemDecoration(
                this,
                1,
                R.color.colorDivider,
                true,
                true));
        mRrvMoments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                    mPresenter.getTweets(false);
                }
            }
        });
        mLoPullRefresh.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onPull(RecyclerView recyclerView, int topOffset, int dy) {

            }

            @Override
            public void onRelease(boolean refresh) {
                if(refresh){
                    mPresenter.getTweets(true);
                }
            }
        });
        mPresenter.getAll();
    }

    @Override
    public void showError() {
        Toast.makeText(this, getString(R.string.fetch_data_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshUserProfile(UserProfile value) {
        //No such need yet
    }

    @Override
    public void showAll(UserProfile userProfile, List<Tweet> tweets, boolean reachEnd) {
        ArrayList<MomentsItem> items = new ArrayList<>();
        items.add(userProfile);
        items.addAll(tweets);
        mAdapter.refreshAll(items);
    }

    @Override
    public void refreshTweets(List<Tweet> tweets, boolean hasMore) {
        ArrayList<MomentsItem> items = new ArrayList<>();
        items.addAll(tweets);
        mAdapter.refreshTweets(items);
    }

    @Override
    public void moreTweets(List<Tweet> tweets, boolean hasMore) {
        ArrayList<MomentsItem> items = new ArrayList<>();
        items.addAll(tweets);
        mAdapter.addTweets(items);
    }


}
