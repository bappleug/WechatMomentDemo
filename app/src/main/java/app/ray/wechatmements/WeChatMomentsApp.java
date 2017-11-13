package app.ray.wechatmements;

import android.app.Application;

import app.ray.wechatmements.di.AppComponent;
import app.ray.wechatmements.di.AppModule;
import app.ray.wechatmements.di.DaggerAppComponent;

/**
 * Created by Ray on 2017/11/10.
 */

public class WeChatMomentsApp extends Application {

    private static WeChatMomentsApp sApp;
    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        mComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public static WeChatMomentsApp getInstance(){
        return sApp;
    }

    public AppComponent getComponent(){
        return mComponent;
    }
}
