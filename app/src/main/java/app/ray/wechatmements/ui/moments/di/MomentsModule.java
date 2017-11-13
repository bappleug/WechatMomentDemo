package app.ray.wechatmements.ui.moments.di;

import app.ray.wechatmements.di.ViewScope;
import app.ray.wechatmements.repo.TweetsRepo;
import app.ray.wechatmements.repo.UserRepo;
import app.ray.wechatmements.ui.moments.MomentsActivity;
import app.ray.wechatmements.ui.moments.MomentsPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Ray on 2017/11/10.
 */
@Module
public class MomentsModule {

    private MomentsActivity mActivity;

    public MomentsModule(MomentsActivity activity) {
        this.mActivity = activity;
    }

    @ViewScope
    @Provides
    MomentsPresenter providesPresenter(TweetsRepo tweetsRepo, UserRepo userRepo){
        return new MomentsPresenter(mActivity, tweetsRepo, userRepo);
    }
}
