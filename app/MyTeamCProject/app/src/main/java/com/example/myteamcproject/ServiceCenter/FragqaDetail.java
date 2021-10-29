package com.example.myteamcproject.ServiceCenter;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.qalist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.ATask.CommunityATask;
import com.example.myteamcproject.ATask.QaATask;
import com.example.myteamcproject.Community.CommunityAdapter;
import com.example.myteamcproject.Community.CommunityDTO;
import com.example.myteamcproject.Community.FragComm;
import com.example.myteamcproject.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragqaDetail extends Fragment {

    private static final String TAG = "FragCoView";

    Button return_qaview, qa_delete, qa_update;
    TextView qad_title, qad_content, qad_readcount;
    ImageView qa_imagefile;
    CommunityDTO qadto;
    File file;
    public String imageRealPathA;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_qadetail, container, false);


        Bundle bundle = getArguments();
        qadto = (CommunityDTO) bundle.getSerializable("dto");
        int position = bundle.getInt("pos");

        qad_content = viewGroup.findViewById(R.id.qad_content);
        qad_title = viewGroup.findViewById(R.id.qad_title);
        qad_readcount = viewGroup.findViewById(R.id.qad_readcount);
        qa_delete = viewGroup.findViewById(R.id.qa_delete);
        qa_update = viewGroup.findViewById(R.id.qa_update);
        qa_imagefile = viewGroup.findViewById(R.id.qa_imagefile);
        return_qaview = viewGroup.findViewById(R.id.return_qaview);

        if (qadto != null) {
            qad_title.setText(qadto.getC_title());
            qad_content.setText(qadto.getC_content());
            qad_readcount.setText(String.valueOf(qadto.getC_readcount()));
            String filepath = ipConfig + "/resources/" + qadto.getC_filepath();
            Log.d(TAG, "setDto: " + filepath);
            Glide.with(this).load(filepath).into(qa_imagefile);
        }//if

        return_qaview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragQA fragQA = new FragQA();
                transaction.replace(R.id.main_frag, fragQA);
                transaction.commit();
            }
        });

        qa_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                FragQA fragQA = new FragQA();
                int co_delete_c = qadto.getC_numb();
                Log.d(TAG, "onClick: " + co_delete_c);
                CommunityAdapter adapter = new CommunityAdapter((ArrayList<CommunityDTO>) qalist, getContext(), getParentFragmentManager());

                adapter.removeDto(position);
                adapter.Refresh();

                CommunityATask communityATask = new CommunityATask("delete", co_delete_c);

                try {
                    communityATask.execute().get();
                } catch (ExecutionException e) {
                    e.getMessage();
                } catch (InterruptedException e) {
                    e.getMessage();
                }

                transaction.replace(R.id.main_frag, fragQA);
                transaction.commit();
            }
        });
        qa_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragQAUpdate fragQAUpdate = new FragQAUpdate();
                transaction.replace(R.id.main_frag, fragQAUpdate);
                transaction.commit();
            }
        });

        qa_imagefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file = null;
                try {
                    file = createFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                galleryAddPic();
            }
        });

        return viewGroup;
    }

    private File createFile() throws IOException {

        String imageFileName = qadto.getC_filename();
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File curFile = null;
        try {
            curFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageRealPathA = curFile.getAbsolutePath();
        Log.d(TAG, "createFile: " + imageRealPathA);
        Toast.makeText(getContext(), "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show();
        return curFile;
    }//createFile

    private void galleryAddPic() {
        Intent mediaScanIntent =
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(qadto.getC_filepath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }//galleryAddPic
}

