package com.example.linkmemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.linkmemo.data.bean.Link;
import com.example.linkmemo.data.bean.Point;

import java.util.HashMap;

public class HomeFragment extends Fragment {
    View view;
    TextView logoTxt;
    EditText searchBar;
    ImageButton searchBtn;

    private searchClicked mCallback;

    public interface searchClicked {
        void sendDataToBookAndLinkNew(Link data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (searchClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context
                    + " must implement searchClicked");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null; // => avoid leaking
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        searchBar = view.findViewById(R.id.search_bar);
        searchBtn = view.findViewById(R.id.search_btn);
        logoTxt = view.findViewById(R.id.logoText);
        //设置logo渐变
        int startColor = Color.parseColor("#800CDD");
        int endColor = Color.parseColor("#3BA3F2");
        logoTxt.setTextColor(startColor);
        Shader textShader = new LinearGradient(0, 0,
                logoTxt.getPaint().measureText(logoTxt.getText().toString()), logoTxt.getTextSize(),
                new int[]{startColor, endColor},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        logoTxt.getPaint().setShader(textShader);
        //设置搜索按钮点击事件
        searchBtn.setOnClickListener(view -> {
            String word = searchBar.getText().toString();
            if (word.isEmpty()) {
                Toast.makeText(view.getContext(), "输入单词为空", Toast.LENGTH_SHORT).show();
                return;
            }
            Link data = MainActivity.getInstance().findWord(word);

            //未找到单词
            if (data == null || data.getPoint_id() == 0) {
                Toast.makeText(view.getContext(), "暂未收录此单词", Toast.LENGTH_SHORT).show();
            } else {
                MainActivity.getInstance().switchFragment(2, true);
                mCallback.sendDataToBookAndLinkNew(data);
            }
        });
        return view;
    }
}