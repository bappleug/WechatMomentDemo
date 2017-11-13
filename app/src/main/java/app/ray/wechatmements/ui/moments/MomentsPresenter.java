package app.ray.wechatmements.ui.moments;

import android.util.Pair;

import java.util.List;

import app.ray.wechatmements.base.BasePresenter;
import app.ray.wechatmements.repo.TweetsRepo;
import app.ray.wechatmements.repo.UserRepo;
import app.ray.wechatmements.ui.moments.bean.Tweet;
import app.ray.wechatmements.ui.moments.bean.UserProfile;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

/**
 * Created by Ray on 2017/11/12.
 */

public class MomentsPresenter extends BasePresenter {

    private static final int PAGE_COUNT = 5;
    private TweetsRepo mTweetsRepo;
    private UserRepo mUserRepo;
    private IMomentsView mView;
    private CompositeDisposable mDisposable;
    private int mNextPage = 0;
    private boolean isRefreshing = false;

    public MomentsPresenter(IMomentsView view, TweetsRepo tweetsRepo, UserRepo userRepo) {
        this.mView = view;
        mDisposable = new CompositeDisposable();
        this.mTweetsRepo = tweetsRepo;
        this.mUserRepo = userRepo;
    }

    public void getAll() {
        if(isRefreshing){
            return;
        }
        mNextPage = 0;
        Single<UserProfile> userProfileSingle = mUserRepo.getUserProfile();
        Single<List<Tweet>> tweetsSingle = mTweetsRepo.getTweets(0, PAGE_COUNT);
        userProfileSingle.zipWith(tweetsSingle, new BiFunction<UserProfile, List<Tweet>, Pair<UserProfile, List<Tweet>>>() {
            @Override
            public Pair<UserProfile, List<Tweet>> apply(UserProfile userProfile, List<Tweet> tweets) throws Exception {
                return new Pair<>(userProfile, tweets);
            }
        })
        .subscribe(new SingleObserver<Pair<UserProfile, List<Tweet>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable.add(d);
                isRefreshing = true;
            }

            @Override
            public void onSuccess(Pair<UserProfile, List<Tweet>> value) {
                UserProfile userProfile = value.first;
                List<Tweet> tweets = value.second;
                boolean reachEnd = tweets.size() == 0;
                if(!reachEnd){
                    mNextPage++;
                }
                mView.showAll(userProfile, tweets, reachEnd);
                isRefreshing = false;
            }

            @Override
            public void onError(Throwable e) {
                mView.showError();
                isRefreshing = false;
            }
        });
    }

    public void getUserProfile() {
        if(isRefreshing){
            return;
        }
        resetRequest();
        mUserRepo.getUserProfile()
                .subscribe(new SingleObserver<UserProfile>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                        isRefreshing = true;
                    }

                    @Override
                    public void onSuccess(UserProfile value) {
                        mView.refreshUserProfile(value);
                        isRefreshing = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                        isRefreshing = false;
                    }
                });
    }

    public void getTweets(boolean isRefresh) {
        if(isRefreshing){
            return;
        }
        resetRequest();
        if (isRefresh) {
            mNextPage = 0;
        }
        mTweetsRepo.getTweets(mNextPage, PAGE_COUNT)
                .subscribe(new SingleObserver<List<Tweet>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        isRefreshing = true;
                        mDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Tweet> value) {
                        boolean reachEnd = value.size() == 0;
                        if (!reachEnd) {
                            mNextPage++;
                        }
                        if (isRefresh) {
                            mView.refreshTweets(value, reachEnd);
                        } else {
                            mView.moreTweets(value, reachEnd);
                        }
                        isRefreshing = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                        isRefreshing = false;
                    }
                });
    }


    private void resetRequest(){
        mDisposable.dispose();
    }

    @Override
    protected void onDestroy() {
        resetRequest();
    }
}
