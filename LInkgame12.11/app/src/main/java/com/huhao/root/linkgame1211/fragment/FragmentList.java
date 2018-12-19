package com.huhao.root.linkgame1211.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.huhao.root.linkgame1211.R;

public class FragmentList extends ListFragment {

    String rank1[]={
"冠军","亚军","季军"
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
View view=inflater.inflate(R.layout.list_fragment,container,false);

        return view;
    }

    public static FragmentList newInstance() {
        FragmentList fragmentList = new FragmentList();
        return  fragmentList;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String>adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,rank1);

        setListAdapter(adapter);
    }

}
