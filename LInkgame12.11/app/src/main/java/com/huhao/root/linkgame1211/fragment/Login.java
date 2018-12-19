package com.huhao.root.linkgame1211.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import  android.content.SharedPreferences;
import android.widget.RadioButton;
import android.widget.Toast;

import com.huhao.root.linkgame1211.R;


public class Login extends DialogFragment {
private EditText loginname;
private RadioButton girl1;
private RadioButton girl2;
private RadioButton girl3;
private Button sure;
SharedPreferences sp;

public interface LoginInputListener{
    void onLoginInputComplete(String userName, String password);
    }
    private FragmentManager fmanager;
    private FragmentTransaction ftransaction;
    public Login() {

    }


    public static Login newInstance() {
        Login fragment = new Login();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

   View view=inflater.inflate(R.layout.fragment_login,container,false);
   Button sure=(Button)view.findViewById(R.id.sure);
        sure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "注册成功，信息已录入", Toast.LENGTH_SHORT).show();
//点击按钮，出现下一个fragment
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
           fragmentTransaction.add(R.id.container,new LinkGameFragment());
            fragmentTransaction.commit();
            }
        });

        return view;


    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }
private void init(View view){

loginname=view.findViewById(R.id.loginname);

    girl1=view.findViewById(R.id.girl1);
    girl1.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getActivity(), "您选择了该图片", Toast.LENGTH_SHORT).show();


        }
    });
    girl2=view.findViewById(R.id.girl2);
    girl2.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getActivity(), "您选择了该图片", Toast.LENGTH_SHORT).show();

        }
    });
    girl3=view.findViewById(R.id.girl3);
    girl3.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getActivity(), "您选择了该图片", Toast.LENGTH_SHORT).show();

        }
    });
}

    public void onStop() {
        super.onStop();

    }
    public void onResume() {
        super.onResume();

    }


}
