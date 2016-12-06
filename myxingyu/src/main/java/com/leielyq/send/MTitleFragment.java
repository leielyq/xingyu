package com.leielyq.send;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.myxingyu.R;
import com.leielyq.bean.MyType;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MTitleFragment extends Fragment {


    @Bind(R.id.item_title)
    EditText itemTitle;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.item_context)
    EditText itemContext;
    @Bind(R.id.hp)
    EditText hp;
    private List<String> mdatas;
    private ArrayAdapter<String> adapter;

    public MTitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mtitle, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mdatas = new ArrayList<>();

        BmobQuery<MyType> query = new BmobQuery<>();
        query.findObjects(new FindListener<MyType>() {
            @Override
            public void done(List<MyType> list, BmobException e) {
                if (e == null) {
                    for (MyType i : list)
                        mdatas.add(i.getType());
                    adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mdatas);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                } else
                    Log.d("TitleFragment", e.toString());

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String TitleToString() {
        String str_title = itemTitle.getText().toString();
        if (str_title.length() < 1)
            return null;
        else
            return str_title;
    }

    public String ContextToString() {
        String str_context = itemContext.getText().toString();
        if (str_context.length() < 1)
            return null;
        else
            return str_context;
    }

    public String TypeToString() {
        return spinner.getSelectedItem().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public Integer HpToString() {
        if (hp.getText().equals(""))
            return Integer.parseInt(hp.getText().toString());
        else
            return null;
    }
}
