package com.example.bankexample;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import Interface.inter_setUI;

public abstract class setID_1 extends AppCompatActivity implements inter_setUI {

    //계좌 생성
    Account account1 = new Account("321-1234-1022-99","김동환","941201-1234567","1234",100000000);
    Account account2 = new Account("321-4321-2201-11","김민수","940101-7654321","4321",9999);

    //선언
    private String Choosed_Account, str_name, str_id, str_pw;
    int Money;
    private Spinner spinner;
    TextView tv_account, tv_name, tv_id, tv_pw, tv_money;

    String str_lastaccount2;
    String str_laststate2;
    int money2;
    private static final int REQUEST_CODE = 1;    //상수 값을 선언 상수(항상 같은 수, 변하지 않는 수)

    //ID 세팅
    @Override
    public void set_ID(int i) {
        //ID 매칭
        tv_account = (TextView)findViewById(R.id.tv_account);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_id = (TextView)findViewById(R.id.tv_id);
        tv_pw = (TextView)findViewById(R.id.tv_pw);
        tv_money = (TextView)findViewById(R.id.tv_money);
        spinner = (Spinner)findViewById(R.id.spinner);

        //계좌 선택시
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {   //뭔가를 선택
                Choosed_Account = parent.getItemAtPosition(position).toString();    //선택된 String값 가져오기
                //계좌 클릭에 따른 분기
                Choose(Choosed_Account);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {  //계좌 아무것도 안선택

            }
        });

    }

    //Spinner 계좌 클릭에 따른 분기
    @Override
    public void Choose(String Choosed_Account){
        switch (Choosed_Account) {
            case "계좌를 선택하십시오.":
                tv_account.setText("계좌 번호");
                break;
            case "321-1234-1022-99":
                str_name = account1.getName();
                str_id = account1.getID();
                str_pw = account1.getPw();
                Money = account1.getMoney();
                //계좌 정보 표시
                tv_account.setText(Choosed_Account);
                tv_name.setText(str_name);
                tv_id.setText(str_id);
                tv_pw.setText(str_pw);
                tv_money.setText(Money+"원");
                break;
            case "123-4321-2201-11":
                str_name = account2.getName();
                str_id = account2.getID();
                str_pw = account2.getPw();
                Money = account2.getMoney();
                //계좌 정보 표시
                tv_account.setText(Choosed_Account);
                tv_name.setText(str_name);
                tv_id.setText(str_id);
                tv_pw.setText(str_pw);
                tv_money.setText(Money+"원");
                break;
        }

    }

    //인텐트 값 설정, 세팅된 값에 따른 (String)값넘김, 화면전환 발생
    @Override
    public void set_intent(View v, int key) {
        if(key ==0) {   //연속 클릭 시 intent 중첩 실행되는거 막음
            if (!Choosed_Account.equals("계좌를 선택하십시오.")) {   //계좌 선택 안돼있을때 막음
                Intent intent_pw = new Intent(this, CheckAccount.class);
                intent_pw.putExtra("bb", Choosed_Account);
                intent_pw.putExtra("pw", str_pw);
                intent_pw.putExtra("money",Money);
                switch (v.getId()) {
                    case R.id.iv_1:     //1. 현금입금   deposit
                        intent_pw.putExtra("aa", "deposit");
                        break;
                    case R.id.iv_2:     //2. 예금출금   withdraw
                        intent_pw.putExtra("aa", "withdraw");
                        break;
                    case R.id.iv_3:     //3. 계좌이체   transfer
                        intent_pw.putExtra("aa", "transfer");
                        break;
                    case R.id.iv_4:      //4. 예금조회   check
                        intent_pw.putExtra("aa", "check");
                        break;

                }
                startActivityForResult(intent_pw,REQUEST_CODE);  //실제적인 엑티비티 이동하는 구문
            }
        }
    }

    //CheckAccount에서 받은 값을 실질적으로 처리하는 함수
    public void getResult(int requestCode, Intent data){
        if(requestCode == REQUEST_CODE){    //여기서 값 받음
            str_lastaccount2 = data.getStringExtra("str_lastaccount1");
            str_laststate2 = data.getStringExtra("str_laststate1");
            money2 = data.getIntExtra("money1",0);

            Log.e("str_lastaccount",str_lastaccount2);
            Log.e("str_laststate",str_laststate2);
            Log.e("money",money2 + "");
        }

        //실제 돈 + - 하는 영역
        if(str_lastaccount2.equals("321-1234-1022-99")) {   //첫계좌일 경우
            switch (str_laststate2) {
                case "deposit":
                    account1.plusMoney(money2);
                    break;
                case "withdraw":
                    account1.MinusMoney(money2);
                    break;
                case "transfer":
                    account1.MinusMoney(money2);
                    account2.plusMoney(money2);
                    break;
            }
            Money = account1.getMoney();
            tv_money.setText(account1.getMoney()+"");
        }else if(str_lastaccount2.equals("123-4321-2201-11")){  //둘째 계좌일 경우
            switch (str_laststate2) {
                case "deposit":
                    account2.plusMoney(money2);
                    break;
                case "withdraw":
                    account2.MinusMoney(money2);
                    break;
                case "transfer":
                    account2.MinusMoney(money2);
                    account1.plusMoney(money2);
                    break;
            }
            Money = account2.getMoney();
            tv_money.setText(account2.getMoney()+"");
        }
    }

}
