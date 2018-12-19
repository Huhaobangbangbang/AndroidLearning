package com.huhao.root.linkgame1211;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import com.huhao.root.linkgame1211.fragment.FragmentList;
import com.huhao.root.linkgame1211.fragment.LinkGameFragment;
import com.huhao.root.linkgame1211.fragment.Login;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LinkGameFragment linkGameFragment = null;
    private FragmentList   fragmentList=null;
    private Login login=null;
    private Fragment mcontent=null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("LinkGame");
        if (mcontent==null)
           showlogindefault();
        //切换fragment
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container,new Login());
        fragmentTransaction.commit();
    }
    //设定自动fragment
    public void showlogindefault(){
         if(login==null)
            login=Login.newInstance();
            FragmentTransaction mFragmentTrans = getSupportFragmentManager().beginTransaction();
            mFragmentTrans.add(R.id.container, login).commit();
            mcontent = login;
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
                menu.findItem(R.id.diffculty).setChecked(true);
                break;
            case R.id.introduce:
                Toast.makeText(MainActivity.this,"开发者:胡昊  最后将出现排名",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
