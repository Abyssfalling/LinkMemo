package com.example.linkmemo;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.example.linkmemo.data.bean.Link;
import com.example.linkmemo.data.bean.LinkItem;
import com.example.linkmemo.data.bean.Point;
import com.example.linkmemo.data.network.NetworkResponseNew;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LinkFragment extends Fragment {
    View view;
    RadioGroup radioGroup;
    Button centerBtn;
    AppCompatImageButton addBtn;
    /**
     * 按钮数组
     */
    Button[] linkBtn = new Button[8];
    String curWord = "Hello";
    String[] colors = new String[]{"#800CDD", "#3BA3F2", "#2A83D8"};

    Link link;

    /**
     * 当前ui的编号
     */
    int currentUiCode = 1;

    private LinkFragment.linkBtnClicked mCallback;

    AssociationViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (LinkFragment.linkBtnClicked) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement searchClicked");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null; // => avoid leaking
    }

    public interface linkBtnClicked {
        void sendDataToBook(String word);

        void sendDataToBookNew(Link word);

        void flash(Link word);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_link, container, false);
        viewModel = new AssociationViewModel();
        radioGroup = view.findViewById(R.id.radioGroup);
        centerBtn = view.findViewById(R.id.centerBtn);
        centerBtn.setText(curWord);
        centerBtn.setOnClickListener(view -> MainActivity.getInstance().switchFragment(3, true));
        linkBtn[0] = view.findViewById(R.id.linkBtn1);
        linkBtn[1] = view.findViewById(R.id.linkBtn2);
        linkBtn[2] = view.findViewById(R.id.linkBtn3);
        linkBtn[3] = view.findViewById(R.id.linkBtn4);
        linkBtn[4] = view.findViewById(R.id.linkBtn5);
        linkBtn[5] = view.findViewById(R.id.linkBtn6);
        linkBtn[6] = view.findViewById(R.id.linkBtn7);
        linkBtn[7] = view.findViewById(R.id.linkBtn8);

        for (int i = 0; i < linkBtn.length; i++) {
            Button button = linkBtn[i];
            button.setOnClickListener(view -> {
                Link data = MainActivity.getInstance().findWord(button.getText().toString().toLowerCase());
                // 这里改为空
                if (data == null) Toast.makeText(view.getContext(), "暂未收录此单词", Toast.LENGTH_SHORT).show();
                else {
                    updateWordNew(data);
                    int id = radioGroup.getCheckedRadioButtonId();
                    if (id == R.id.meanLikeBtn) updateUI(1);
                    else if (id == R.id.lookLikeBtn) updateUI(2);
                    else updateUI(3);
                    mCallback.flash(data);
                }
            });
            int finalI = i;
            button.setOnLongClickListener(view1 -> {

                if (App.userInfo == null) {
                    Toast.makeText(this.getContext(), "请登录后操作！", Toast.LENGTH_SHORT).show();
                    return true;
                }

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this.getContext()).setTitle("删除").setMessage("是否删除次关联关系?").setPositiveButton("确定", (dialogInterface, j) -> {
                    viewModel.addAssociation(App.userInfo.getUser_id(), getCurrentLinkList().get(finalI).getId(), currentUiCode);
                    mCallback.flash(link);
                    updateUI(currentUiCode);
                    Toast.makeText(this.getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                }).setNegativeButton("取消", (dialogInterface, j) -> {
                });
                builder.create().show();
                return true;
            });
        }

        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.meanLikeBtn) updateUI(1);
            else if (i == R.id.lookLikeBtn) updateUI(2);
            else updateUI(3);
        });

        addBtn = view.findViewById(R.id.add);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        addBtn.setVisibility(App.userInfo == null ? View.GONE : View.VISIBLE);
        addBtn.setOnClickListener(view -> {

            View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog, null, false);

            TextView mainWordText = dialogView.findViewById(R.id.dialog_customer_association_input_main);
            AutoCompleteTextView secondaryWordText = dialogView.findViewById(R.id.dialog_customer_association_input_secondary);
            Spinner typeSpinner = dialogView.findViewById(R.id.dialog_customer_association_spinner_type);

            mainWordText.setText(link.getPoint_english());
            typeSpinner.setSelection(0);
            secondaryWordText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    List<Point> pointList = viewModel.getWordByFuzzy(editable.toString()).getData();
                    if (pointList == null || pointList.isEmpty()) {
                        return;
                    }

                    String[] strings = pointList.stream().map(Point::getPoint_english).toArray(String[]::new);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, strings);
                    secondaryWordText.setAdapter(adapter);
                }
            });
            new AlertDialog.Builder(this.getContext()).setTitle("新增词组关系").setView(dialogView).setNegativeButton("取消", (dialog12, which) -> dialog12.dismiss()).setPositiveButton("确定", (dialog1, which) -> {

                if (secondaryWordText.getText() == null || TextUtils.isEmpty(secondaryWordText.getText().toString())) {
                    Toast.makeText(this.getContext(), "请输入关联单词", Toast.LENGTH_SHORT).show();
                    return;
                }

                NetworkResponseNew<String> responseNew = viewModel.addCustomerAssociation(App.userInfo.getUser_id(), link.getPoint_id(), secondaryWordText.getText().toString(), typeSpinner.getSelectedItemPosition() + 1);
                if (responseNew != null && responseNew.getCode() == 200) {
                    mCallback.flash(link);
                    updateUI(currentUiCode);
                } else {
                    Toast.makeText(getContext(), responseNew.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).create().show();
        });
        updateUI(currentUiCode);
    }

    private void setBtnVisible(int cnt) {
        for (int i = 0; i < cnt; i++)
            linkBtn[i].setVisibility(View.VISIBLE);
    }

    private void setBtnColor(int cnt, int color) {
        for (int i = 0; i < cnt; i++)
            linkBtn[i].setBackgroundColor(color);
    }

    private void updateUI(int index) {

        if (link == null){
            return;
        }

        centerBtn.setText(curWord);
        List<Point> linkPoints = new ArrayList<>();

        switch (index) {
            case 1:
                // 近义词
                linkPoints = link.getSynonym().stream().map(LinkItem::getLinkPoint).collect(Collectors.toList());
                break;
            case 2:
                // 反义词
                linkPoints = link.getAntonym().stream().map(LinkItem::getLinkPoint).collect(Collectors.toList());
                ;
                break;
            case 3:
                // 关联词
                linkPoints = link.getRelate().stream().map(LinkItem::getLinkPoint).collect(Collectors.toList());
                ;
                break;
        }

        for (int i = 0; i < 8; i++) {
            Button itemButton = linkBtn[i];
            if (i >= linkPoints.size()) {
                itemButton.setVisibility(View.GONE);
            } else {
                itemButton.setVisibility(View.VISIBLE);
                itemButton.setText(linkPoints.get(i).getPoint_english());
            }
        }
        currentUiCode = index;
    }

    /**
     * 新增 新的数据接收方法
     *
     * @param data 单词
     */
    public void updateWordNew(Link data) {
        this.link = data;
        curWord = data.getPoint_english();
    }

    /**
     * 新增 单词模糊查询
     *
     * @param str 关键字
     * @return 符合关键字的列表
     */
    private List<Point> getWordByFuzzy(String str) {
        return viewModel.getWordByFuzzy(str).getData();
    }

    private List<Point> getCurrentPointList() {
        if (currentUiCode == 1) {
            return link.getSynonym().stream().map(LinkItem::getLinkPoint).collect(Collectors.toList());
        } else if (currentUiCode == 2) {
            return link.getAntonym().stream().map(LinkItem::getLinkPoint).collect(Collectors.toList());
        } else if (currentUiCode == 3) {
            return link.getAntonym().stream().map(LinkItem::getLinkPoint).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private List<LinkItem> getCurrentLinkList() {
        if (currentUiCode == 1) {
            return link.getSynonym();
        } else if (currentUiCode == 2) {
            return link.getAntonym();
        } else if (currentUiCode == 3) {
            return link.getAntonym();
        }
        return new ArrayList<>();
    }

}