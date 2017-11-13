package app.ray.wechatmements.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import app.ray.wechatmements.R;
import app.ray.wechatmements.utils.DensityUtils;

/**
 * Created by Ray on 2017/11/12.
 */

public class PullToRotateView extends android.support.v7.widget.AppCompatImageView {

    private float mPullPxPerRound;
    private AnimatorSet animatorSet;

    public PullToRotateView(Context context) {
        this(context, null);
    }

    public PullToRotateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        TypedArray a =context.obtainStyledAttributes(attrs, R.styleable.PullToRotateView);
        mPullPxPerRound = a.getFloat(R.styleable.PullToRotateView_ptr_rotateSpeed, DensityUtils.dip2px(context, 48));
        a.recycle();
    }

    public void rotateByPull(int distance){
        setRotation(distance / mPullPxPerRound * 360.0f);
    }

    public void startRotateAnim(Runnable actionOnEnd){
        if(animatorSet != null){
            animatorSet.removeAllListeners();
            animatorSet.cancel();
        }
        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        ValueAnimator roller = ValueAnimator.ofFloat(getRotation(), getRotation() + 360);
        roller.setDuration(2000);
        roller.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float rotation = (float) animation.getAnimatedValue();
                if(rotation > 360){
                    rotation -= 360;
                }
                setRotation(rotation);
            }
        });
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                actionOnEnd.run();
            }
        });
        animatorSet.play(roller);
        animatorSet.start();
    }

    public void cancelRotateAnim(){
        if(animatorSet != null){
            animatorSet.removeAllListeners();
            animatorSet.cancel();
        }
        animatorSet = null;
    }
}
