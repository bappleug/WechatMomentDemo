package app.ray.wechatmements.widgets;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import app.ray.wechatmements.R;

/**
 * Created by Ray on 2017/11/10.
 *
 * Only RecyclerView is supported now
 */
public class PullToRefreshLayout extends FrameLayout {

    private int TOP_HIDE_HEIGHT;
    private int PULL_TO_REFRESH_HEIGHT;

    private RecyclerView mRecyclerView;
    private PullToRotateView mPullToRollView;
    private ViewDragHelper mDragger;
    private int mPullDistance;
    private boolean isDragging = false;
    private boolean isPositionHolding = false;
    private AnimatorSet mSlideUpAnimator;
    private OnPullListener mListener;


    public PullToRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public PullToRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public PullToRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attributeSet, int defStyleAttr){
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.PullToRefreshLayout, defStyleAttr, 0);
        TOP_HIDE_HEIGHT = typedArray.getDimensionPixelSize(R.styleable.PullToRefreshLayout_ptr_topHideHeight, 0);
        typedArray.recycle();
        PULL_TO_REFRESH_HEIGHT = context.getResources().getDimensionPixelSize(R.dimen.pull_to_roll_view_margin_top_expand)
                - context.getResources().getDimensionPixelSize(R.dimen. pull_to_roll_view_margin_top_hide);
        mDragger = ViewDragHelper.create(this, 1.0f, new DraggerCallback());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(mRecyclerView != null){
            return;
        }
        if(!(getChildAt(0) instanceof RecyclerView)){
            throw new IllegalStateException("Only RecyclerView as first child is acceptable");
        }
        if(!(getChildAt(1) instanceof PullToRotateView)){
            throw new IllegalStateException("Only PullToRollingView as second child is acceptable");
        }
        mRecyclerView = (RecyclerView) getChildAt(0);
        mPullToRollView = (PullToRotateView) getChildAt(1);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    private final class DraggerCallback extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if(isPositionHolding){
                return false;
            }
            if(child == mRecyclerView){
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                if(layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    resetRotateView();
                    isDragging = true;
                    return true;
                }
            }
            return false;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //Not allowed
            return 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //disallow push up
            if(top < -TOP_HIDE_HEIGHT){
                return top - dy;
            }
            //Reduce view movement to half of finger movement to simulate friction
            return top - dy / 2;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            if (child == mRecyclerView) {
                return 1;
            }
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            isDragging = false;
            boolean shouldTriggerRefresh = mPullDistance >= PULL_TO_REFRESH_HEIGHT;
            if(mListener != null){
                mListener.onRelease(shouldTriggerRefresh);
            }
            if(shouldTriggerRefresh){
                mPullToRollView.startRotateAnim(() -> hideUpRotateView());
            } else {
                hideUpRotateView();
            }
            if(mDragger.settleCapturedViewAt(0, -TOP_HIDE_HEIGHT)){
                isPositionHolding = true;
                invalidate();
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mPullDistance = top + TOP_HIDE_HEIGHT;
            if(isDragging){
                if(mPullDistance < PULL_TO_REFRESH_HEIGHT){
                    //Pull down pullToRollView too
                    mPullToRollView.setTranslationY(mPullDistance);
                } else {
                    //Hold PullToRollView position
                    mPullToRollView.setTranslationY(PULL_TO_REFRESH_HEIGHT);
                }
                mPullToRollView.rotateByPull(mPullDistance);
            }
            if(mListener != null){
                mListener.onPull((RecyclerView) changedView, mPullDistance, dy);
            }
        }
    }

    private void hideUpRotateView() {
        mPullToRollView.cancelRotateAnim();
        if(mSlideUpAnimator != null){
            mSlideUpAnimator.cancel();
        }
        mSlideUpAnimator = new AnimatorSet();
        ValueAnimator hideUpAnim = ValueAnimator.ofFloat(mPullToRollView.getTranslationY(), 0);
        hideUpAnim.setDuration(800);
        hideUpAnim.setInterpolator(new FastOutLinearInInterpolator());
        hideUpAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPullToRollView.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        mSlideUpAnimator.play(hideUpAnim);
        mSlideUpAnimator.start();
    }

    private void resetRotateView(){
        if(mSlideUpAnimator != null){
            mSlideUpAnimator.cancel();
        }
        mPullToRollView.cancelRotateAnim();
        mPullToRollView.setTranslationY(0);
    }

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        } else {
            isPositionHolding = false;
        }
    }

    public void setOnPullListener(OnPullListener listener){
        this.mListener = listener;
    }

    public void stopRefresh(){

    }

    public interface OnPullListener{

        void onPull(RecyclerView recyclerView, int topOffset, int dy);
        void onRelease(boolean refresh);
    }
}
