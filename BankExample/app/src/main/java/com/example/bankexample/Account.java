package com.example.bankexample;

public class Account{

    public String Account_Number;
    public String Name;
    public String ID;
    public String Pw;
    public int Money;

    public Account(String Account_Number, String Name, String ID, String Pw, int Money){
        this.Account_Number = Account_Number;
        this.Name = Name;
        this.ID = ID;
        this.Pw = Pw;
        this.Money = Money;
    }

    public String getAccount (){
        return this.Account_Number;
    }
    public String getName(){
        return this.Name;
    }
    public String getID(){
        return this.ID;
    }
    public String getPw(){
        return this.Pw;
    }
    public int getMoney(){
        return this.Money;
    }

    public void plusMoney(int Money){
        this.Money = this.Money + Money;
    }

    public void MinusMoney(int Money){
        this.Money = this.Money - Money;
    }


}
