package app.ray.wechatmements.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import app.ray.wechatmements.utils.DensityUtils;


/**
 * Created by Ray on 2016/6/2.
 * recycleview子条目间隔
 */
public class LinearSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private final static int DEFAULT_SPACE = 10;
    private boolean includeEdge;
    private boolean excludeHeader;
    private Paint paint;

    public LinearSpaceItemDecoration(Context context) {
        this(context, 0, DensityUtils.dip2px(context, DEFAULT_SPACE), false, false);
    }

    public LinearSpaceItemDecoration(Context context, int spacePx, boolean includeEdge) {
        this(context, 0, DensityUtils.dip2px(context, DEFAULT_SPACE), false, false);
    }

    public LinearSpaceItemDecoration(Context context, int spacePx, int colorRes, boolean includeEdge, boolean excludeHeader) {
        this.space = spacePx;
        this.includeEdge = includeEdge;
        this.excludeHeader = excludeHeader;
        if(colorRes != 0){
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(context.getResources().getColor(colorRes));
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            throw new IllegalStateException("this decoration only for LinearLayoutManager");
        }
        if(excludeHeader && position == 0){
            return;
        }
        int orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        if (position >= 0 && position <= parent.getAdapter().getItemCount() - 1) {
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.bottom = space;
            } else {
                outRect.right = space;
            }
        }
        if (position == 0 && includeEdge) {
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.top = space;
            } else {
                outRect.left = space;
            }
        }
        if (position == parent.getAdapter().getItemCount() - 1) {
            if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.bottom = includeEdge ? space : 0;
            } else {
                outRect.right = includeEdge ? space : 0;
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(paint == null){
            return;
        }
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + space;
            c.drawRect(left, top, right, bottom, paint);
        }
    }
}
