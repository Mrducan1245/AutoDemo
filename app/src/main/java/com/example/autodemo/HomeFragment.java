package com.example.autodemo;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class HomeFragment extends Fragment {
    private Button btnStartServer,btnHFconfirm;
    private TextView tvIfCloseServer;
    private EditText editText;

    private String goalStrign;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home,container,false);
        bindView(view);
        return view ;
    }

    private void bindView(View view) {
        btnStartServer = view.findViewById(R.id.btn_start_server);
        tvIfCloseServer = view .findViewById(R.id.tv_ifclose_service);
        editText = view.findViewById(R.id.et_serch);
        btnHFconfirm = view.findViewById(R.id.btn_hf_confirm);
        btnHFconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable editable = editText.getText();
                goalStrign = editable.toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                  getActivity().getSystemService(AutoBilily.class).eleText = goalStrign;
                }
                Log.e("输入值","查找的字符是"+goalStrign);
            }
        });
        btnStartServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public HomeFragment(){

    }

}
