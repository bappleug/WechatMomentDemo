package app.ray.wechatmements.di;

import android.app.Application;

import javax.inject.Singleton;

import app.ray.wechatmements.WeChatMomentsApp;
import app.ray.wechatmements.ui.moments.MomentsModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Ray on 2017/11/10.
 */
@Singleton
@Component(modules = {AppModule.class, MomentsModule.class, AndroidInjectionModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(WeChatMomentsApp app);

}
