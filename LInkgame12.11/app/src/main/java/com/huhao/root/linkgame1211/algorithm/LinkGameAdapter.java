package com.huhao.root.linkgame1211.algorithm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.huhao.root.linkgame1211.R;
import com.huhao.root.linkgame1211.algorithm.BaseHolder;
import com.huhao.root.linkgame1211.algorithm.BaseRecyclerviewAdapter;
import com.huhao.root.linkgame1211.algorithm.Item;

import java.io.IOException;
import java.util.List;

public class LinkGameAdapter extends BaseRecyclerviewAdapter<Item> {

    public LinkGameAdapter(Context context, List<Item> list) {
        super(context, list);
    }

    @Override
    public int getContentView(int viewType) {
        return R.layout.item;
    }

    @Override
    public void onInitView(BaseHolder holder, Item object, int position) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open("image/" + mList.get(position).getId() + ".png"));
            ((ImageView) holder.getView(R.id.image)).setImageBitmap(bitmap);
          /*  Glide.with(context)
                    .load(bitmap)
                    .apply(new RequestOptions().placeholder(((ImageView) holder.getView(R.id.image)).getDrawable()))
                    .into((ImageView) holder.getView(R.id.image));*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (object.isSelect()) {
            holder.getView(R.id.select).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.select).setVisibility(View.GONE);
        }
        if (object.isEliminated()) {
            holder.getView(R.id.image).setVisibility(View.GONE);
            holder.getView(R.id.select).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.image).setVisibility(View.VISIBLE);
        }
    }
}
