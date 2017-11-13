package app.ray.wechatmements.net;

import java.util.List;

import app.ray.wechatmements.ui.moments.bean.Tweet;
import app.ray.wechatmements.ui.moments.bean.UserProfile;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ray on 2017/11/10.
 */

public interface Api {

    @GET("user/{user_name}")
    public Single<UserProfile> getUserInfo(@Path("user_name") String userName);

    @GET("user/{user_name}/tweets")
    public Single<List<Tweet>> getTweets(@Path("user_name") String userName);
}
