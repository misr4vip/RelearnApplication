package com.example.my1stapplication.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.my1stapplication.Post;
import com.example.my1stapplication.R;
import java.util.ArrayList;
import java.util.List;


public class favoriteadapter extends ArrayAdapter<Post>{

    private Activity context;
    public List<Post> postslist;
    private List<Post> total;

    public favoriteadapter(Activity context, List<Post> postslist){
        super(context, R.layout.favlist, postslist );
        this.context=context;
        this.postslist=postslist;
        this.total=new ArrayList<Post>();

    }


    public List<Post> getPostslist() {
        return postslist;
    }

    @NonNull
    @Override
    public Activity getContext() {
        return context;
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listviewitem= inflater.inflate(R.layout.favlist,null, true);
        TextView materialname1 = (TextView) listviewitem.findViewById(R.id.materialname);
        TextView coursename1 = (TextView) listviewitem.findViewById(R.id.coursename);
        TextView uniname1 = (TextView) listviewitem.findViewById(R.id.uniname);
        TextView price1 = (TextView) listviewitem.findViewById(R.id.price);

        Post post = postslist.get(position);

        materialname1.setText(post.getMaterialname());
        coursename1.setText(post.getCoursename());
        uniname1.setText(post.getUniname());
        price1.setText(post.getPrice());

        if(total.isEmpty())
            total.addAll(postslist);

        return listviewitem;
    }


    public void savePosts(){
        if(total.isEmpty())
            total.addAll(postslist);
    }

    public void filter(String charText) {
        charText = charText.toLowerCase();


        postslist.clear();

        if (charText.length() == 0) {
            postslist.addAll(total);
        } else {

            for (Post p : total) {
                if (p.getMaterialname().toLowerCase().contains(charText) || p.getCoursename().toLowerCase().contains(charText) || p.getUniname().toLowerCase().contains(charText))
                    postslist.add(p);
            }

            notifyDataSetChanged();

        }

    }}










