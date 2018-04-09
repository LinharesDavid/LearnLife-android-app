package com.learnlife.learnlife.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.learnlife.learnlife.R;
import com.learnlife.learnlife.crosslayers.models.Challenge;
import com.learnlife.learnlife.home.view.HomeActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends ArrayAdapter<Challenge> {
    private Context _context;
    private List<Challenge> _items;
    private int _layoutId;

    public Adapter(Context context, int layoutId, ArrayList<Challenge> items){
        super(context, layoutId, items);
        _context = context;
        _items = items;
        _layoutId = layoutId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        CartoucheViewHolder vHolder = null;

        LayoutInflater inflater = (LayoutInflater) _context.getSystemService((Activity.LAYOUT_INFLATER_SERVICE));

        if(view == null){
            view = inflater.inflate(_layoutId, parent, false);

            vHolder = new CartoucheViewHolder();

            vHolder.imvChallenge = view.findViewById(R.id.imvChallenge);
            vHolder.txvTitleChallenge = view.findViewById(R.id.txvTitleChallenge);
            vHolder.txvDetailsChallenge = view.findViewById(R.id.txvDetailsChallenge);
            vHolder.txvCategory = view.findViewById(R.id.txvCategory);
        }else{
            vHolder = (CartoucheViewHolder) view.getTag();
        }

        Challenge challenge = _items.get(position);

        vHolder.txvTitleChallenge.setText(challenge.getTitle());
        vHolder.txvDetailsChallenge.setText(challenge.getDescription());
        vHolder.txvCategory.setText(challenge.getCategory());
        Glide.with(_context).load(challenge.getUrl_image()).into(vHolder.imvChallenge);

        return view;
    }

    public class CartoucheViewHolder{
        public ImageView imvChallenge;
        public TextView txvTitleChallenge;
        public TextView txvDetailsChallenge;
        public TextView txvCategory;
    }
}
