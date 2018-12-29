package com.huhao.root.linkgame1211;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.sql.DatabaseMetaData;
import  java.sql.SQLData;
import com.huhao.root.linkgame1211.fragment.FragmentList;
import com.huhao.root.linkgame1211.fragment.LinkGameFragment;
import com.huhao.root.linkgame1211.fragment.Login;
import  android.database.sqlite.SQLiteDatabase;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity   {
    private Toolbar toolbar;
    private LinkGameFragment linkGameFragment = null;
    private FragmentList fragmentList=null;
    private Login login=null;
    private Fragment mcontent=null;
    private  MyReceiver receiver;
    private RadioButton hot;
    private RadioButton quiet;
      SharedPreferences sp;
    private EditText loginname;
    String filename="login_file";

    private SQLiteDatabase db;
    //数据库名称
    private static final String DATABASE_NAME="huhao.db";
    //数据库版本号
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="rank";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //采用preference 存储用户关于背景音乐的个人设置，允许用户设置默认音效和背景音乐的开关
        hot=(RadioButton)findViewById(R.id.hot);
        quiet=(RadioButton)findViewById(R.id.quiet);
        sp=getSharedPreferences("config",MODE_PRIVATE);


        loginname=findViewById(R.id.loginname);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("LinkGame");

        linkGameFragment = LinkGameFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, linkGameFragment).commit();

    }
    public void Login(View view)throws IOException {
        FileOutputStream fileOutputStream=openFileOutput(filename,Context.MODE_PRIVATE);
        String name=loginname.getText().toString();
        fileOutputStream.write((name).getBytes());
        fileOutputStream.close();
    }





    public void onClick(View v){
        switch (v.getId()){
            case R.id.hot:
                String etString= quiet.getText().toString();
                saveData(etString);
                break;
            case  R.id.quiet:
                String etString2=hot.getText().toString();
                saveData(etString2);
                break;
        }
    }
    private void saveData(String string){

        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("content",string);
        editor.commit();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
                       //菜单的组成
    public boolean onOptionsItemSelected(MenuItem item) {
        if (linkGameFragment == null || !linkGameFragment.isPlaying())
            return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.shuffle:
                linkGameFragment.shuffle();
                break;
            case R.id.easy:
                linkGameFragment.loadData(LinkGameFragment.EASY);
                //记录用户选择难度
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("rank", LinkGameFragment.EASY).apply();
                break;
            case R.id.medium:
                linkGameFragment.loadData(LinkGameFragment.MEDIUM);
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("rank", LinkGameFragment.MEDIUM).apply();
                break;
            case R.id.introduce:
                Toast.makeText(MainActivity.this,"开发者:胡昊  最后将出现排名",Toast.LENGTH_SHORT).show();
                break;
            case R.id.diffculty:
                linkGameFragment.loadData(LinkGameFragment.DIFFICULTY);
                getSharedPreferences("linkgame", MODE_PRIVATE).edit().putInt("rank", LinkGameFragment.DIFFICULTY).apply();
                break;

            case R.id.newGame:
                linkGameFragment.loadData(getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("rank", 1));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        int rank = getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("rank", 1);
        switch (rank) {
            case LinkGameFragment.EASY:
                menu.findItem(R.id.easy).setChecked(true);
                break;
            case LinkGameFragment.MEDIUM:
                menu.findItem(R.id.medium).setChecked(true);
                break;
            case LinkGameFragment.DIFFICULTY:
                menu.findItem(R.id.diffculty).setChecked(true);;
            case R.id.introduce:
                Toast.makeText(MainActivity.this,"开发者:胡昊  最后将出现排名",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }
    class MyReceiver extends BroadcastReceiver{

        public void onReceive(Context context, Intent intent) {
       receiver=new  MyReceiver();
            IntentFilter filter=new IntentFilter();
            filter.addAction("sendMsg");
            registerReceiver(receiver,filter);
            fragmentList=FragmentList.newInstance();
        }
    }
}
