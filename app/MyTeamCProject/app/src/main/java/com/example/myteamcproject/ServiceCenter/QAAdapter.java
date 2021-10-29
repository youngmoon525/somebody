package com.example.myteamcproject.ServiceCenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myteamcproject.Community.CommunityDTO;
import com.example.myteamcproject.Community.CommunityWriterClickListener;
import com.example.myteamcproject.Community.FragCoView;
import com.example.myteamcproject.R;

import java.util.ArrayList;

public class QAAdapter extends BaseAdapter {
    private static final String TAG = "QAAdapter";

    Context context;
    ArrayList<CommunityDTO> dtos;
    FragmentManager fragmentManager;
    LayoutInflater inflater;

    public QAAdapter() {};

    public QAAdapter(Context context, ArrayList<CommunityDTO> dtos, FragmentManager fragmentManager){
        this.context = context;
        this.dtos = dtos;
        this.fragmentManager = fragmentManager;

        this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    void addDTO(CommunityDTO dto){
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
        QAViewHolder viewHolder;

        if(view == null){
            view = inflater.inflate(R.layout.qa_view, viewGroup, false);

            viewHolder = new QAViewHolder();
            viewHolder.qa_num = view.findViewById(R.id.qa_num);
            viewHolder.qa_title = view.findViewById(R.id.qa_title);
            viewHolder.qa_writer = view.findViewById(R.id.qa_writer);
            viewHolder.qa_readcount = view.findViewById(R.id.qa_readcount);
            viewHolder.qalistlayout = view.findViewById(R.id.qalistlayout);

            view.setTag(viewHolder);
        }else{
            viewHolder = (QAViewHolder) view.getTag();
        }//if

        CommunityDTO dto= dtos.get(position);
        int qa_n = dto.getC_numb();


        viewHolder.qa_num.setText(String.valueOf(dtos.get(position).getC_numb()));
        viewHolder.qa_title.setText(dtos.get(position).getC_title());
        viewHolder.qa_writer.setText(dtos.get(position).getC_writer());
        viewHolder.qa_readcount.setText(String.valueOf(dtos.get(position).getC_readcount()));

        viewHolder.qalistlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragqaDetail fragqaDetail = new FragqaDetail();
                Bundle bundle = new Bundle();
                bundle.putSerializable("dto", dtos.get(position));
                fragqaDetail.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.main_frag, fragqaDetail).commit();

            }
        });

        return view;
    }//getView

    public class QAViewHolder{
        public TextView qa_num, qa_title, qa_writer, qa_readcount;
        public LinearLayout qalistlayout;
    }//QAViewHolder

}
