package com.example.quickchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatWin extends AppCompatActivity {
    String recieverimg,recieverUid,recieverName,SenderUid;
    CircleImageView profile;
    TextView recieverNName;
    CardView sendbtn;
    EditText textmsg;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    public static String senderImg;
    public static String recieverIImg;
    String senderRoom,recieverRoom;
    RecyclerView mmessangesAdpter;
    ArrayList<msgModelclass> messagesArrayList;
    messagesAdpter messagesAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_win);

        getSupportActionBar().hide();

        mmessangesAdpter=findViewById(R.id.msgadpter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mmessangesAdpter.setLayoutManager(linearLayoutManager);
        messagesAdpter=new messagesAdpter(chatWin.this,messagesArrayList);
        mmessangesAdpter.setAdapter(messagesAdpter);

        recieverimg=getIntent().getStringExtra("recieverImg");
        recieverUid=getIntent().getStringExtra("UID");
        recieverName=getIntent().getStringExtra("name");
        messagesArrayList=new ArrayList<>();

        sendbtn=findViewById(R.id.sendbtnn);
        textmsg=findViewById(R.id.textmsg);

        profile=findViewById(R.id.profileimgg);
        recieverNName=findViewById(R.id.recivername);

        DatabaseReference reference=database.getReference().child("user").child(firebaseAuth.getUid());
        DatabaseReference chatreference=database.getReference().child("user").child(senderRoom).child("messages");

        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    msgModelclass messages=dataSnapshot.getValue(msgModelclass.class);
                    messagesArrayList.add(messages);

                }
                messagesAdpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderImg=snapshot.child("profilepic").getValue().toString();
                recieverIImg=recieverimg;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Picasso.get().load(recieverimg).into( profile);
        recieverNName.setText(""+recieverName);

        SenderUid=firebaseAuth.getUid();
        senderRoom=SenderUid+recieverUid;
        recieverRoom=recieverUid+SenderUid;

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=textmsg.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(chatWin.this, "Enter The Message First", Toast.LENGTH_SHORT).show();
                }
                textmsg.setText("");
                Date date=new Date();
                msgModelclass messagess=new msgModelclass(message,SenderUid, date.getTime());
                database=FirebaseDatabase.getInstance();
                database.getReference().child("chats").child("recieverRoom").child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats").child("recieverRoom").child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });
            }
        });
    }
}