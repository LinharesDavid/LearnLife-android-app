package com.learnlife.learnlife.tags.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnlife.learnlife.R;
import com.learnlife.learnlife.tags.modele.Tag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Adama on 16/05/2018.
 */

public class TagAdapter extends RecyclerView.Adapter {
    private List<Tag> tagsItems;
    private TagActivity activity;
    private List<String> tagsChosen = new ArrayList<>();

    public TagAdapter(List<Tag> tagsItems, TagActivity activity) {
        this.tagsItems = tagsItems;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.tag_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.ctnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.ctnMain.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimaryDark));
                holder.txvTag.setTextColor(activity.getResources().getColor(R.color.white));

                tagsChosen.add(tagsItems.get(holder.getAdapterPosition()).getName());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tag element = tagsItems.get(position);
        ViewHolder vHolder = (ViewHolder) holder;

        vHolder.txvTag.setText(element.getName());
    }

    @Override
    public int getItemCount() {
        return tagsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txv_tag) TextView txvTag;
        @BindView(R.id.ctn_main) ViewGroup ctnMain;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
