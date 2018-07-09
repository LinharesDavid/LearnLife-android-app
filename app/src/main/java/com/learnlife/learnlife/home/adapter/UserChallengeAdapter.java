package com.learnlife.learnlife.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.models.UserChallenge;

import java.util.List;

public class UserChallengeAdapter extends ArrayAdapter<UserChallenge> {
    private Context _context;
    private List<UserChallenge> items;
    private int layoutId;

    public UserChallengeAdapter(Context context, int layoutId, List<UserChallenge> items){
        super(context, layoutId, items);
        _context = context;
        this.items = items;
        this.layoutId = layoutId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        CartoucheViewHolder vHolder = null;

        LayoutInflater inflater = (LayoutInflater) _context.getSystemService((Activity.LAYOUT_INFLATER_SERVICE));

        if(view == null){
            view = inflater.inflate(layoutId, parent, false);

            vHolder = new CartoucheViewHolder();

            vHolder.imvChallenge = view.findViewById(R.id.imvChallenge);
            vHolder.txvTitleChallenge = view.findViewById(R.id.txvTitleChallenge);
            vHolder.txvDetailsChallenge = view.findViewById(R.id.txvDetailsChallenge);
            vHolder.txvCategory = view.findViewById(R.id.txvCategory);
        }else{
            vHolder = (CartoucheViewHolder) view.getTag();
        }

        UserChallenge userChallenge = items.get(position);

        vHolder.txvTitleChallenge.setText(userChallenge.getChallenge().getName());
        vHolder.txvDetailsChallenge.setText(userChallenge.getChallenge().getDetails());
        vHolder.txvCategory.setText(userChallenge.getChallenge().getCategory());

        Glide.with(_context).load(userChallenge.getChallenge().getImageUrl()).into(vHolder.imvChallenge);

        return view;
    }

    public class CartoucheViewHolder{
        public ImageView imvChallenge;
        public TextView txvTitleChallenge;
        public TextView txvDetailsChallenge;
        public TextView txvCategory;
    }
}
