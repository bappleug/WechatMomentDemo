package app.ray.wechatmements.repo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.ray.wechatmements.net.Api;
import app.ray.wechatmements.ui.moments.bean.Tweet;
import app.ray.wechatmements.utils.CollectionUtils;
import app.ray.wechatmements.utils.ExecutorHelper;
import app.ray.wechatmements.utils.StringUtils;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ray on 2017/11/12.
 */

public class TweetsRepo {

    @Inject
    UserRepo userRepo;
    @Inject
    Api api;

    List<Tweet> tweetsCache;
    //TODO add disk cache

    @Inject
    public TweetsRepo() {
    }

    public Single<List<Tweet>> getTweets(int page, int count) {
        if (tweetsCache != null) {
            return Single.just(getByPage(tweetsCache, page, count));
        }

        return api.getTweets(userRepo.getUserName())
                .map(filterErrorItems())
                .doOnSuccess(tweets -> tweetsCache = tweets)
                .map(tweets -> getByPage(tweets, page, count))
                .subscribeOn(Schedulers.from(ExecutorHelper.BALANCE))
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Function<List<Tweet>, List<Tweet>> filterErrorItems(){
        return tweets -> {
            if (tweets == null) {
                tweets = new ArrayList<>();
            }
            for (int i = tweets.size() - 1; i >= 0; i--) {
                Tweet tweet = tweets.get(i);
                if (tweet.getSender() == null
                        || (StringUtils.isEmpty(tweet.getContent()) && CollectionUtils.isCollectionNullOrEmpty(tweet.getImages()))) {
                    tweets.remove(i);
                }
            }
            return tweets;
        };
    }

    private List<Tweet> getByPage(List<Tweet> source, int page, int count) {
        List<Tweet> pageItems = new ArrayList<>();
        if (page * count < source.size()) {
            for (int i = page * count; i < Math.min((page + 1) * count, source.size()); i++) {
                pageItems.add(source.get(i));
            }
        }
        return pageItems;
    }
}
