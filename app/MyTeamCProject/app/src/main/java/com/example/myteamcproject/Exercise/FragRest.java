package com.example.myteamcproject.Exercise;

import static com.example.myteamcproject.Common.CommonMethod.exlist;
import static com.example.myteamcproject.Common.CommonMethod.explaylist;
import static com.example.myteamcproject.Common.CommonMethod.tonext;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myteamcproject.Common.CommonMethod;
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
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                int num = (int) (millisUntilFinished / 1000);
                timer = num;
                tv_time.setText(Integer.toString(timer));
            }

            @Override
            public void onFinish() {
                if (explaylist.get(tonext + 1) != null) {
                    Bundle bundle = new Bundle(); //번들을 통해 값 전달

                    bundle.putInt("pos", pos + 1);
                    bundle.putSerializable("dto", exlist.get(pos + 1));
                    fragmentManager = getParentFragmentManager();
                    FragExStart fragExStart = new FragExStart(fragmentManager);   //FragExStart 선언
                    fragExStart.setArguments(bundle);    //번들을 FragExStart 로 보낼 준비


                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    transaction.replace(R.id.main_frag, fragExStart).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);

                    transaction.commit();
                }
            }//onFinish()
        };

        countDownTimer.start();

        return viewGroup;

    }//onCreateView()


}




