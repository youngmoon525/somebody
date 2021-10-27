package com.example.myteamcproject.Exercise;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.example.myteamcproject.Common.CommonMethod.exlist;
import static com.example.myteamcproject.Common.CommonMethod.explaylist;
import static com.example.myteamcproject.Common.CommonMethod.inputStream;
import static com.example.myteamcproject.Common.CommonMethod.ipConfig;
import static com.example.myteamcproject.Common.CommonMethod.mBluetoothAdapter;
import static com.example.myteamcproject.Common.CommonMethod.tonext;
import static com.example.myteamcproject.Common.CommonMethod.mOutputStream;
import static com.example.myteamcproject.Common.CommonMethod.mStrDelimiter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myteamcproject.Common.CommonMethod;
import com.example.myteamcproject.MainActivity;
import com.example.myteamcproject.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class FragExStart extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragExStart";
    MainActivity activity;
    String e_type;
    int pos;
    FragmentManager fragmentManager;
    TextView tv_count;

    CountDownTimer countDownTimer;
    int timer, count;
    int readBufferPosition;
    CommonMethod commonMethod;
    static final int REQUEST_ENABLE_BT = 10;
    int mPariedDeviceCount = 0;
    Set<BluetoothDevice> mDevices;
    BluetoothDevice mRemoteDevie;

    // Fragment에서는 onClick을 사용할 수 없기때문에, 별도로 리스너를 달아서 클릭이벤트를 지정한다.
    TextView tv_etitle;
    TextView tv_time;
    ImageView img_timer;
    ImageView img_play;
    ImageView img_pause;
    ImageView img_camera;
    ImageView img_exs;
    Timer timers;

    // 폰의 블루투스 모듈을 사용하기 위한 오브젝트.
    BluetoothSocket mSocket = null;
    String mStrDelimiter = "\n";
    char mCharDelimiter = '\n';

    public FragExStart() {

    }

    // 생성자
    public FragExStart(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.frag_exs, container, false);
        activity = (MainActivity) getActivity();
        Bundle bundle = getArguments();
        ExerciseDTO dto = null;
            pos = bundle.getInt("pos");
            dto = (ExerciseDTO) bundle.getSerializable("dto");

        Log.d("TAG", "onCreateView: " + e_type);
        timers = new Timer();

        // Fragment에서는 onClick을 사용할 수 없기때문에, 별도로 리스너를 달아서 클릭이벤트를 지정한다.
        tv_etitle = viewGroup.findViewById(R.id.tv_etitle);
        tv_time = viewGroup.findViewById(R.id.tv_time);
        img_camera = viewGroup.findViewById(R.id.img_camera);
        img_timer = viewGroup.findViewById(R.id.img_timer);
        img_play = viewGroup.findViewById(R.id.img_play);
        img_pause = viewGroup.findViewById(R.id.img_pause);
        img_exs = viewGroup.findViewById(R.id.img_exs);
        tv_count = viewGroup.findViewById(R.id.tv_count);
        img_play.setOnClickListener(this);
        img_pause.setOnClickListener(this);
        commonMethod = new CommonMethod();

        String[] exarray = {"명상"};

        for (int i = 0; i < exarray.length; i++){
            if(explaylist.get(pos).getE_name().equals(exarray[i])){
                tv_count.setVisibility(View.GONE);
                break;
            }else{
                img_timer.setVisibility(View.GONE);
                tv_time.setVisibility(View.GONE);
                img_pause.setVisibility(View.GONE);
            }//if
        }//for

        if(!explaylist.isEmpty()){
            tv_etitle.setText(explaylist.get(pos).getE_name());
        }else{
            tv_etitle.setText("운동제목");
        }

        String filepath = ipConfig + "/resources/"  + dto.getE_filepath() + dto.getE_filename();
        Log.d(TAG, "setDto: " + filepath);
        Glide.with(viewGroup).load(filepath).into(img_exs);

        countDownTimer = new CountDownTimer(30000, 1000){
            public void onTick(long millisUntilFinished) {
                int num = (int) (millisUntilFinished / 1000);
                timer = num;
                tv_time.setText(Integer.toString(timer));
            }

            public void onFinish() {

                try{
                if(explaylist.get(tonext + 1) != null){
                    Bundle bundle = new Bundle(); //번들을 통해 값 전달

                    bundle.putInt("pos", pos + 1);
                    bundle.putSerializable("dto", exlist.get(pos + 1));
                    FragExStart fragExStart = new FragExStart();   //FragExStart 선언
                    FragRest fragRest = new FragRest();
                    fragRest.setArguments(bundle);    //번들을 FragExStart 로 보낼 준비


                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    transaction.replace(R.id.main_frag, fragRest).setTransition(transaction.TRANSIT_FRAGMENT_OPEN);

                    transaction.commit();
                }
                } catch (IndexOutOfBoundsException e){
                    tv_time.setText("운동 끝");
                    FragHome fraghome = new FragHome();
                    getParentFragmentManager().beginTransaction().replace(R.id.main_frag, fraghome).commit();
                }
                commonMethod.sendData("c", mOutputStream, mStrDelimiter, getContext());
                timers.cancel();
            }//onFinish
        };

        tv_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                count = Integer.parseInt( ( (tv_count.getText().toString().trim() == "" ) ? "0" : tv_count.getText().toString().trim()) );

                if(count == 3){
                    commonMethod.sendData("c", mOutputStream, mStrDelimiter, getContext());
                    countDownTimer.onFinish();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return viewGroup;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_play:
                if(mBluetoothAdapter == null){
                    checkBluetooth();
                }//if

                if(tv_time.getVisibility() != View.GONE){
                    countDownTimer.start();
                }//if
                break;
            case R.id.img_pause:
                countDownTimer.cancel();
                break;
        }//switch
        if(inputStream != null){
            commonMethod.sendData("s", mOutputStream, mStrDelimiter, getContext());
            receiveData(tv_count);
        }
    }//onclick

    void checkBluetooth() {
        //Toast.makeText(activity, "기test.", Toast.LENGTH_LONG).show();
//
        /**
         * getDefaultAdapter() : 만일 폰에 블루투스 모듈이 없으면 null 을 리턴한다.
         이경우 Toast를 사용해 에러메시지를 표시하고 앱을 종료한다.
         */
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {  // 블루투스 미지원
            Toast.makeText(getContext(), "기기가 블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG).show();
//            finish();  // 앱종료
        } else { // 블루투스 지원
            /** isEnable() : 블루투스 모듈이 활성화 되었는지 확인.
             *               true : 지원 ,  false : 미지원
             */
            if (!mBluetoothAdapter.isEnabled()) { // 블루투스 지원하며 비활성 상태인 경우.
                Toast.makeText(getContext(), "현재 블루투스가 비활성 상태입니다.", Toast.LENGTH_LONG).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                // REQUEST_ENABLE_BT : 블루투스 활성 상태의 변경 결과를 App 으로 알려줄 때 식별자로 사용(0이상)
                /**
                 startActivityForResult 함수 호출후 다이얼로그가 나타남
                 "예" 를 선택하면 시스템의 블루투스 장치를 활성화 시키고
                 "아니오" 를 선택하면 비활성화 상태를 유지 한다.
                 선택 결과는 onActivityResult 콜백 함수에서 확인할 수 있다.
                 */
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else // 블루투스 지원하며 활성 상태인 경우.
                selectDevice();

        }
    }

    // 블루투스 지원하며 활성 상태인 경우.
    void selectDevice() {
        // 블루투스 디바이스는 연결해서 사용하기 전에 먼저 페어링 되어야만 한다
        // getBondedDevices() : 페어링된 장치 목록 얻어오는 함수.
        mDevices = mBluetoothAdapter.getBondedDevices();
        mPariedDeviceCount = mDevices.size();

        if (mPariedDeviceCount == 0) { // 페어링된 장치가 없는 경우.
            Toast.makeText(getContext(), "페어링된 장치가 없습니다.", Toast.LENGTH_LONG).show();
//            finish(); // App 종료.
        }
        // 페어링된 장치가 있는 경우.
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("블루투스 장치 선택");//.setMessage("test");

        // 각 디바이스는 이름과(서로 다른) 주소를 가진다. 페어링 된 디바이스들을 표시한다.
        List<String> listItems = new ArrayList<String>();

        //페어링된 기기 쿼리
        for (BluetoothDevice device : mDevices) {
            // device.getName() : 단말기의 Bluetooth Adapter 이름을 반환.
            listItems.add(device.getName());
        }

        listItems.add("취소");  // 취소 항목 추가.

        // CharSequence : 변경 가능한 문자열.
        // toArray : List형태로 넘어온것 배열로 바꿔서 처리하기 위한 toArray() 함수.
        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
        // toArray 함수를 이용해서 size만큼 배열이 생성 되었다.
        listItems.toArray(new CharSequence[listItems.size()]);

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                if (item == mPariedDeviceCount) { // 연결할 장치를 선택하지 않고 '취소' 를 누른 경우.
                    Toast.makeText(getContext(), "연결할 장치를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//                    finish();
                } else { // 연결할 장치를 선택한 경우, 선택한 장치와 연결을 시도함.
                    connectToSelectedDevice(items[item].toString());
                }
            }

        });

        builder.setCancelable(false);  // 뒤로 가기 버튼 사용 금지.
        AlertDialog alert = builder.create();

        alert.show();


    }

    //  connectToSelectedDevice() : 원격 장치와 연결하는 과정을 나타냄.
    //   실제 데이터 송수신을 위해서는 소켓으로부터 입출력 스트림을 얻고 입출력 스트림을 이용하여 이루어 진다.
    void connectToSelectedDevice(String selectedDeviceName) {
        // BluetoothDevice 원격 블루투스 기기를 나타냄.
        mRemoteDevie = getDeviceFromBondedList(selectedDeviceName);
        // java.util.UUID.fromString : 자바에서 중복되지 않는 Unique 키 생성.
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        try {
            // 소켓 생성, RFCOMM 채널을 통한 연결.
            // createRfcommSocketToServiceRecord(uuid) : 이 함수를 사용하여 원격 블루투스 장치와
            //                                           통신할 수 있는 소켓을 생성함.
            // 이 메소드가 성공하면 스마트폰과 페어링 된 디바이스간 통신 채널에 대응하는
            //  BluetoothSocket 오브젝트를 리턴함.
            mSocket = mRemoteDevie.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect(); // 소켓이 생성 되면 connect() 함수를 호출함으로써 두기기의 연결은 완료된다.

            // 데이터 송수신을 위한 스트림 얻기.
            // BluetoothSocket 오브젝트는 두개의 Stream을 제공한다.
            // 1. 데이터를 보내기 위한 OutputStrem
            // 2. 데이터를 받기 위한 InputStream
            mOutputStream = mSocket.getOutputStream();
            inputStream = mSocket.getInputStream();
            // 데이터 수신 준비.
            // beginListenForData();

        } catch (Exception e) { // 블루투스 연결 중 오류 발생
            Log.d(TAG, "connectToSelectedDevice: " + e.getStackTrace() + " : " + e.getMessage());
            Toast.makeText(getContext(),
                    "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
//            finish();  // App 종료
        }
    }

    // 블루투스 장치의 이름이 주어졌을때 해당 블루투스 장치 객체를 페어링 된 장치 목록에서 찾아내는 코드.
    BluetoothDevice getDeviceFromBondedList(String name) {
        // BluetoothDevice : 페어링 된 기기 목록을 얻어옴.
        BluetoothDevice selectedDevice = null;
        // getBondedDevices 함수가 반환하는 페어링 된 기기 목록은 Set 형식이며,
        // Set 형식에서는 n 번째 원소를 얻어오는 방법이 없으므로 주어진 이름과 비교해서 찾는다.
        for (BluetoothDevice deivce : mDevices) {
            // getName() : 단말기의 Bluetooth Adapter 이름을 반환
            if (name.equals(deivce.getName())) {
                selectedDevice = deivce;
                break;
            }
        }
        return selectedDevice;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // startActivityForResult 를 여러번 사용할 땐 이런 식으로
        // switch 문을 사용하여 어떤 요청인지 구분하여 사용함.
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) { // 블루투스 활성화 상태
                    selectDevice();
                    Toast.makeText(getContext(), "블루투스 켜짐", Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) { // 블루투스 비활성화 상태 (종료)
                    Toast.makeText(getContext(), "블루투스를 사용할 수 없어 프로그램을 종료안해",
                            Toast.LENGTH_SHORT).show();
//                    finish();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void receiveData(TextView tv_count) {
        byte[] readBuffer;
        //Thread workerThread = null;
        TimerTask timerTask = null;

        final Handler handler = new Handler();
        // 데이터를 수신하기 위한 버퍼를 생성
        readBufferPosition = 0;
        readBuffer = new byte[1024];

        // 데이터를 수신하기 위한 쓰레드 생성
        //workerThread = new Thread(new Runnable() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        // 데이터를 수신했는지 확인합니다.
                        int byteAvailable = inputStream.available();
                        // 데이터가 수신 된 경우
                        if (byteAvailable > 0) {
                            // 입력 스트림에서 바이트 단위로 읽어 옵니다.
                            byte[] bytes = new byte[byteAvailable];
                            inputStream.read(bytes);
                            // 입력 스트림 바이트를 한 바이트씩 읽어 옵니다.
                            for (int i = 0; i < byteAvailable; i++) {
                                byte tempByte = bytes[i];
                                // 개행문자를 기준으로 받음(한줄)
                                if (tempByte == '\n') {
                                    // readBuffer 배열을 encodedBytes로 복사
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    // 인코딩 된 바이트 배열을 문자열로 변환
                                    final String text = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            tv_count.setText(text.trim());

                                            // 텍스트 뷰에 출력
                                            Log.d(TAG, "receiveData: " + text);
                                        }
                                    });
                                } // 개행 문자가 아닐 경우
                                else {
                                    readBuffer[readBufferPosition++] = tempByte;
                                }
                            }
                        }
                    } catch (IOException e) {
                        Log.d(TAG, "receiveDataE: " + e.getMessage() + " : " + e.getStackTrace());
                    }
                    try {
                        // 1초마다 받아옴
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        Log.d(TAG, "receiveDataE: " + e.getMessage() + " : " + e.getStackTrace());
                    }
                }//while
            }
            // });
        };  //timertask
        //workerThread.start();
        timers.schedule(timerTask, 500, 1000);
    }


}
