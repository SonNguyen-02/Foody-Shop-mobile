package com.example.foodyshop.adapter;

import static com.example.foodyshop.config.Const.LIMIT_TIME_EDIT_DEL_FEEDBACK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodyshop.R;
import com.example.foodyshop.helper.Helper;
import com.example.foodyshop.model.FeedbackModel;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class FeedbackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_ITEM = 1;
    public static final int TYPE_LOADING = 2;
    private boolean isLoadingAdd;
    private int totalFeedback;

    private final Context context;
    private List<FeedbackModel> mListFeedback;
    private final IOnClickCallback mIOnClickCallback;
    private final SimpleDateFormat formatFrom;
    private final SimpleDateFormat formatTo;

    @SuppressLint("SimpleDateFormat")
    public FeedbackAdapter(Context context, List<FeedbackModel> mListFeedback) {
        this.context = context;
        this.mListFeedback = mListFeedback;
        mIOnClickCallback = (IOnClickCallback) context;
        formatFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatTo = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    public interface IOnClickCallback {
        void onClickLoadMore();

        void onClickEditFeedback(FeedbackModel feedback);

        void onClickDeleteFeedback(FeedbackModel feedback);
    }

    public void setTotalFeedback(int totalFeedback) {
        this.totalFeedback = totalFeedback;
    }

    public void addToListFeedback(List<FeedbackModel> list) {
        if (mListFeedback == null) {
            mListFeedback = list;
            notifyDataSetChanged();
        } else {
            mListFeedback.addAll(list);
            notifyItemRangeChanged(mListFeedback.size() - 1, list.size());
        }
    }

    public void reloadFeedback(FeedbackModel feedback) {
        if (mListFeedback.contains(feedback)) {
            notifyItemChanged(mListFeedback.indexOf(feedback));
        }
    }

    public void removeFeedback(FeedbackModel feedback) {
        if (mListFeedback.contains(feedback)) {
            totalFeedback--;
            int pos = mListFeedback.indexOf(feedback);
            mListFeedback.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public void addFirst(FeedbackModel feedback) {
        if (mListFeedback == null) {
            mListFeedback = new ArrayList<>();
        }
        totalFeedback++;
        mListFeedback.add(0, feedback);
        notifyItemInserted(0);
    }

    public int getSize() {
        if (mListFeedback != null) {
            if (isLoadingAdd) {
                return mListFeedback.size() - 1;
            } else {
                return mListFeedback.size();
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (mListFeedback != null && position == mListFeedback.size() - 1 && isLoadingAdd) {
            return TYPE_LOADING;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
            return new FeedbackViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            FeedbackModel feedback = mListFeedback.get(position);
            if (feedback == null) {
                return;
            }
            FeedbackViewHolder viewHolder = (FeedbackViewHolder) holder;
            Glide.with(context).load(feedback.getCustomerImg()).placeholder(R.drawable.placeholder_user).error(R.drawable.placeholder_user).into(viewHolder.imgAvatar);
            viewHolder.tvCustomerName.setText(feedback.getCustomerName());
            viewHolder.tvContent.setText(feedback.getContent());
            try {
                Date date = formatFrom.parse(feedback.getCreated());
                viewHolder.tvCreated.setText(formatTo.format(Objects.requireNonNull(date)));
            } catch (ParseException e) {
                viewHolder.tvCreated.setText(feedback.getCreated());
                e.printStackTrace();
            }
            if (Helper.isLogin(context) && Helper.getCurrentAccount().getId() == feedback.getCustomerId()
                    && System.currentTimeMillis() - feedback.getTime() < LIMIT_TIME_EDIT_DEL_FEEDBACK) {
                viewHolder.tvEdit.setVisibility(View.VISIBLE);
                viewHolder.tvDelete.setVisibility(View.VISIBLE);
                viewHolder.tvEdit.setOnClickListener(v -> mIOnClickCallback.onClickEditFeedback(feedback));
                viewHolder.tvDelete.setOnClickListener(v -> mIOnClickCallback.onClickDeleteFeedback(feedback));
            } else {
                viewHolder.tvEdit.setVisibility(View.GONE);
                viewHolder.tvDelete.setVisibility(View.GONE);
            }
        } else {
            LoadingViewHolder viewHolder = (LoadingViewHolder) holder;
            viewHolder.btnLoadMore.setText(MessageFormat.format(context.getResources().getString(R.string.see_more_feedback), totalFeedback - mListFeedback.size() + 1));
            viewHolder.btnLoadMore.setVisibility(View.VISIBLE);
            viewHolder.pbLoading.setVisibility(View.GONE);
            viewHolder.btnLoadMore.setOnClickListener(v -> {
                v.setVisibility(View.GONE);
                viewHolder.pbLoading.setVisibility(View.VISIBLE);
                mIOnClickCallback.onClickLoadMore();
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mListFeedback != null) {
            return mListFeedback.size();
        }
        return 0;
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView tvCustomerName, tvContent, tvCreated, tvEdit, tvDelete;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            tvCustomerName = itemView.findViewById(R.id.tv_customer_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvCreated = itemView.findViewById(R.id.tv_created);
            tvEdit = itemView.findViewById(R.id.tv_edit);
            tvDelete = itemView.findViewById(R.id.tv_delete);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar pbLoading;
        Button btnLoadMore;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            pbLoading = itemView.findViewById(R.id.pb_loading);
            btnLoadMore = itemView.findViewById(R.id.btn_load_more);
        }
    }

    public void addFooterLoading() {
        isLoadingAdd = true;
        mListFeedback.add(new FeedbackModel());
        notifyItemInserted(mListFeedback.size() - 1);
    }

    public void removeFooterLoading() {
        isLoadingAdd = false;
        mListFeedback.remove(mListFeedback.size() - 1);
        notifyItemRemoved(mListFeedback.size());
    }
}
