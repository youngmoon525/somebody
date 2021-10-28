package com.example.myteamcproject.ServiceCenter;

<<<<<<< HEAD
<<<<<<< HEAD
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
=======
=======
>>>>>>> ysj
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
>>>>>>> jensh
=======
>>>>>>> ysj
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
<<<<<<< HEAD
<<<<<<< HEAD
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.R;
import com.google.firebase.database.ChildEventListener;
=======
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.R;
>>>>>>> jensh
=======
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.R;
>>>>>>> ysj
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
<<<<<<< HEAD
<<<<<<< HEAD

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
=======
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
>>>>>>> jensh
=======
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
>>>>>>> ysj


public class FragMyChat extends Fragment {

    private static final String TAG = "FragMyChat";

    private String chatRoomUid; //채팅방 하나 id
    private String myuid;       //나의 id
    private String destUid;     //상대방 uid
<<<<<<< HEAD
<<<<<<< HEAD
    private EditText edt_chat;
    private Button btn_send;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private List<UserDTO> chatDTOS;


=======

    private RecyclerView recyclerView;
>>>>>>> jensh
=======

    private RecyclerView recyclerView;
>>>>>>> ysj
    private Button button;
    private EditText editText;

    private FirebaseDatabase firebaseDatabase;
<<<<<<< HEAD
<<<<<<< HEAD
    private DatabaseReference myReference;
    private DatabaseReference destReference;

    private UserDTO destUserDTO;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy.MM.dd HH:mm");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mychat, container, false);



        if(loginDTO.getAdmin().equals("Y")){
            Bundle bundle = getArguments();
            destUid = bundle.getString("userid");
            myuid = "admin";
        }else if(loginDTO.getAdmin().equals("N")){
            myuid = loginDTO.getId();
            destUid = "admin";
        }


        btn_send = view.findViewById(R.id.btn_send);
        edt_chat = view.findViewById(R.id.et_chatting);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = edt_chat.getText().toString();

                if (msg != null){
                    UserDTO dto = new UserDTO();
                    dto.setName(loginDTO.getId());
                    dto.setMsg(msg);
                    dto.setProfileImgUrl(ipConfig + "/resources/" + loginDTO.getMember_c_file_path());
                    long now = System.currentTimeMillis();
                    Date mDate = new Date(now);
                    SimpleDateFormat simpleDate = new SimpleDateFormat("hh:mm:aa");
                    String getTime = simpleDate.format(mDate);
                    dto.setDate(getTime);
                    myReference.push().setValue(dto);
                    destReference.push().setValue(dto);
                    edt_chat.setText("");
                }
            }
        });

        recyclerView = view.findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        chatDTOS = new ArrayList<>();
        adapter = new ChatAdpter(chatDTOS, getContext(), loginDTO.getId());
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        if(loginDTO.getAdmin().equals("Y")){
            myReference = database.getReference("chatroom").child(destUid + "Xadmin");
            destReference = database.getReference("chatroom").child(destUid);
        }else if(loginDTO.getAdmin().equals("N")){
            myReference = database.getReference("chatroom").child(loginDTO.getId());
            destReference = database.getReference("chatroom").child(loginDTO.getId() + "Xadmin");
        }

        myReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UserDTO dto = snapshot.getValue(UserDTO.class);
                ((ChatAdpter) adapter).addChat(dto);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }//onCreateView
=======
=======
>>>>>>> ysj

    private User destUser;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy.MM.dd HH:mm");

    public void onBackPressed() {

    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_account, container, false);

        
        return view;
    }//onCreateView

    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]
    }
<<<<<<< HEAD
>>>>>>> jensh
=======
>>>>>>> ysj
}
