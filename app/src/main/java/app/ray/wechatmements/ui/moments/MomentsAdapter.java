package app.ray.wechatmements.ui.moments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.ray.wechatmements.R;
import app.ray.wechatmements.ui.moments.bean.MomentsItem;
import app.ray.wechatmements.ui.moments.bean.Tweet;
import app.ray.wechatmements.ui.moments.bean.UserProfile;
import app.ray.wechatmements.utils.CollectionUtils;
import app.ray.wechatmements.utils.StringUtils;
import app.ray.wechatmements.widgets.AutoSizeImageView;
import app.ray.wechatmements.widgets.ExpandableTextView;
import app.ray.wechatmements.widgets.NetworkImageView;
import app.ray.wechatmements.widgets.SquaredImageView;
import app.ray.wechatmements.widgets.recyclerview.BaseRecyclerAdapter;
import app.ray.wechatmements.widgets.recyclerview.RecyclerHolder;

/**
 * Created by Ray on 2017/11/12.
 */

class MomentsAdapter extends BaseRecyclerAdapter<MomentsItem> {

    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_CONTENT = 1;
    private SparseBooleanArray mExpandStatus = new SparseBooleanArray();

    public MomentsAdapter(Context context, List<MomentsItem> data) {
        super(context, data);
    }

    public void refreshAll(ArrayList<MomentsItem> items) {
        mExpandStatus = new SparseBooleanArray();
        update(items);
    }

    public void refreshTweets(ArrayList<MomentsItem> items) {
        UserProfile userProfile = (UserProfile) getData().get(0);
        items.add(0, userProfile);
        update(items);
    }

    public void addTweets(ArrayList<MomentsItem> items) {
        add(items);
    }

    @Override
    protected void convert(RecyclerHolder holder, MomentsItem item, int position) {
        if (getItemViewType(position) == ITEM_TYPE_HEADER) {
            UserProfile userProfile = (UserProfile) item;
            handleUserProfile(holder, userProfile);
        } else {
            Tweet tweet = (Tweet) item;
            handleBaseInfo(holder, tweet);
            handleImage(holder, tweet);
            handleComment(holder, tweet);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    protected int getLayoutIdFromType(int viewType) {
        switch (viewType) {
            case ITEM_TYPE_HEADER:
                return R.layout.item_header_profile;
            case ITEM_TYPE_CONTENT:
                return R.layout.item_moments_tweet;
        }
        return 0;
    }

    private void handleUserProfile(RecyclerHolder holder, UserProfile userProfile) {
        NetworkImageView ivBg = holder.getView(R.id.iv_bg);
        NetworkImageView ivAvatar = holder.getView(R.id.iv_avatar);
        ivBg.setUrl(userProfile.getProfileImage());
        ivAvatar.setUrl(userProfile.getAvatar());
        holder.setText(R.id.tv_name, userProfile.getNick());
    }

    private void handleBaseInfo(RecyclerHolder holder, Tweet item) {
        Tweet.SenderBean sender = item.getSender();
        SquaredImageView ivAvatar = holder.getView(R.id.iv_avatar);
        ivAvatar.setUrl(sender.getAvatar());
        ((TextView) holder.getView(R.id.tv_name)).setText(sender.getUsername());
        ExpandableTextView expandableTextView = holder.getView(R.id.tv_content);
        if (StringUtils.isEmpty(item.getContent())) {
            expandableTextView.setVisibility(View.GONE);
        } else {
            expandableTextView.setVisibility(View.VISIBLE);
            expandableTextView.setText(item.getContent(), mExpandStatus, holder.getAdapterPosition());
        }
    }

    private void handleImage(RecyclerHolder holder, Tweet item) {
        List<Tweet.ImagesBean> imgs = item.getImages() == null ? Collections.emptyList() : item.getImages();
        AutoSizeImageView autoSizeImageView = holder.getView(R.id.iv_one);
        ViewGroup layoutImgs0 = holder.getView(R.id.layout_imgs0);
        ViewGroup layoutImgs1 = holder.getView(R.id.layout_imgs1);
        ViewGroup layoutImgs2 = holder.getView(R.id.layout_imgs2);
        if (imgs.size() == 1) {
            autoSizeImageView.setVisibility(View.VISIBLE);
            autoSizeImageView.setSize(3, 2);
            autoSizeImageView.setUrl(imgs.get(0).getUrl());
            layoutImgs0.setVisibility(View.GONE);
            layoutImgs1.setVisibility(View.GONE);
            layoutImgs2.setVisibility(View.GONE);
        } else if (imgs.size() == 4) {
            autoSizeImageView.setVisibility(View.GONE);
            layoutImgs0.setVisibility(View.VISIBLE);
            layoutImgs1.setVisibility(View.VISIBLE);
            layoutImgs2.setVisibility(View.GONE);
            List<SquaredImageView> imageViews = new ArrayList<>(4);
            imageViews.add((SquaredImageView) layoutImgs0.getChildAt(0));
            imageViews.add((SquaredImageView) layoutImgs0.getChildAt(2));
            layoutImgs0.getChildAt(4).setVisibility(View.GONE);
            imageViews.add((SquaredImageView) layoutImgs1.getChildAt(0));
            imageViews.add((SquaredImageView) layoutImgs1.getChildAt(2));
            layoutImgs1.getChildAt(4).setVisibility(View.GONE);
            for (int i = 0; i < imgs.size(); i++) {
                imageViews.get(i).setVisibility(View.VISIBLE);
                imageViews.get(i).setUrl(imgs.get(i).getUrl());
            }
        } else {
            autoSizeImageView.setVisibility(View.GONE);
            layoutImgs0.setVisibility(View.VISIBLE);
            layoutImgs1.setVisibility(View.VISIBLE);
            layoutImgs2.setVisibility(View.VISIBLE);
            List<SquaredImageView> imageViews = new ArrayList<>(9);
            for (int i = 0; i < layoutImgs0.getChildCount(); i++) {
                View child = layoutImgs0.getChildAt(i);
                if (child instanceof SquaredImageView) {
                    imageViews.add((SquaredImageView) child);
                }
            }
            for (int i = 0; i < layoutImgs1.getChildCount(); i++) {
                View child = layoutImgs1.getChildAt(i);
                if (child instanceof SquaredImageView) {
                    imageViews.add((SquaredImageView) child);
                }
            }
            for (int i = 0; i < layoutImgs2.getChildCount(); i++) {
                View child = layoutImgs2.getChildAt(i);
                if (child instanceof SquaredImageView) {
                    imageViews.add((SquaredImageView) child);
                }
            }
            for (int i = 0; i < imgs.size(); i++) {
                SquaredImageView imageView = imageViews.get(i);
                imageView.setVisibility(View.VISIBLE);
                imageView.setUrl(imgs.get(i).getUrl());
            }

            for (int i = imgs.size(); i < 9; i++) {
                imageViews.get(i).setVisibility(View.GONE);
            }
        }
    }

    private void handleComment(RecyclerHolder holder, Tweet item) {
        RecyclerView rvComment = holder.getView(R.id.rv_comments);
        if (CollectionUtils.isCollectionNullOrEmpty(item.getComments())) {
            rvComment.setVisibility(View.GONE);
        } else {
            rvComment.setVisibility(View.VISIBLE);
            if (rvComment.getAdapter() == null) {
                rvComment.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                CommentListAdapter adapter = new CommentListAdapter(getContext(), item.getComments());
                rvComment.setAdapter(adapter);
            } else {
                ((CommentListAdapter) rvComment.getAdapter()).update(item.getComments());
            }
        }
    }
}
