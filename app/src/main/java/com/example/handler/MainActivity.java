package com.example.handler;
/*
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
*/

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

//public class HandlerProgressBarActivity extends AppCompatActivity {

public class MainActivity extends AppCompatActivity {
    /**
     * 声明控件
     */
    ProgressBar progressBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**找到控件*/
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.buttonProgressBar);

        /**设置监听器*/
        button.setOnClickListener(new buttonListener());

    }

    /**
     * 复写监听器方法
     */
    class buttonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            /**设置进度条为可见状态*/
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(100);
            /**把UpdateThread线程加入线程队列*/
            System.out.println("Start UpdateThread!");
            handler.post(UpdateThread);
        }
    }

    Handler handler = new Handler() {
        @Override
        /**handler接收到UpdateProgressBar的SendMessage方法传递来的消息后
         * 当从消息队列里面取出消息时会执行此方法*/
        public void handleMessage(Message msg) {
            /**从传递过来的msg中得到arg1，并把这个值设置成当前的进度条*/
            progressBar.setProgress(msg.arg1);
            /**把UpdateThread线程加入线程队列*/
            handler.post(UpdateThread);
        }
    };

    Runnable UpdateThread = new Runnable() {

        int bar = 0;
        @Override
        public void run() {
            System.out.println("Run UpdateThread!");
            bar += 10;
            /**得到一个消息对象
             * 该消息对象由UpdateProgressBar的obtainMessage提供
             * */
            Message msg = handler.obtainMessage();
            msg.arg1 = bar;
            try{
                /**设置当前的线程睡眠1000ms*/
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(bar == 100) {
                /**当进度条满后，停止UpdateThread线程*/
                System.out.println("End UpdateThread.......");
                progressBar.setVisibility(View.GONE);
                handler.removeCallbacks(UpdateThread);
            }
            else
                handler.sendMessage(msg);
        }
    };
}
