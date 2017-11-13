package app.ray.wechatmements.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Ray on 2017/9/18.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ViewScope {
}
