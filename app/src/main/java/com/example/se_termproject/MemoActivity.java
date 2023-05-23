package com.example.se_termproject;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

public class MemoActivity extends PopupWindow {

    private EditText memoEditText;
    private Button saveButton;


    // 기본 생성자 추가
    public MemoActivity() {
        // 빈 생성자로 아무 작업도 하지 않음
    }

    public MemoActivity(Context context) {
        super(context);
        setupView(context);
        setupListeners();
    }

    private void setupView(Context context) {
        // 팝업 레이아웃 설정
        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.activity_memo, null);
        setContentView(popupView);

        // EditText와 Button 참조
        memoEditText = popupView.findViewById(R.id.memoEditText);
        saveButton = popupView.findViewById(R.id.saveButton);

        // 팝업 창의 크기와 배경 설정
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // 포커스를 설정하여 EditText에 입력 가능하도록 함
        setFocusable(true);
    }

    private void setupListeners() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memoText = memoEditText.getText().toString();
                // 메모를 저장하거나 처리하는 로직을 추가
                // 저장 후 팝업 창을 닫을 수도 있음
                dismiss();
            }
        });
    }
}

