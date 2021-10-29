package com.example.myteamcproject.Exercise;

import static com.example.myteamcproject.Common.CommonMethod.exlist;
import static com.example.myteamcproject.Common.CommonMethod.explaylist;
import static com.example.myteamcproject.Common.CommonMethod.tonext;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myteamcproject.ATask.ExInsertATask;
import com.example.myteamcproject.Common.CommonMethod;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

public class FragRest extends Fragment{

    private View view;

    private static final String TAG = "FragRest";

    String e_type;
    int pos;
    FragmentManager fragmentManager;
    TextView tv_count;
    TextView tv_time;

    CountDownTimer countDownTimer;
    int timer, count;
    CommonMethod commonMethod;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.frag_rest, container, false);

        tv_time = viewGroup.findViewById(R.id.tv_resttime);
        countDownTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                int num = (int) (millisUntilFinished / 1000);
                timer = num;
                tv_time.setText(Integer.toString(timer));
            }

            @Override
            public void onFinish() {
                if (explaylist.get(tonext + 1) != null) {
                    /// update
                /*Log.d(TAG, "onClick: explaylist " + tonext + explaylist.get(tonext).getU_numb());

                ExUpdateATask updateATask = new ExUpdateATask("exu", explaylist.get(tonext).getU_numb());//u_numb값;
                updateATask.execute();*/
                    ///


                    Log.d(TAG, "onClick: explaylist " + tonext + explaylist.get(tonext).getE_name());

                    ExInsertATask insertATask = new ExInsertATask("exi", loginDTO.getId(), explaylist.get(tonext).getE_name());//id, e_name값;
                    try{
                        insertATask.execute().get();
                    }catch (Exception e){

                    }

                    tonext += 1;

                    try{
                        if(tonext < explaylist.size()){
                            Bundle bundle = new Bundle(); //번들을 통해 값 전달
                            bundle = getArguments();
                            bundle.putInt("pos", pos + 1);
                            bundle.putSerializable("dto", exlist.get(pos + 1));
                            FragExStart fragExStart = new FragExStart();   //FragExStart 선언
                            fragExStart.setArguments(bundle);    //번들을 FragExStart 로 보낼 준비

                            MainActivity activity = new MainActivity();
                            fragmentManager = getParentFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.main_frag, fragExStart).commit();
                        }else {
                            explaylist.clear();

                            tv_time.setText("운동 끝");

                            tonext = 0;
                            countDownTimer.cancel();

                            // 운동끝나면 홈화면으로
                            FragHome fragHome = new FragHome();
                            getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fragHome).commit();

                        }

                    } catch (IndexOutOfBoundsException e){
                        e.getMessage();
                        explaylist.clear();

                    }//try&catch
                }
            }//onFinish()
        };

        countDownTimer.start();

        return viewGroup;

    }//onCreateView()


}




