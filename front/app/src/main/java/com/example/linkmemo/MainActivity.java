package com.example.linkmemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alibaba.fastjson.JSON;
import com.example.linkmemo.data.WordRepository;
import com.example.linkmemo.data.bean.Link;
import com.example.linkmemo.data.bean.Point;
import com.example.linkmemo.data.bean.UserInfo;
import com.example.linkmemo.utils.SharedPreferencesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements HomeFragment.searchClicked, LinkFragment.linkBtnClicked {
    //底部导航栏
    BottomNavigationView bottomNavigationView;
    //Fragments
    FragmentManager manager;
    HomeFragment homeFragment = new HomeFragment();
    LinkFragment linkFragment = new LinkFragment();
    BookFragment bookFragment = new BookFragment();
    SettingFragment settingFragment = new SettingFragment();

    PopupWindow userInfoWindow;

    private static MainActivity instance;
    private MainActivityViewModel viewModel;

    public static MainActivity getInstance() {
        return instance;
    }

    //切换Fragment
    public void switchFragment(int index, boolean setBottomNavi) {
        Fragment fragment;
        switch (index) {
            case 1:
                fragment = homeFragment;
                if (setBottomNavi) bottomNavigationView.setSelectedItemId(R.id.home);
                break;
            case 2:
                fragment = linkFragment;
                if (setBottomNavi) bottomNavigationView.setSelectedItemId(R.id.link);
                break;
            case 3:
                fragment = bookFragment;
                if (setBottomNavi) bottomNavigationView.setSelectedItemId(R.id.book);
                break;
            default:
                fragment = settingFragment;
                if (setBottomNavi) bottomNavigationView.setSelectedItemId(R.id.setting);
                break;
        }
        manager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        instance = this;
        //底边栏切换
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        manager = getSupportFragmentManager();
        switchFragment(1, false);
        viewModel = new MainActivityViewModel();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    switchFragment(1, false);
                    return true;
                case R.id.link:
                    switchFragment(2, false);
                    return true;
                case R.id.book:
                    switchFragment(3, false);
                    return true;
                case R.id.setting:
                    switchFragment(4, false);
                    return true;
            }
            return false;
        });

    }

    public void sendDataToBookAndLinkNew(Link data) {
        bookFragment.updateWordNew(data);
        linkFragment.updateWordNew(data);
    }

    @Override
    public void sendDataToBook(String data) {
        bookFragment.updateWord(data);
    }

    public void sendDataToBookNew(Link data) {
        bookFragment.updateWordNew(data);
    }

    @Override
    public void flash(Link word) {
        Link link = findWord(word.getPoint_english());
        sendDataToBookAndLinkNew(link);
        sendDataToBookNew(link);
    }


    private final WordRepository wordRepository = new WordRepository();


    /*
    public String findWord(String word) {
        String data = "";
        try {
            data = viewModel.getDefinition(word);
        } catch (Exception e) {
            Toast.makeText(new App().appContext(), "Network error", Toast.LENGTH_SHORT).show();
        }
        Log.d("tag", data);
        return data;
        // return "ask;vi.询问,问,要求vt.问,要求,需要,邀请--beg;claim;demand;entreat;implore;pray;request;require-beg;claim;demand;entreat;implore;pray;request;require";
    }*/

    /**
     * 新查询方法
     *
     * @param word 单词
     * @return
     */
    public Link findWord(String word) {
        String data = "";
        Link LInk = null;
        try {
            data = viewModel.getDefinitionNew(App.userInfo == null ? 0 : App.userInfo.getUser_id(), word);
            Log.i("TAG", "findWord: " + data);
            LInk = JSON.parseObject(data, Link.class);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
        }
        return LInk;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user:
                if (App.userInfo == null) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    showUserInfoPopWindow(App.userInfo);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 显示用户信息框
     */
    private void showUserInfoPopWindow(UserInfo userInfo) {

        View contentView;

        if (userInfoWindow == null) {
            userInfoWindow = new PopupWindow();
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contentView = inflater.inflate(R.layout.pop_user_bottom, null);
            userInfoWindow.setContentView(contentView);
            userInfoWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT); // 设置宽度
            userInfoWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT); // 设置高度
            userInfoWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 设置背景为透明
            userInfoWindow.setOutsideTouchable(true); // 点击外部区域使userInfoWindow消失
        }

        TextView tvNickName = userInfoWindow.getContentView().findViewById(R.id.pop_user_bottom_nickname);
        TextView tvUserName = userInfoWindow.getContentView().findViewById(R.id.pop_user_bottom_username);
        Button btnLoginOut = userInfoWindow.getContentView().findViewById(R.id.pop_user_bottom_logout);

        tvUserName.setText("@" + userInfo.getUser_name());
        tvNickName.setText(userInfo.getUser_nickname());
        btnLoginOut.setOnClickListener(view -> {
            App.userInfo = null;
            // 保存密码
            SharedPreferencesUtil.clear(getApplicationContext());
            userInfoWindow.dismiss();
            Toast.makeText(instance, "退出成功！", Toast.LENGTH_SHORT).show();
        });

        userInfoWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

}