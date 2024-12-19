package com.example.linkmemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.linkmemo.data.bean.Link;
import com.example.linkmemo.data.bean.Point;

import java.util.Arrays;

public class BookFragment extends Fragment {
    View view;
    ListView translationList;
    TextView wordTxt;
    ArrayAdapter<String> adapter;
    String curWord = "Hello";
    String[] wordMeaning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book, container, false);
        translationList = (ListView) view.findViewById(R.id.translate_List);
        wordTxt = (TextView) view.findViewById(R.id.word_Txt);

        wordTxt.setText(curWord);
        adapter = new ArrayAdapter<>(view.getContext(), R.layout.trans_listview, R.id.transTextView, (wordMeaning == null)?new String[0]:wordMeaning);
        translationList.setAdapter(adapter);
        return view;
    }

    public void updateWord(String data) {
        String[] parts = data.split("-");
        String[] wordAndMeaning = parts[0].split(";");
        curWord = wordAndMeaning[0];
        wordMeaning = new String[wordAndMeaning.length - 1];
        for (int i = 0; i < wordMeaning.length; i++)
            wordMeaning[i] = wordAndMeaning[i + 1];
    }

    /**
     * 新增 新的数据显示方法
     *
     * @param data
     */
    public void updateWordNew(Link data) {

        if (data == null || data.getPoint_chinese() == null){
            return;
        }

        curWord = data.getPoint_english();

        if (data.getPoint_chinese().contains(",")) {
            wordMeaning = data.getPoint_chinese().split(",");
        } else {
            wordMeaning = new String[1];
            wordMeaning[0] = data.getPoint_chinese();
        }

       /* wordMeaning = new String[3];
        for (int i = 0; i < wordMeaning.length; i++) {
            if (i == 0) {
                wordMeaning[0] = data.getSynonym() == null ? "" : data.getSynonym().getPoint_english();
            } else if (i == 1) {
                wordMeaning[1] = data.getAntonym() == null ? "" : data.getAntonym().getPoint_english();
            } else if (i == 2) {
                wordMeaning[2] = data.getRelate() == null ? "" : data.getRelate().getPoint_english();
            } else {
                wordMeaning[i - 1] = "";
            }
        }

        // 这里将删掉的关系的行隐藏掉，因为adapter的列的个数是根据wordMeaning的count走的。第几项空白都一样
        wordMeaning = Arrays.stream(wordMeaning).filter(item -> !TextUtils.isEmpty(item)).toArray(String[]::new);*/

    }

}