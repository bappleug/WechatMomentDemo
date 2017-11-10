package app.ray.wechatmements.ui.moments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.ray.wechatmements.R;
import butterknife.ButterKnife;

public class MomentActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void initView(){

    }
}
