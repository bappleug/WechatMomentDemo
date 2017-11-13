package app.ray.wechatmements.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import app.ray.wechatmements.R;
import app.ray.wechatmements.utils.DensityUtils;


/**
 * Created by Ray on 2017/10/23.
 * This view always keep maximum height of 2/3 screen width
 */
public class AutoSizeImageView extends NetworkImageView {

    private float widthRatio = -1;
    private float heightRatio = -1;

    public AutoSizeImageView(Context context) {
        this(context, null);
    }

    public AutoSizeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoSizeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr){
        if(isInEditMode()){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AutoSizeImageView, defStyleAttr, 0);
            int width = typedArray.getDimensionPixelSize(R.styleable.AutoSizeImageView_test_width, DensityUtils.getScreenW(getContext()));
            int height = typedArray.getDimensionPixelSize(R.styleable.AutoSizeImageView_test_height, DensityUtils.getScreenW(getContext()));
            setSize(width, height);
            typedArray.recycle();
        }
        setGlideListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                setSize(3, 2);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                setSize(resource.getIntrinsicWidth(), resource.getIntrinsicHeight());
                return false;
            }
        });
    }

    @Override
    protected void loadImage() {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (centerCrop) {
            requestOptions.centerCrop();
        }
        if (url != null) {
            RequestBuilder<Drawable> builder = Glide.with(this).load(url);
            if(listener != null){
                builder.listener(listener);
            }
            builder.apply(requestOptions).into(this);
        }
        if (path != null) {
            RequestBuilder<Drawable> builder = Glide.with(this).load(path);
            if(listener != null){
                builder.listener(listener);
            }
            builder.apply(requestOptions).into(this);
        }
    }

    public void setSize(int width, int height){
        if(width <= 0 || height <= 0){
            throw new IllegalArgumentException("negative width or height");
        }
        float ratio = (float) width / height;
        if(ratio > 3.0 / 2.0){
            widthRatio = 1.0f;
            heightRatio = (float) height / width * widthRatio;
        } else {
            heightRatio = 2.0f / 3.0f;
            widthRatio = (float) width / height * heightRatio;
        }
        requestLayout();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(widthRatio != -1){
            int width = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension((int)(width * widthRatio), (int)(width * heightRatio));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
