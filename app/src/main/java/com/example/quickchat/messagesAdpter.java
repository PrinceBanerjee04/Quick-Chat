package com.example.quickchat;

import static com.example.quickchat.chatWin.recieverIImg;
import static com.example.quickchat.chatWin.senderImg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class messagesAdpter extends RecyclerView.Adapter {

    Context context;
    ArrayList<msgModelclass> messagesAdpterArrayList;
    int ITEM_SEND=1,ITEM_RECIEVE=2;

    public messagesAdpter(Context context, ArrayList<msgModelclass> messagesAdpterArrayList) {
        this.context = context;
        this.messagesAdpterArrayList = messagesAdpterArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND){
            View view= LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new senderViewHolder(view);
        }
        else{
            View view=LayoutInflater.from(context).inflate(R.layout.reciever_layout,parent,false);
            return new recieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        msgModelclass messages=messagesAdpterArrayList.get(position);
        if(holder.getClass()==senderViewHolder.class){
            senderViewHolder viewHolder=(senderViewHolder) holder;
            viewHolder.msgtext.setText(messages.getMesssage());
            Picasso.get().load(senderImg).into(viewHolder.circleImageView);
        }
        else{
            recieverViewHolder viewHolder=(recieverViewHolder) holder;
            viewHolder.msgtext.setText(messages.getMesssage());
            Picasso.get().load(recieverIImg).into(viewHolder.circleImageView);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        msgModelclass messages=messagesAdpterArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderid())){
            return ITEM_SEND;
        }
        else{
            return ITEM_RECIEVE;
        }
    }


    class senderViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView msgtext;
        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.profilerggg);
            msgtext=itemView.findViewById(R.id.msgsendertyp);
        }
    }

    class recieverViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView msgtext;
        public recieverViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.pro);
            msgtext=itemView.findViewById(R.id.recivertextset);
        }
    }
}
