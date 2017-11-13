package app.ray.wechatmements.ui.moments;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.List;

import app.ray.wechatmements.R;
import app.ray.wechatmements.ui.moments.bean.Tweet;
import app.ray.wechatmements.widgets.recyclerview.BaseRecyclerAdapter;
import app.ray.wechatmements.widgets.recyclerview.RecyclerHolder;

/**
 * Created by Ray on 2017/11/12.
 */

public class CommentListAdapter extends BaseRecyclerAdapter<Tweet.CommentsBean> {


    private final int mSenderColor;

    public CommentListAdapter(Context context, List<Tweet.CommentsBean> data) {
        super(context, data);
        mSenderColor = context.getResources().getColor(R.color.textColorAccent);
    }

    @Override
    protected void convert(RecyclerHolder holder, Tweet.CommentsBean item, int position) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        sb.append(item.getSender().getNick());
        sb.append("：");
        sb.append(item.getContent());
        sb.setSpan(new ForegroundColorSpan(mSenderColor), 0, item.getSender().getNick().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ((TextView) holder.getView(R.id.tv_content)).setText(sb);
    }

    @Override
    protected int getLayoutIdFromType(int viewType) {
        return R.layout.item_comment;
    }
}
