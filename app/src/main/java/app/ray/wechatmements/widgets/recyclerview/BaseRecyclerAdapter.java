
package app.ray.wechatmements.widgets.recyclerview;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.ray.wechatmements.R;

/**
 * Created by Ray on 2016/1/5.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> {

    protected List<T> mData;
    private final Context mContext;
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    public BaseRecyclerAdapter(Context context, List<T> data) {
        mData = data == null ? new ArrayList<>(0) : data;
        mContext = context;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerHolder holder = new RecyclerHolder(LayoutInflater.from(mContext).inflate(getLayoutIdFromType(viewType), parent, false));
        Log.d("onCreateViewHolder", "create new view holder");
        holder.itemView.setOnClickListener(v -> {
            int realPosition = (int) v.getTag(R.id.recycle_adapter_pos);
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, mData.get(realPosition), realPosition);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            int realPosition = (int) v.getTag(R.id.recycle_adapter_pos);
            if (mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onItemLongClick(v, mData.get(realPosition), realPosition);
                return true;
            }
            return false;
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, int position) {
        holder.itemView.setTag(R.id.recycle_adapter_pos, position);
        convert(holder, mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void update(List<T> news) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback<>(mData, news));
        mData = news;
        diffResult.dispatchUpdatesTo(this);
    }

    public void add(int index, List<T> items) {
        if (index < 0 || index > mData.size()) {
            throw new IllegalArgumentException("index wrong");
        }
        mData.addAll(index, items);
        notifyItemRangeInserted(index, items.size());
    }

    public void add(T item) {
        add(mData.size(), item);
    }

    public void add(List<T> items){
        add(mData.size(), items);
    }

    public void add(int index, T item) {
        mData.add(index, item);
        notifyItemInserted(index);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnItemLongClickListener = listener;
    }

    public Context getContext() {
        return mContext;
    }

    /**Override this method to bind data*/
    protected abstract void convert(RecyclerHolder holder, T t, int position);

    protected abstract int getLayoutIdFromType(int viewType);

    public interface OnItemClickListener<T> {
        void onItemClick(View view, T data, int position);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View view, T data, int position);
    }

    public List<T> getData() {
        return mData;
    }
}
