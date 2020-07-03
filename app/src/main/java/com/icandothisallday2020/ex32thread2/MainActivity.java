package com.icandothisallday2020.ex32thread2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    MyThread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //Activity 가 Memory 에서 delete 될 때 자동으로 실행되는 method
    @Override
    protected void onDestroy() {
        //다른 스레드 종료
        //run() method 종료되도록 while 문을 멈추게함
        //t.isRun=!t.isRun;//but 가능하나 객체지향적이지 않은 코드
        //다른 객체의 필드를 직접 수정하기 때문
        t.stopThread();//이 메소드안에서 isRun 변수를 false 로 변경
        super.onDestroy();
    }
    public void click(View view) {
        //무한반복작업-5초마다 현재시간 토스트로 출력
        //이 작업을 수행하는 별도의 Thread 생성
        t=new MyThread();
        t.start();//자동 run() invoke(발동)
    }

    //현재시간을 무한히 출력하는 Thread
    class MyThread extends Thread{
        boolean isRun=true;
        @Override
        public void run() {
            //Thread 의 수행동작은 무조건 run() 안에 작성
            while (isRun){
                //현재시간 출력-date, canlender
                Date now=new Date();//객체가 생성될 때의 시간 보유
                final String s=now.toString();//현재 시간을 문자열로 리턴
                //화면변경(UI)작업 -(ver.28이하에서)별도 Thread 가 할 수 없음
                runOnUiThread(new Runnable() {//이안에서는 UI 작업 가능
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                });
                //5초대기
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {e.printStackTrace();}
            }//while
        }//run()
        //스레드를 종료시키는 기능 메소드
        void stopThread(){
            isRun=false;
        }
    }///////////////////////////////////////
}
