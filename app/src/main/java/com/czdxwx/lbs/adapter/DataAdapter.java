package com.czdxwx.lbs.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.czdxwx.lbs.MainActivity;
import com.czdxwx.lbs.MyApplication;
import com.czdxwx.lbs.R;
import com.czdxwx.lbs.entity.Data;
import com.czdxwx.lbs.PushNews.NewsActivity;

import java.util.ArrayList;
import java.util.List;


public class DataAdapter extends BaseQuickAdapter<Data, BaseViewHolder> {
    //适配器传入数据
private List<Data> dataList=new ArrayList<>();
    public DataAdapter(List<Data> dataList) {
        //填充item布局，和数据
        super(R.layout.layout_animation,dataList);
        this.dataList=dataList;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Data item) {
        //每三行循环一次壁纸
        switch (helper.getLayoutPosition() % 3) {
            case 0:
                helper.setImageResource(R.id.img, R.mipmap.animation_img1);
                break;
            case 1:
                helper.setImageResource(R.id.img, R.mipmap.animation_img2);
                break;
            case 2:
                helper.setImageResource(R.id.img, R.mipmap.animation_img3);
                break;
            default:
                break;
        }

        //item文本框显示数据
        helper.setText(R.id.tweetName, item.getTweetName());
        ((TextView) helper.getView(R.id.tweetText)).setText(item.getTweetText());
        ((TextView) helper.getView(R.id.tweetText)).setFocusable(false);
        ((TextView) helper.getView(R.id.tweetText)).setClickable(false);
        ((TextView) helper.getView(R.id.tweetText)).setLongClickable(false);
        helper.getView(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新建对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("推送内容：");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                TextView content = new EditText(getContext());
                content.setLayoutParams(lp);
                content.setGravity(Gravity.CENTER);
                content.setText(item.getContent());
//                content.setText(NewsActivity.db.findDbModelBySQL(String.format("select * from news where newsName='%s'", item.);
                builder.setView(content);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });


        helper.getView(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.db.delete(item);
                dataList.remove(item);
                setList(dataList);
            }

        });
    }
}
