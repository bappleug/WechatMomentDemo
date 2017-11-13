package app.ray.wechatmements.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by Ray on 2017/9/15.
 * An image view which always remains square with respect to its width.
 */
public class SquaredImageView extends NetworkImageView {
  public SquaredImageView(Context context) {
    super(context);
  }

  public SquaredImageView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public SquaredImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
  }
}
