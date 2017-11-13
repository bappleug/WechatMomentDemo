package app.ray.wechatmements.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;

import app.ray.wechatmements.R;

/**
 * Created by Ray on 2017/9/15.
 * A ImageView which supports loading image from network with Glide
 */

public class NetworkImageView extends AppCompatImageView {

    protected int placeholderResId;
    protected int errorResId;
    protected String url;
    protected String path;
    protected boolean centerCrop;
    protected RequestListener<Drawable> listener;

    public NetworkImageView(Context context) {
        this(context, null);
    }

    public NetworkImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetworkImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NetworkImageView);
        placeholderResId = typedArray.getResourceId(R.styleable.NetworkImageView_niv_placeholderResId, 0);
        errorResId = typedArray.getResourceId(R.styleable.NetworkImageView_niv_errorResId, 0);
        url = typedArray.getString(R.styleable.NetworkImageView_niv_url);
        centerCrop = getScaleType() == ScaleType.CENTER_CROP;
        typedArray.recycle();
    }

    public void setUrl(String url) {
        this.path = null;
        this.url = url;
        post(() -> loadImage());
    }

    public void setPath(String path) {
        if (!path.startsWith("file://")) {
            path = "file://" + path;
        }
        this.url = null;
        this.path = path;
        post(() -> loadImage());
    }

    protected void loadImage() {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (centerCrop) {
            requestOptions.centerCrop();
        }
        if (measuredHeight != 0 && measuredWidth != 0) {
            requestOptions = requestOptions.override(measuredWidth, measuredHeight);
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

    public void setGlideListener(RequestListener<Drawable> listener){
        this.listener = listener;
    }
}