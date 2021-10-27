package com.example.myteamcproject.Common;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myteamcproject.Community.CommunityDTO;
import com.example.myteamcproject.Exercise.ExerciseDTO;
import com.example.myteamcproject.Exercise.UserExerciseDTO;
import com.example.myteamcproject.Gift.CartDTO;
import com.example.myteamcproject.Gift.GiftDTO;
import com.example.myteamcproject.Member.MemberDTO;
import com.example.myteamcproject.Mypage.AttendanceDTO;
import com.example.myteamcproject.Mypage.FoodDTO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class CommonMethod {
    // 나의 ip를 선언해 놓는다.
<<<<<<< HEAD
<<<<<<< HEAD
    public static String ipConfig = "http://192.168.0.3:80/project";

    private static final String TAG = "CommonMethod";
=======
    public static String ipConfig = "http://192.168.0.45:80/project";
>>>>>>> c02e2c87c568a2035f371a417852e038ad36a0f3
=======
    public static String ipConfig = "http://221.156.48.92:8083/project";
>>>>>>> main

    // 어느곳에서나 로그인이 되어 있는지 loginDTO를 static으로 생성
    public static MemberDTO loginDTO = null;

    public static List<ExerciseDTO> exlist = null;
    public static ArrayList<UserExerciseDTO> explaylist = null;
    public static int tonext = 0;

    public static List<CommunityDTO> colist = null;
    public static CommunityDTO coDto = null;

    public static List<CommunityDTO> qalist = null;

    public static List<GiftDTO> gflist = null;
    public static List<CartDTO> cartlist = null;
    public static CartDTO cartDto = null;

    public static List<FoodDTO> foodlist = null;
    public static FoodDTO foodDTO = null;

<<<<<<< HEAD
<<<<<<< HEAD
    public static InputStream inputStream = null;
    public static String mStrDelimiter = "\n";
    public static OutputStream mOutputStream = null;
    public static BluetoothAdapter mBluetoothAdapter = null;

=======
>>>>>>> c02e2c87c568a2035f371a417852e038ad36a0f3
=======
    public static List<AttendanceDTO> MyattList = null;

>>>>>>> main
    // 이미지 로테이트 및 사이즈 변경
    public static Bitmap imageRotateAndResize(String path){ // state 1:insert, 2:update
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 8;

        File file = new File(path);
        if (file != null) {
            // 돌아간 앵글각도 알기
            int rotateAngle = setImageOrientation(file.getAbsolutePath());
            Bitmap bitmapTmp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

            // 사진 바로 보이게 이미지 돌리기
            Bitmap bitmap = imgRotate(bitmapTmp, rotateAngle);

            return bitmap;
        }
        return null;
    }//imageRotateAndResize

    // 사진 찍을때 돌린 각도 알아보기 : 가로로 찍는게 기본임
    public static int setImageOrientation(String path){
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int oriention = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        return oriention;
    }

    // 이미지 돌리기
    public static Bitmap imgRotate(Bitmap bitmap, int orientation){

        Matrix matrix = new Matrix();

        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }

    }

    // 우노보드로 데이터 보내기
    public void sendData(String msg, OutputStream mOutputStream, String mStrDelimiter, Context context) {
        try {

            mOutputStream.write(new StringBuilder(String.valueOf(msg)).append(mStrDelimiter).toString().getBytes());
        } catch (Exception e) {
            Toast.makeText(context, "\ub370\uc774\ud130 \uc804\uc1a1 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.", Toast.LENGTH_SHORT).show();

        }
    }

}//CommonMethod
