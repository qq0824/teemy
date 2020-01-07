package com.example.bankexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Interface.inter_end;

public class Money_flow extends AppCompatActivity implements inter_end {

    Button btn_lastok, btn_lastexit;
    TextView tv_1, tv_2, tv_3, tv_send, tv_state, tv_send2, tv_send3, tv_send4;
    EditText et_money;
    int int_lastmoney;
    String str_laststate;
    String str_lastaccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_flow);

        setId_value();    //ID설정, CheckAccount에서 값 받아옴

        //시작 화면 설정
        switch (str_laststate){     //UI설정
            case "deposit":
                setUI(1);
                break;
            case "withdraw":
                setUI(2);
                break;
            case "transfer":
                setUI(3);
                break;
            case "check":
                setUI(4);
                break;
        }
        //확인 버튼 눌렀을때
        btn_lastok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maxminmoney();  //금액에 따른 체크함
            }
        });
        //종료 버튼 눌렀을때
        btn_lastexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               end(0);
            }
        });


    }

    public void end(int money){    //끝내면서 이전페이지로 값 보냄
        Log.e("money 의 값", money + "");
        Intent intent_last = new Intent();
        intent_last.putExtra("money", money);
        intent_last.putExtra("str_lastaccount", str_lastaccount);
        intent_last.putExtra("str_laststate", str_laststate);
        setResult(RESULT_OK, intent_last);   //결과 값 설정
        finish();   //현재 Activity 종료
    }

    public void setId_value(){   //ID설정, CheckAccount에서 값 받아옴
        tv_1 = (TextView)findViewById(R.id.tv_1);
        tv_2 = (TextView)findViewById(R.id.tv_2);
        tv_3 = (TextView)findViewById(R.id.tv_3);
        tv_send = (TextView)findViewById(R.id.tv_send);
        tv_send2 = (TextView)findViewById(R.id.tv_send2);
        tv_send3 = (TextView)findViewById(R.id.tv_send3);
        tv_send4 = (TextView)findViewById(R.id.tv_send4);
        tv_state = (TextView)findViewById(R.id.tv_state);
        btn_lastok = (Button)findViewById(R.id.btn_lastok);
        btn_lastexit = (Button)findViewById(R.id.btn_lastexit);
        et_money = (EditText)findViewById(R.id.et_money);

        //CheckAccount에서 값 받아옴
        Intent intentt = getIntent();
        str_laststate = intentt.getStringExtra("str_state");
        str_lastaccount = intentt.getStringExtra("str_account");
        String str_lastpw = intentt.getStringExtra("str_pw");
        int_lastmoney = intentt.getIntExtra("int_money", 0);
        tv_3.setText("현재 잔액은 "+int_lastmoney+"원 입니다.");
    }
    void setUI(int k){  //Ui 설정
        tv_send.setVisibility(View.GONE);
        tv_send2.setVisibility(View.GONE);
        tv_send3.setVisibility(View.GONE);
        tv_send4.setVisibility(View.GONE);
        switch (k) {
            case 1:
                tv_1.setText("입금 할 금액을 입력 하십시오.(1천만미만)");
                tv_state.setText("입금 하시겠습니까?");
                tv_2.setVisibility(View.GONE);
                break;
            case 2:
                tv_1.setText("출금 할 금액을 입력 하십시오.(1천만미만)");
                tv_state.setText("출금 하시겠습니까?");
                tv_2.setVisibility(View.GONE);
                break;
            case 3:
                tv_send.setVisibility(View.VISIBLE);
                tv_send2.setVisibility(View.VISIBLE);
                tv_send3.setVisibility(View.VISIBLE);
                tv_send4.setVisibility(View.VISIBLE);
                tv_1.setText("이체 할 금액을 입력 하십시오.(1천만미만)");
                tv_state.setText("이체 하시겠습니까?");
                tv_2.setVisibility(View.GONE);
                if(str_lastaccount.equals("321-1234-1022-99")){
                    tv_send2.setText("123-4321-2201-1");
                    tv_send4.setText("321-1234-1022-99");
                }else if(str_lastaccount.equals("123-4321-2201-11")){
                    tv_send2.setText("321-1234-1022-99");
                    tv_send4.setText("123-4321-2201-11");
                }
                break;
            case 4:
                tv_1.setVisibility(View.GONE);
                tv_2.setVisibility(View.GONE);
                tv_state.setVisibility(View.GONE);
                btn_lastok.setVisibility(View.GONE);
                et_money.setVisibility(View.GONE);
                tv_3.setText("현재 잔액은 " + int_lastmoney + "원 입니다.\n 종료하시겠습니까?");
                break;
        }
    }
    void maxminmoney(){ //금액에 따른 체크함
        if(!et_money.getText().toString().equals("")){      //입력금액이 Null이 아닐때
            Integer i = Integer.parseInt(et_money.getText().toString());    //Edittext값을 Int로 받음
            if(i<=int_lastmoney) {   //입력된 금액이 잔액보다 작을때만 실행 됨
                end(i);//종료

            }else if(str_laststate.equals("deposit")){  //혹시나 입력된 금액이 잔액보다 큰데 그게 입금일때 실행됨
                end(i);//종료
            }
            else{
                tv_2.setVisibility(View.VISIBLE);
                et_money.setText("");
            }
        }
        else{       //입력금액이 Null일때
            Toast.makeText(Money_flow.this,"금액을 다시 입력해 주십시오", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        end(0);
    }
}
