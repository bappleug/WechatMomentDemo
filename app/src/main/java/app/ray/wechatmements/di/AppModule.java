package app.ray.wechatmements.di;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import app.ray.wechatmements.GlobalConfig;
import app.ray.wechatmements.net.TestApi;
import app.ray.wechatmements.net.utils.LoggingInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ray on 2017/11/10.
 */
@Module
class AppModule {

    @Provides
    @Singleton
    LoggingInterceptor getInterceptor(){
        return new LoggingInterceptor();
    }

    @Provides
    @Singleton
    OkHttpClient getOkHttpClient(LoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(loggingInterceptor)
                .build();
    }

    @Singleton
    @Provides
    TestApi providesTestApi(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(GlobalConfig.TEST_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(TestApi.class);
    }
}
