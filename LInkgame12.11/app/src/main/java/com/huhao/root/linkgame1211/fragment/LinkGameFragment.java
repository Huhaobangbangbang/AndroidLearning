package com.huhao.root.linkgame1211.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.huhao.root.linkgame1211.MainActivity;
import com.huhao.root.linkgame1211.algorithm.Item;
import com.huhao.root.linkgame1211.algorithm.LinkGameAdapter;
import com.huhao.root.linkgame1211.listen.OnItemClickListener;
import com.huhao.root.linkgame1211.R;
import com.huhao.root.linkgame1211.algorithm.Util;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

public class LinkGameFragment extends Fragment {
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int DIFFICULTY = 3;
    public static final int introduce = 1;
    public static final int ROW = 9;
    public static final int COLUMN = 8;


   private  MainActivity mainActivity;
    private int[][] map = new int[ROW][COLUMN];
    private RecyclerView recyclerview;
    private LinkGameAdapter linkGameAdapter;
    private List<Item> itemList = new ArrayList<>();
    private int lastClick = -1;
    private MediaPlayer mediaPlayer;
    private Button start;
    private ImageView bg;
    private long startTime = 0, endTime = 0;
    private boolean isPlaying = false;
    private RadioButton hot;
    private RadioButton quiet;
    SharedPreferences sp;
    private  long time=endTime-startTime;
    private Button login2;

    private SQLiteDatabase db;
    //数据库名称
    private static final String DATABASE_NAME="huhao.db";
    //数据库版本号
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="rank";

    public LinkGameFragment() {
    }

    public static LinkGameFragment newInstance() {
        LinkGameFragment fragment = new LinkGameFragment();
        return fragment;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_link_game, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(@NonNull View view) {
        recyclerview = view.findViewById(R.id.recyclerview);

        start = view.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "游戏开始", Toast.LENGTH_SHORT).show();
                loadData(getActivity().getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("rank", 1));
                bg.setVisibility(View.GONE);
                start.setVisibility(View.GONE);
                startTime = System.currentTimeMillis();//startTime为目前时间
                isPlaying = true;
            }
        });
        hot=view.findViewById(R.id.hot);
        hot.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getActivity(),"音乐重新开始",Toast.LENGTH_SHORT).show();
                startMusic();

            }
        });
        quiet=view.findViewById(R.id.quiet);
        quiet.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getActivity(),"开启静音模式",Toast.LENGTH_LONG).show();
            onStop();
            }
        });
        login2=view.findViewById(R.id.login2);
        login2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getActivity(),"请输入你的信息",Toast.LENGTH_LONG).show();
                //点击按钮，出现下一个fragment
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.container,new Login());

                fragmentTransaction.commit();
            }
        });

        bg = view.findViewById(R.id.bg);
        linkGameAdapter = new LinkGameAdapter(getActivity(), itemList);
        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), COLUMN));
        recyclerview.setAdapter(linkGameAdapter);
        linkGameAdapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Item item = itemList.get(position);
                if (item.isEliminated()) return;//判断选择
                item.setSelect(!item.isSelect());
                linkGameAdapter.notifyItemChanged(position);
               //判断消除
                if (lastClick != -1) {
                    eliminable(lastClick, position);
                } else {
                    lastClick = position;
                }
            }
        });

    }
    public void onResume() {
        super.onResume();
        startMusic();
    }

    private void startMusic() {
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.release();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.high);
        mediaPlayer.start();

    }
    private String getTime() {
        String pattern = "yyyy-MM-dd HH:mm";
        return new SimpleDateFormat(pattern).format(new Date());
    }
    public void onStop() {
        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }

    private void eliminable(int first, int second) {
        lastClick = -1;

        //计算点击位置所在 行和列
        int rowOne = first / COLUMN;
        int columnOne = first - rowOne * COLUMN;

        int rowTwo = second / COLUMN;
        int columnTwo = second - rowTwo * COLUMN;


        int id1 = itemList.get(first).getId(), id2 = itemList.get(second).getId();

        Log.d("onclick", "id1:" + id1 + " id2:" + id2 + " rowOne:" + rowOne + " columnOne:" + columnOne
                + " rowTwo:" + rowTwo + " columnTwo:" + columnTwo);
        Pair<Integer, Integer> pointOne = new Pair<>(rowOne, columnOne);
        Pair<Integer, Integer> pointTwo = new Pair<>(rowTwo, columnTwo);

        if (itemList.get(first).getId() == itemList.get(second).getId() && Util.linkable(map, rowOne, columnOne, rowTwo, columnTwo)) {
            itemList.get(first).setEliminated(true);
            itemList.get(second).setEliminated(true);
            map[rowOne][columnOne] = 0;
            map[rowTwo][columnTwo] = 0;
            if (isGameOver()) {
                endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
               //广播输出信息
                Intent intent=new Intent();
                intent.setAction("sendMsg");
                getActivity().sendBroadcast(intent);
                Toast.makeText(getActivity(), "一局连连看结束～！ 用时：" + (duration / (1000 * 60.0) + "分钟"), Toast.LENGTH_SHORT).show();
       loadData(getActivity().getSharedPreferences("linkgame", Context.MODE_PRIVATE).getInt("rank", 1));
                bg.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
                isPlaying = false;
            }
        } else {
            itemList.get(first).setSelect(false);
            itemList.get(second).setSelect(false);
        }
        linkGameAdapter.notifyItemChanged(first);
        linkGameAdapter.notifyItemChanged(second);
    }

    public void loadData(int rank) {
        itemList.clear();
        int totalAnimal = 10;
        if (rank == EASY) {
            totalAnimal = 10;
        } else if (rank == MEDIUM) {
            totalAnimal = 15;
        } else if (rank == DIFFICULTY) {
            totalAnimal = 25;
        }else if (rank==introduce){
            Toast.makeText(getActivity(),"开发者:胡昊  最后将出现排名",Toast.LENGTH_LONG);
        }
        for (int i = 0; i < (COLUMN * ROW / 2); i++) {
            //每次加两个，保证是偶数
            Item item1 = new Item(i % totalAnimal + 1, false, false);
            Item item2 = new Item(i % totalAnimal + 1, false, false);
            itemList.add(item1);
            itemList.add(item2);
        }
        shuffle();
    }

    private void resetLinkGameMatrix() {
        int size = itemList.size();
        for (int i = 0; i < size; i++) {
            int row = i / COLUMN;
            int column = i - row * COLUMN;
            if (itemList.get(i).isEliminated()) {
                map[row][column] = 0;
            } else {
                map[row][column] = 1;
            }
        }
    }

    private boolean isGameOver() {//游戏结束
        boolean gameOver = true;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (map[i][j] == 1) return false;
            }
        }
        getTime();


        return gameOver;
    }

    public void shuffle() {
        if (lastClick != -1) {
            itemList.get(lastClick).setSelect(false);
            lastClick = -1;
        }

        Collections.shuffle(itemList);
        resetLinkGameMatrix();
        if (linkGameAdapter != null)
            linkGameAdapter.notifyDataSetChanged();
    }

}
