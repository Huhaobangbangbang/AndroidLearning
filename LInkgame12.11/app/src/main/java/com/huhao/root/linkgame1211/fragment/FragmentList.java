package com.huhao.root.linkgame1211.fragment;

import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.huhao.root.linkgame1211.MainActivity;
import com.huhao.root.linkgame1211.R;

public class FragmentList extends ListFragment {




    public static FragmentList newInstance() {
        FragmentList fragmentList = new FragmentList();
        return  fragmentList;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.list_fragment,container,false);

        return view;
    }




}
