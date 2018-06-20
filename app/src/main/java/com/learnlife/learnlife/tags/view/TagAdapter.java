package com.learnlife.learnlife.tags.view;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private List<Tag> tagsChosen = new ArrayList<>();
    private List<Tag> userTags = new ArrayList<>();
    private ViewHolder holder;

    public TagAdapter(List<Tag> tagsItems, TagActivity activity) {
        this.tagsItems = tagsItems;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.tag_item, parent, false);
        this.holder = new ViewHolder(view);

        holder.imbAddChips.setOnClickListener(view1 -> {
            Tag tag = tagsItems.get(holder.getAdapterPosition());
            if(tagsChosen.contains(tag)){
                //Si le tag avait déjà était ajouté alors retire
                setUnselectedTag(tag);
            }else {
                //Sinon on ajoute
                setSelectedTag(tag);
            }
        });

        if(userTags != null)
            for (Tag tag : userTags) setSelectedTag(tag);

        return holder;
    }

    public void setSelectedTag(Tag tag) {
        holder.ctnMain.setBackground(activity.getResources().getDrawable(R.drawable.shape_chip_selected_drawable));
        holder.txvChips.setTextColor(activity.getResources().getColor(R.color.white));
        holder.imbAddChips.setBackground(ContextCompat.getDrawable(activity, R.drawable.ic_remove_chips));

        activity.tagsChosen.add(tag.getId());
        tagsChosen.add(tag);
    }

    public void setUnselectedTag(Tag tag) {
        holder.ctnMain.setBackground(activity.getResources().getDrawable(R.drawable.shape_chip_unselected_drawable));
        holder.txvChips.setTextColor(activity.getResources().getColor(R.color.black));
        holder.imbAddChips.setBackground(ContextCompat.getDrawable(activity, R.drawable.ic_add_chips));

        activity.tagsChosen.remove(tag.getId());
        tagsChosen.remove(tag);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Tag element = tagsItems.get(position);
        ViewHolder vHolder = (ViewHolder) holder;

        vHolder.txvChips.setText(element.getName());
    }

    @Override
    public int getItemCount() {
        return tagsItems.size();
    }

    public void setUserTags(List<Tag> userTags) {
        this.userTags = userTags;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txvChips) TextView txvChips;
        @BindView(R.id.ctnMain) ViewGroup ctnMain;
        @BindView(R.id.imbAddChips) ImageView imbAddChips;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
