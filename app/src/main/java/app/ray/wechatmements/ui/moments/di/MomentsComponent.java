package app.ray.wechatmements.ui.moments.di;

import app.ray.wechatmements.di.AppComponent;
import app.ray.wechatmements.di.ViewScope;
import app.ray.wechatmements.ui.moments.MomentsActivity;
import dagger.Component;

/**
 * Created by Ray on 2017/11/12.
 */
@ViewScope
@Component(dependencies = AppComponent.class, modules = MomentsModule.class)
public interface MomentsComponent {

    void inject(MomentsActivity activity);

}
