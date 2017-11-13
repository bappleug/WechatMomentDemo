package app.ray.wechatmements.di;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import app.ray.wechatmements.GlobalConfig;
import app.ray.wechatmements.net.Api;
import app.ray.wechatmements.net.utils.FakeX509TrustManager;
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
public class AppModule {

    @Provides
    @Singleton
    LoggingInterceptor getInterceptor(){
        return new LoggingInterceptor();
    }

    @Provides
    @Singleton
    @Named("FakeX509TrustManager")
    X509TrustManager getX509TrustManager(){
        return new FakeX509TrustManager();
    }

    @Provides
    @Singleton
    @Named("FakeSslSocketFactory")
    SSLSocketFactory getSslSocketFactory(@Named("FakeX509TrustManager") X509TrustManager trustManager){
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null,  new TrustManager[] {trustManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            //do nothing
        }
        return ssfFactory;
    }

    @Provides
    @Singleton
    @Named("TestOkHttpClient")
    OkHttpClient getOkHttpClient(LoggingInterceptor loggingInterceptor,
                                 @Named("FakeSslSocketFactory") SSLSocketFactory sslSocketFactory,
                                 @Named("FakeX509TrustManager") X509TrustManager trustManager) {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(loggingInterceptor)
                .sslSocketFactory(sslSocketFactory, trustManager)
                .build();
    }

    @Singleton
    @Provides
    Api providesTestApi(@Named("TestOkHttpClient") OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(GlobalConfig.TEST_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(Api.class);
    }
}
