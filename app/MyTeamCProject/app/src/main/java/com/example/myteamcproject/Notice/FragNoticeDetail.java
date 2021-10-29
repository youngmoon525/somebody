package com.example.myteamcproject.Notice;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.noticeDTO;

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
import com.example.myteamcproject.Community.CommunityAdapter;
import com.example.myteamcproject.Community.CommunityDTO;
import com.example.myteamcproject.R;
import com.example.myteamcproject.ServiceCenter.FragQA;
import com.example.myteamcproject.ServiceCenter.FragQAUpdate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FragNoticeDetail extends Fragment {

    private static final String TAG = "FragNoticeDetail";

    Button return_nodview, nod_delete, nod_update;
    TextView nod_title, nod_content, nod_readcount;
    ImageView nod_imagefile;
    File file;
    public String imageRealPathA;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_noticedetail, container, false);

        nod_content = viewGroup.findViewById(R.id.nod_content);
        nod_title = viewGroup.findViewById(R.id.nod_title);
        nod_readcount = viewGroup.findViewById(R.id.nod_readcount);
        nod_imagefile = viewGroup.findViewById(R.id.nod_imagefile);
        return_nodview = viewGroup.findViewById(R.id.return_nodview);

        if (noticeDTO != null) {
            nod_title.setText(noticeDTO.getN_title());
            nod_content.setText(noticeDTO.getN_content());
            nod_readcount.setText(String.valueOf(noticeDTO.getN_readcount()));
            if(noticeDTO.n_filepath != null){
                String filepath = ipConfig + "/resources/" + noticeDTO.getN_filepath();
                Log.d(TAG, "setDto: " + filepath);
                Glide.with(this).load(filepath).into(nod_imagefile);
            }


        }//if

        return_nodview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragNotice fragNotice = new FragNotice();
                transaction.replace(R.id.main_frag, fragNotice);
                transaction.commit();
            }
        });

        nod_imagefile.setOnClickListener(new View.OnClickListener() {
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

        String imageFileName = noticeDTO.getN_filename();
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
        File f = new File(noticeDTO.getN_filename());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }//galleryAddPic
}
