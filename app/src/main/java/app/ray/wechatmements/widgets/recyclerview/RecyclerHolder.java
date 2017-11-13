package app.ray.wechatmements.widgets.recyclerview;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.method.MovementMethod;
import android.util.SparseArray;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Ray on 2017/4/11.
 */

public class RecyclerHolder extends RecyclerView.ViewHolder{
    private static final String TAG = "RecyclerHolder";
    private final SparseArray<View> mViews;
    private final static int MAX_VIEW_NUMBER = 12;//布局中有id的view数 不超过12个

    public RecyclerHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>(MAX_VIEW_NUMBER);
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    
    public RecyclerHolder setText(@IdRes int viewId, @NonNull CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    
    public RecyclerHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    
    public RecyclerHolder setTextColor(@IdRes int viewId, ColorStateList colorStateList) {
        TextView view = getView(viewId);
        view.setTextColor(colorStateList);
        return this;
    }

    
    public RecyclerHolder setMovementMethod(@IdRes int viewId, MovementMethod method) {
        TextView textView = getView(viewId);
        textView.setMovementMethod(method);
        return this;
    }

    
    public RecyclerHolder setImageResource(@IdRes int viewId, @DrawableRes int imgResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imgResId);
        return this;
    }

    
    public RecyclerHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    
    public RecyclerHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    
    public RecyclerHolder setImageUri(@IdRes int viewId, Uri imageUri) {
        ImageView view = getView(viewId);
        view.setImageURI(imageUri);
        return this;
    }

    
    public RecyclerHolder setScaleType(@IdRes int viewId, ImageView.ScaleType type) {
        ImageView view = getView(viewId);
        view.setScaleType(type);
        return this;
    }

    
    public RecyclerHolder setBackgroundColor(@IdRes int viewId, @ColorInt int bgColor) {
        View view = getView(viewId);
        view.setBackgroundColor(bgColor);
        return this;
    }

    
    public RecyclerHolder setBackgroundResource(@IdRes int viewId, @DrawableRes int bgRes) {
        View view = getView(viewId);
        view.setBackgroundResource(bgRes);
        return this;
    }

    
    public RecyclerHolder setColorFilter(@IdRes int viewId, ColorFilter colorFilter) {
        ImageView view = getView(viewId);
        view.setColorFilter(colorFilter);
        return this;
    }

    
    public RecyclerHolder setColorFilter(@IdRes int viewId, @ColorRes int colorFilter) {
        ImageView view = getView(viewId);
        view.setColorFilter(colorFilter);
        return this;
    }

    
    public RecyclerHolder setAlpha(@IdRes int viewId, @FloatRange(from = 0.0, to = 1.0) float value) {
        View view = getView(viewId);
        ViewCompat.setAlpha(view, value);
        return this;
    }

    /**
     * 设置子控件可见性
     */
    public RecyclerHolder setVisibility(@IdRes int viewId, int visibility){
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    
    public RecyclerHolder setMax(@IdRes int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    
    public RecyclerHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    
    public RecyclerHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    
    public RecyclerHolder setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    
    public RecyclerHolder setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    
    public RecyclerHolder setEnabled(@IdRes int viewId, boolean enabled) {
        View view = getView(viewId);
        view.setEnabled(enabled);
        return this;
    }

    
    public RecyclerHolder setAdapter(@IdRes int viewId, Adapter adapter) {
        AdapterView<Adapter> view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    
    public RecyclerHolder setAdapter(@IdRes int viewId, RecyclerView.Adapter adapter) {
        RecyclerView view = getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    
    public RecyclerHolder setChecked(@IdRes int viewId, boolean checked) {
        Checkable view = getView(viewId);
        view.setChecked(checked);
        return this;
    }

    
    public RecyclerHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    
    public RecyclerHolder setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
        return this;
    }

    
    public RecyclerHolder setOnTouchListener(@IdRes int viewId, View.OnTouchListener listener) {
        getView(viewId).setOnTouchListener(listener);
        return this;
    }


}