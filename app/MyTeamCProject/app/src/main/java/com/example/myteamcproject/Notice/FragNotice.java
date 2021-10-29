package com.example.myteamcproject.Notice;

import static com.example.myteamcproject.Common.CommonMethod.noticeList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myteamcproject.ATask.NoticeAtask;
import com.example.myteamcproject.ATask.QaATask;
import com.example.myteamcproject.Community.CommunityDTO;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;
import com.example.myteamcproject.ServiceCenter.FragQaWrite;
import com.example.myteamcproject.ServiceCenter.QAAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragNotice extends Fragment {

    MainActivity activity;
    ArrayList<NoticeDTO> dtos;
    FragmentManager fragmentManager;
    NoticeAdapter adapter;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_notice, container, false);

        listView = viewGroup.findViewById(R.id.list_notice);

        //프래그먼트에서 액티비티에 있는 객체를 사용하기 위한 액티비티 변수 선언 후 액티비티를 저장
        activity = (MainActivity) getActivity();
        //DB와 연동하기 위한 Atask 객체 선언
        NoticeAtask noticeAtask = new NoticeAtask();

        //Atask 실행 후 값을 가져온다
        try {
            noticeAtask.execute().get();
        } catch (ExecutionException e) {
            e.getMessage();
        } catch (InterruptedException e) {
            e.getMessage();
        }

        dtos = new ArrayList<>();

        listView = viewGroup.findViewById(R.id.list_notice);

        fragmentManager = getParentFragmentManager();
        adapter = new NoticeAdapter(activity, dtos, fragmentManager);

        if(noticeList != null){
            for (int i = 0; i <= noticeList.size() - 1; i++ ){
                adapter.addDTO(noticeList.get(i));
            }
        }//if

        listView.setAdapter(adapter);

        return viewGroup;
    }
}
