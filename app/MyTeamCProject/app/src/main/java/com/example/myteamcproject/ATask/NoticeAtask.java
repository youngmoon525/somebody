package com.example.myteamcproject.ATask;

import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.loginDTO;
import static com.example.myteamcproject.Common.CommonMethod.noticeList;
import static com.example.myteamcproject.Common.CommonMethod.noticeDTO;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myteamcproject.Mypage.FoodDTO;
import com.example.myteamcproject.Notice.NoticeDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NoticeAtask extends AsyncTask<Void, Void, String> {

    private static final String TAG = "NoticeAtask";

    String reqC = "";
    int n_numb;
    // 반드시 선언해야 할것들 : 무조건 해야함 복,붙
    HttpClient httpClient;  // 클라이언트 객체
    HttpPost httpPost;      // 클라이언트에 붙일 본문
    HttpResponse httpResponse; // 서버에서의 응답
    HttpEntity httpEntity;  // 응답 내용
    String body;

    public NoticeAtask(){

    }

    public NoticeAtask(String reqcode){

    }//QaATask

    public NoticeAtask(String reqcode, int n_numb) {
        this.reqC = reqcode;
        this.n_numb = n_numb;
    }

    // doInBackground 하기전에 설정 및 초기화
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            // MultipartEntityBuilder 생성 : 무조건 해야함
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.setCharset(Charset.forName("UTF-8"));

            // 전송
            String postURL = ipConfig + "/Notcielist.no";
            if(reqC.equals("readcount")){
                builder.addTextBody("id", loginDTO.getId(), ContentType.create("Multipart/related", "UTF-8"));
                builder.addTextBody("numb", String.valueOf(n_numb), ContentType.create("Multipart/related", "UTF-8"));
                postURL = ipConfig + "/NoticeReadcount.no";
            }//if


            Log.d(TAG, "doInBackground: " + postURL);
            InputStream inputStream = null;
            httpClient = AndroidHttpClient.newInstance("Q&A");
            httpPost = new HttpPost(postURL);
            httpPost.setEntity(builder.build());
            httpResponse = httpClient.execute(httpPost); // 응답
            body = EntityUtils.toString(httpResponse.getEntity());

            // 하나의 오브젝트 가져올때
            if(reqC.equals("readcount")){
                noticeDTO = makedto(body);
            }else{
                noticeList = readMessage(body);
            }


        } catch (IOException e) {
            Log.d(TAG, "doInBackground: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }finally {
            if(httpEntity != null){
                httpEntity = null;
            }
            if(httpResponse != null){
                httpResponse = null;
            }
            if(httpPost != null){
                httpPost = null;
            }
            if(httpClient != null){
                httpClient = null;
            }

        }

        return null;
    }

    // doInBackground 끝난후에 오는부분
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Log.d("main:", "onPostExecute: result => " + result);
    }

    public List<NoticeDTO> readMessage(String body) throws IOException {
        List<NoticeDTO> list = new ArrayList<NoticeDTO>();
        try {
            JSONObject jobject = new JSONObject(body);
            JSONArray jArray = jobject.getJSONArray("list");
            for(int i=0; i<jArray.length();i++) {
                JSONObject row = jArray.getJSONObject(i);
                NoticeDTO dto = new NoticeDTO();
                dto.setN_numb(Integer.parseInt(row.getString("n_numb")));
                dto.setN_title(row.getString("n_title"));
                dto.setN_content(row.getString("n_content"));
                dto.setN_writer((row.getString("n_writer")));
                dto.setN_date(row.getString("n_date"));
                dto.setN_readcount(Integer.parseInt(row.getString("n_readcount")));
                if(!row.isNull("n_filename")){
                    dto.setN_filename(row.getString("n_filename"));
                    dto.setN_filepath(row.getString("n_filepath"));
                }
                if(!row.isNull("n_important")){
                    dto.setN_important(row.getString("n_important"));
                }

                list.add(dto);
            }

        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }

        // 핸들러에게 메시지를 요청
        //Log.d("main:", "readMessage: " + list.get(0).getC_writer() );

        return list;

    }

    public NoticeDTO makedto(String body) throws IOException {
        NoticeDTO dto = null;
        try {
            JSONObject row = new JSONObject(body);
            dto = new NoticeDTO();
            dto.setN_numb(Integer.parseInt(row.getString("n_numb")));
            dto.setN_title(row.getString("n_title"));
            dto.setN_content(row.getString("n_content"));
            dto.setN_writer((row.getString("n_writer")));
            dto.setN_date(row.getString("n_date"));
            dto.setN_readcount(Integer.parseInt(row.getString("n_readcount")));
            if(!row.isNull("n_filename")){
                dto.setN_filename(row.getString("n_filename"));
                dto.setN_filepath(row.getString("n_filepath"));
            }
            if(!row.isNull("n_important")){
                dto.setN_important(row.getString("n_important"));
            }
        } catch (JSONException e) {
            Log.d(TAG, "readMessage: " + e.getStackTrace() + ", msg : " + e.getMessage());
        }
        // 핸들러에게 메시지를 요청
//        Log.d("main:", "readMessage: " + dto.getId() );

        return dto;
    }//makedto

}
