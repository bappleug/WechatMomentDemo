package app.ray.wechatmements.ui.moments;

import java.util.List;

import app.ray.wechatmements.ui.moments.bean.Tweet;
import app.ray.wechatmements.ui.moments.bean.UserProfile;

/**
 * Created by Ray on 2017/11/12.
 */

public interface IMomentsView {

    /**
     * Simply show all error in the same form
     */
    void showError();

    void showAll(UserProfile userProfile, List<Tweet> tweets, boolean reachEnd);

    void refreshTweets(List<Tweet> tweets, boolean reachEnd);

    void moreTweets(List<Tweet> tweets, boolean reachEnd);

    void refreshUserProfile(UserProfile value);
}
