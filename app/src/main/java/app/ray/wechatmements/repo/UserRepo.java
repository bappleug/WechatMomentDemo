package app.ray.wechatmements.repo;

import javax.inject.Inject;

import app.ray.wechatmements.net.Api;
import app.ray.wechatmements.ui.moments.bean.UserProfile;
import app.ray.wechatmements.utils.ExecutorHelper;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ray on 2017/11/12.
 */

public class UserRepo {

    private static final String USER_NAME = "jsmith";

    @Inject
    Api api;

    @Inject
    public UserRepo() {

    }

    public String getUserName() {
        return USER_NAME;
    }

    public Single<UserProfile> getUserProfile() {
        return api.getUserInfo(USER_NAME)
                .subscribeOn(Schedulers.from(ExecutorHelper.BALANCE))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
