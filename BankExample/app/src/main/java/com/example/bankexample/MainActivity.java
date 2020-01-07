package com.example.bankexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Toast;

// 화면 흐름 MainActivity -> CheckAccount - > Money_flow -> CheckAccount -> MainActivity
public class MainActivity extends setID_1{

    //이미지 버튼을 순간적으로 연속 클릭 시 똑같은 화면이 여러개 뜨는 현상을 막기 위한 변수값
    int key=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("oncreate", "oncreate");

        //아이디 값,클릭 시 기능들 지정
        set_ID(1);
    }

    //이미지 버튼 클릭했을때     //계좌 선택안하면 아무효과없음
    public void onClick1(View v) {
        //화면전환, 각 버튼에 따라 다른 String값을 넘김
        set_intent(v, key);
        key=1;
    }


    //CheckAccount.java 에서 보낸 값을 -> 여기서 받음
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult","onActivityResult");
        //수신 성공여부 판단
        if(resultCode == RESULT_OK){
            Toast.makeText(getApplicationContext(), "수신 성공", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(), "수신 실패", Toast.LENGTH_SHORT).show();
        }

        //여기서 받은 값(돈,상태)들을 다 처리함
        getResult(requestCode, data);
    }

    @Override   //첫 화면으로 다시 돌아올 때 1이였던 key값 다시 0만듬
    protected void onResume() {
        key=0;
        super.onResume();
    }


}
