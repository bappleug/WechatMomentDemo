package app.ray.wechatmements.base;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ray on 2017/11/12.
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected abstract BasePresenter getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BasePresenter presenter = getPresenter();
        if(presenter != null){
            presenter.onDestroy();
        }
    }
}
