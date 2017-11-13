package app.ray.wechatmements.di;

import javax.inject.Singleton;

import app.ray.wechatmements.net.Api;
import dagger.Component;

/**
 * Created by Ray on 2017/11/10.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Api getApi();
}
