package com.example.fengyi.fmao1_sizebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NormalRecyclerViewAdapter extends RecyclerView.Adapter<NormalRecyclerViewAdapter.NormalTextViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private static Context mContext;
    private static String[] mTitles;

    public NormalRecyclerViewAdapter(Context context) {
        // TODO: Set text list
        mContext = context;
        JSONManagement jsonManagement = new JSONManagement(mContext);
        List<String> names = jsonManagement.names();
        Log.d("NormalRecyclerViewAdapt", "read names: " + String.valueOf(names.size()));
        mTitles = new String[names.size()];
        mTitles = jsonManagement.names().toArray(mTitles);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_text, parent, false));
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.mTextView.setText(mTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.length;
    }

    public static class NormalTextViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view) TextView mTextView;

        NormalTextViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start DetailActivity, send index
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra("index", getAdapterPosition());
                    mContext.startActivity(intent);
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showDialog(view, getAdapterPosition());
                    return true;
                }
            });
        }
    }

    private static void showDialog(View view, int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_message);

        final int thisIndex = index;

        //监听下方button点击事件
        builder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: delete this item
                JSONManagement jsonManagement = new JSONManagement(mContext);
                jsonManagement.delete(thisIndex);
                List<String> names = jsonManagement.names();
                mTitles = jsonManagement.names().toArray(mTitles);
            }
        });
        builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });

        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
