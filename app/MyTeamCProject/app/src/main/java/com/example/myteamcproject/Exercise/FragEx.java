package com.example.myteamcproject.Exercise;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.myteamcproject.Common.CommonMethod.exlist;
import static com.example.myteamcproject.Common.CommonMethod.explaylist;
<<<<<<< HEAD
<<<<<<< HEAD
import static com.example.myteamcproject.Common.CommonMethod.inputStream;
import static com.example.myteamcproject.Common.CommonMethod.mOutputStream;
import static com.example.myteamcproject.Common.CommonMethod.mBluetoothAdapter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
=======
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;
=======
>>>>>>> main

>>>>>>> c02e2c87c568a2035f371a417852e038ad36a0f3
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.ATask.ExerciseATask;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class FragEx extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView_ex;
    ExerciseAdapter adapter2;
    String e_type;
    int position;
    ArrayList<ExerciseDTO> dtos;
    FragmentManager fragmentManager;
    private static final String TAG = "FragEx";

    public FragEx(){

    }
    // 생성자
    public FragEx(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;

    }

    // 자신이 활동하는 Activity 찾기
    MainActivity activity;

    // 사용자 정의 함수로 블루투스 활성 상태의 변경 결과를 App으로 알려줄때 식별자로 사용됨(0보다 커야함)



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            ViewGroup viewGroup = (ViewGroup) inflater
                    .inflate(R.layout.frag_ex, container, false);

            // Fragment에서는 onClick을 사용할 수 없기때문에, 별도로 리스너를 달아서 클릭이벤트를 지정한다.
            Button btnStart1 = viewGroup.findViewById(R.id.btnStart1);
            btnStart1.setOnClickListener(this);

            Bundle bundle = getArguments();
            if (bundle!=null){
                e_type = bundle.getString("e_type");
            }


            Log.d("TAG", "onCreateView: " + e_type);

        activity = (MainActivity) getActivity();

        ExerciseATask exerciseATask = new ExerciseATask("ex", e_type);

        try {
            exerciseATask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        // 반드시 생성해서 어댑터에 넘겨야 함
        dtos = new ArrayList<>();

        recyclerView_ex = viewGroup.findViewById(R.id.recyclerView_ex);

        // recyclerView 에서 반드시 초기화해야 하는 부분
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                activity, RecyclerView.VERTICAL, false);
        recyclerView_ex.setLayoutManager(layoutManager);


        FragmentManager fragmentManager = getParentFragmentManager();

        // 어댑터 객체를 생성한다
        adapter2 = new ExerciseAdapter(dtos, activity, fragmentManager);

        // 어댑터에 ArrayList에 dto를 추가한다
        for (int i = 0; i <= exlist.size() - 1; i++ ){
            adapter2.addDto(exlist.get(i));
        }//for

        // 만든 어댑터를 리사이클러 뷰에 붙인다
        recyclerView_ex.setAdapter(adapter2);

        // 6.어댑터에 있는 clickListener를 이용해 클릭한 위치에 dto를 가져온다.
        adapter2.setOnItemClickListener(new OnExerciseItemClickListener() {
            @Override
            public void onItemClick(ExerciseAdapter.ViewHolder holderm, View view, int position) {
                // 8.클릭하면 어댑터에게서 받은 클릭위치를 이용해 dto를 가져온다.

            }

        });

        return viewGroup;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            //frag_ex 버튼
            case R.id.btnStart1:
                try {
                    if (explaylist.size() != 0) {
                        ExerciseDTO dto = adapter2.getItem(position);

                        Bundle bundle = new Bundle(); //번들을 통해 값 전달
                        String e_type = dto.getE_type();
                        //String e_filepath = "";
                        bundle.putString("e_type", e_type);   //번들에 넘길 값 저장
                        //bundle.putString("운동사진", e_filepath);
                        bundle.putInt("pos", position);
                        fragmentManager = getParentFragmentManager();
                        bundle.putSerializable("dto", exlist.get(position));
                        FragExStart fragExStart = new FragExStart(fragmentManager);   //FragExStart 선언
                        fragExStart.setArguments(bundle);    //번들을 FragExStart 로 보낼 준비


                        FragmentTransaction transaction = fragmentManager.beginTransaction();

                        transaction.replace(R.id.main_frag, fragExStart).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);

                        transaction.commit();
                        break;

                    }else if(adapter2.getItem(position).getE_name().equals("걷기")){

                        ExerciseDTO dto = adapter2.getItem(position);

                        Bundle bundle = new Bundle(); //번들을 통해 값 전달
                        String e_type = dto.getE_type();
                        //String e_filepath = "";
                        bundle.putString("e_type", e_type);   //번들에 넘길 값 저장
                        //bundle.putString("운동사진", e_filepath);
                        bundle.putInt("pos", position);
                        bundle.putSerializable("dto", exlist.get(position));
                        FragWalk fragWalk = new FragWalk();
                        fragWalk.setArguments(bundle);

                        fragmentManager = getParentFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();

                        transaction.replace(R.id.main_frag, fragWalk).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);

                        transaction.commit();
                    }

                }catch (IndexOutOfBoundsException e){
                        Toast.makeText(getContext(), "운동 목록이 비어있습니다", Toast.LENGTH_SHORT).show();
                        FragWalk fragWalk = new FragWalk();
                        getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fragWalk).commit();
                    }//try & catch
        }//switch

    }//onclick



}
