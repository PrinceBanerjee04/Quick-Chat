package com.example.quickchat;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdpter extends RecyclerView.Adapter<UserAdpter.viewholder>{
    MainActivity mainActivity; ArrayList<User> usersArrayList;
    public UserAdpter(MainActivity mainActivity, ArrayList<User> usersArrayList) {
        this.mainActivity=mainActivity;
        this.usersArrayList=usersArrayList;

    }

    @NonNull
    @Override
    public UserAdpter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdpter.viewholder holder, int position) {
        User user=usersArrayList.get(position);
        holder.username.setText(user.userName);
        holder.userstatus.setText(user.status);
        Picasso.get().load(user.profilepic).into(holder.userimg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mainActivity,chatWin.class);
                intent.putExtra("name",user.getUserName());
                intent.putExtra("recieverImg",user.getProfilepic());
                intent.putExtra("UID",user.getUserId());
                mainActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CircleImageView userimg;
        TextView username,userstatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userimg=itemView.findViewById(R.id.userimg);
            username=itemView.findViewById(R.id.username);
            userstatus=itemView.findViewById(R.id.userstatus);
        }
    }
}
