package app.ray.wechatmements.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Ray on 2017/11/10.
 *
 * Only RecyclerView is supported now
 */
public class PullToRefreshLayout extends FrameLayout {

    private RecyclerView recyclerView;
    private ViewDragHelper viewDragHelper;

    public PullToRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public PullToRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public PullToRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        if(getChildCount() != 1 || !(getChildAt(0) instanceof RecyclerView)){
            throw new IllegalStateException("Only one RecyclerView as child is acceptable");
        }
        recyclerView = (RecyclerView) getChildAt(0);
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new DraggerCallback());
    }

    private final class DraggerCallback extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == recyclerView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //Not allowed
            return 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //Reduce view movement to half of finger movement to simulate friction
            return top - dy / 2;
        }
    }

}
