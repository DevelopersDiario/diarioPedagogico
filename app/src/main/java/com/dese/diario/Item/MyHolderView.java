package com.dese.diario.Item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dese.diario.R;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by Eduardo on 14/07/2017.
 */

public class MyHolderView extends RecyclerView.ViewHolder {
    //public CircularImageView ivItem;
    public ImageView ivItem;
    public TextView tvItem;
    public MyHolderView(View itemView) {
        super(itemView);

        ivItem  = (ImageView) itemView.findViewById(R.id.ivItem);
        tvItem  = (TextView) itemView.findViewById(R.id.tvItem);
    }
}