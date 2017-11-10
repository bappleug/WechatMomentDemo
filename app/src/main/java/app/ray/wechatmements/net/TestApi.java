package app.ray.wechatmements.net;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ray on 2017/11/10.
 */

public interface TestApi {

    @GET("user/{user_name}")
    public Single<UserProfileResponse> getUserInfo(@Path("user_name") String userName);

    @GET("user/{user_name}/tweets")
    public Single<List<TweetsResponse>> getTweets(@Path("user_name") String userName);
}
