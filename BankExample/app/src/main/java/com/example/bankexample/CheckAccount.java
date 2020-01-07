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

public class CheckAccount extends AppCompatActivity implements inter_end {

    EditText et_pw;
    Button btn_ok, btn_exit;
    TextView tv_wpw;
    private static final int REQUEST_CODE = 2;
    //Main에서 넘어온 값을 담을 변수
    String str_lastaccount1;
    String str_laststate1;
    String str_pw;
    int int_money;

    int money1;     //Money_flow EditText에 입력된 Int 값을 받을 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_account);

        setId_value();      //ID설정, CheckAccount에서 값 받아옴

        //확인버튼 클릭
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(CheckAccount.this, Money_flow.class);
                intent2.putExtra("str_state",str_laststate1);
                intent2.putExtra("str_account",str_lastaccount1);
                intent2.putExtra("str_pw",str_pw);
                intent2.putExtra("int_money",int_money);

                if(et_pw.getText().toString().equals(str_pw)){  //비밀번호가 맞을 때
                    Toast.makeText(CheckAccount.this,"비밀번호가 확인되었습니다.", Toast.LENGTH_SHORT).show();

                    startActivityForResult(intent2,REQUEST_CODE);  //실제적인 엑티비티 이동하는 구문
                }else{  //비밀번호가 틀릴 때
                    tv_wpw.setText("비밀번호를 다시 입력하세요");
                    et_pw.setText("");
                }


            }
        });
        //취소버튼 클릭
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end(0);
            }
        });
    }


    //Money_flow에서 보낸 값을 -> onActivityResult 이것이 받음
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult","onActivityResult");
        if(resultCode == RESULT_OK){
            Toast.makeText(getApplicationContext(), "수신 성공", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(), "수신 실패", Toast.LENGTH_SHORT).show();
        }
        if(requestCode == REQUEST_CODE){    //여기서 값 받음
           str_lastaccount1 = data.getStringExtra("str_lastaccount");
            str_laststate1 = data.getStringExtra("str_laststate");
            money1 = data.getIntExtra("money",0);

            Log.e("str_lastaccount",str_lastaccount1);
            Log.e("str_laststate",str_laststate1);
            Log.e("money",money1 + "");
        }
    }

    //이 세션으로 다시 뒤로 돌아올땐 강제종료
    @Override
    protected void onRestart() {
        end(money1);
        super.onRestart();

    }

    public void end(int money){ //끝내면서 이전페이지로 값 보냄
        Intent intent_last = new Intent();
        intent_last.putExtra("money1" , money);
        intent_last.putExtra("str_lastaccount1" , str_lastaccount1);
        intent_last.putExtra("str_laststate1" , str_laststate1);
        setResult(RESULT_OK, intent_last);   //결과 값 설정
        finish();
    }
    public void setId_value(){      //ID설정, CheckAccount에서 값 받아옴
        et_pw = (EditText)findViewById(R.id.et_pw);
        btn_ok = (Button)findViewById(R.id.btn_ok);
        btn_exit = (Button)findViewById(R.id.btn_exit);
        tv_wpw = (TextView)findViewById(R.id.tv_wpw);

        //MainActivity에서 값 받아옴
        Intent intent = getIntent();
        str_laststate1 = intent.getStringExtra("aa");
        str_lastaccount1 = intent.getStringExtra("bb");
        str_pw = intent.getStringExtra("pw");
        int_money = intent.getIntExtra("money",0);
    }

    @Override
    public void onBackPressed() {
        end(0);
    }
}
