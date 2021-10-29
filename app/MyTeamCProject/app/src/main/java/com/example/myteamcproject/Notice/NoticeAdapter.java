package com.example.myteamcproject.Notice;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.myteamcproject.ATask.NoticeAtask;
import com.example.myteamcproject.Community.CommunityDTO;
import com.example.myteamcproject.R;
import com.example.myteamcproject.ServiceCenter.FragqaDetail;
import com.example.myteamcproject.ServiceCenter.QAAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NoticeAdapter extends BaseAdapter {

    private static final String TAG = "NoticeAdapter";

    Context context;
    ArrayList<NoticeDTO> dtos;
    FragmentManager fragmentManager;
    LayoutInflater inflater;

    public NoticeAdapter() {};

    public NoticeAdapter(Context context, ArrayList<NoticeDTO> dtos, FragmentManager fragmentManager){
        this.context = context;
        this.dtos = dtos;
        this.fragmentManager = fragmentManager;

        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    void addDTO(NoticeDTO dto){
        dtos.add(dto);
    }

    @Override
    public int getCount() {
        return dtos.size();
    }

    @Override
    public Object getItem(int position) {
        return dtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Log.d(TAG, "getView: " + position);
        NoticeAdapter.NoticeViewHolder viewHolder;

        if(view == null){
            view = inflater.inflate(R.layout.notice_view, viewGroup, false);

            viewHolder = new NoticeAdapter.NoticeViewHolder();
            viewHolder.no_num = view.findViewById(R.id.no_num);
            viewHolder.no_title = view.findViewById(R.id.no_title);
            viewHolder.no_writer = view.findViewById(R.id.no_writer);
            viewHolder.no_readcount = view.findViewById(R.id.no_readcount);
            viewHolder.nolistlayout = view.findViewById(R.id.noticelistlayout);

            view.setTag(viewHolder);
        }else{
            viewHolder = (NoticeViewHolder) view.getTag();
        }//if

        NoticeDTO dto= dtos.get(position);

        viewHolder.no_num.setText(String.valueOf(dtos.get(position).getN_numb()));
        viewHolder.no_title.setText(dtos.get(position).getN_title());
        viewHolder.no_writer.setText(dtos.get(position).getN_writer());
        viewHolder.no_readcount.setText(String.valueOf(dtos.get(position).getN_readcount()));

        viewHolder.nolistlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NoticeAtask noticeAtask = new NoticeAtask("readcount", dtos.get(position).getN_numb());

                //Atask 실행 후 값을 가져온다
                try {
                    noticeAtask.execute().get();
                } catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }

                FragNoticeDetail fragqaDetail = new FragNoticeDetail();
                fragmentManager.beginTransaction().replace(R.id.main_frag, fragqaDetail).commit();
            }
        });

        return view;
    }//getView

    public class NoticeViewHolder{
        public TextView no_num, no_title, no_writer, no_readcount;
        public LinearLayout nolistlayout;
    }//QAViewHolder

}//NoticeAdapter
