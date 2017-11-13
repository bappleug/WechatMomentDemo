package app.ray.wechatmements.widgets.recyclerview;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Ray on 2017/5/31.
 */

public class DiffCallback<T> extends DiffUtil.Callback {

    private List<T> olds;
    private List<T> news;

    public DiffCallback(List<T> olds, List<T> news) {
        this.olds = olds;
        this.news = news;
    }

    @Override
    public int getOldListSize() {
        return olds == null ? 0 : olds.size();
    }

    @Override
    public int getNewListSize() {
        return news == null ? 0 : news.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return olds.get(oldItemPosition).equals(news.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return olds.get(oldItemPosition).equals(news.get(newItemPosition));
    }
}
