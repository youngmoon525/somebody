package com.example.myteamcproject.ServiceCenter;

<<<<<<< HEAD
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;
import android.os.Bundle;
=======
import android.os.Bundle;

import androidx.fragment.app.Fragment;

>>>>>>> jensh
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

<<<<<<< HEAD
import androidx.fragment.app.Fragment;

=======
>>>>>>> jensh
import com.example.myteamcproject.R;

public class FragService extends Fragment {

    View view;
<<<<<<< HEAD
    Button btnQA, btnChat, btnadmin;
=======
    Button btnQA, btnChat;
>>>>>>> jensh

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       view = inflater.inflate(R.layout.frag_service, container, false);

<<<<<<< HEAD
       btnQA = view.findViewById(R.id.btnQA);
       btnChat = view.findViewById(R.id.btnChat);
       btnadmin = view.findViewById(R.id.btnAdminChat);

       if(loginDTO.getAdmin().equals("Y")){
           btnChat.setVisibility(View.GONE);
           btnadmin.setVisibility(View.VISIBLE);
       }else if(loginDTO.getAdmin().equals("N")){
           btnChat.setVisibility(View.VISIBLE);
           btnadmin.setVisibility(View.GONE);
       }

       btnQA.setOnClickListener(new View.OnClickListener() {
=======
       view.findViewById(R.id.btnQA).setOnClickListener(new View.OnClickListener() {
>>>>>>> jensh
           @Override
           public void onClick(View view) {
               FragQA fragQA = new FragQA();
               getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fragQA).commit();

           }//onClick
       });//view.findViewById(R.id.btnQA).setOnClickListener

<<<<<<< HEAD

       btnChat.setOnClickListener(new View.OnClickListener() {
=======
       view.findViewById(R.id.btnChat).setOnClickListener(new View.OnClickListener() {
>>>>>>> jensh
           @Override
           public void onClick(View view) {
               FragMyChat fragMyChat = new FragMyChat();
               getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fragMyChat).commit();
           }//onClick
       });//view.findViewById(R.id.btnChat).setOnClickListener

<<<<<<< HEAD
       btnadmin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragChatroom fragChatroom = new FragChatroom();
               getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fragChatroom).commit();
           }//onClick
       });//view.findViewById(R.id.btnAdminChat).setOnClickListener
=======

>>>>>>> jensh

       return view;

    }
}