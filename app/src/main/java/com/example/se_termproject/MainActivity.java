package com.example.se_termproject;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import androidx.cardview.widget.CardView;
import android.widget.LinearLayout;
import androidx.core.widget.NestedScrollView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Dialog memoDialog;
    private EditText memoEditText;
    private EditText dosageEditText;
    private EditText alarmEditText;

    private Button g_button;
    private Button n_button;
    private Button b_button;

    private Map<String, String> memoMap; // 날짜와 메모를 저장하는 맵

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        memoMap = new HashMap<>();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 달력을 누르면 해당 날짜를 전송 및 메모 입력 다이아로그 창 출력
                showMemoDialog(year, month, dayOfMonth);
            }
        });
    }

    private void showMemoDialog(final int year, final int month, final int dayOfMonth) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_memo, null);
        builder.setView(dialogView);

        memoEditText = dialogView.findViewById(R.id.medicine_name_edit_text);
        dosageEditText = dialogView.findViewById(R.id.dosage_edit_text);
        alarmEditText = dialogView.findViewById(R.id.alarm_time_edit_text);

        g_button = dialogView.findViewById(R.id.good_button);
        n_button = dialogView.findViewById(R.id.normal_button);
        b_button = dialogView.findViewById(R.id.bad_button);

        String memo = memoMap.get(getDateKey(year, month, dayOfMonth));
        if (memo != null) {
            memoEditText.setText(memo);
        }

        builder.setTitle(year + "년 " + (month + 1) + "월 " + dayOfMonth + "일")
                .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    // 저장 버튼을 누를 시 메모 정보를 저장.
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String memo = memoEditText.getText().toString();
                        if (!memo.isEmpty()) {
                            //메모 저장 함수 호출
                            saveMemo(year, month, dayOfMonth, memo);
                            //스케줄 저장 합수 호출
                            showSchedule(year, month, dayOfMonth, memo);
                        }
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        memoDialog = builder.create();
        memoDialog.show();
    }

    private void saveMemo(int year, int month, int dayOfMonth, String memo) {
        String dateKey = getDateKey(year, month, dayOfMonth);
        // 맵에 저장 후 토스 메시지 출력
        memoMap.put(dateKey, memo);
        Toast.makeText(this, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show();
        memoDialog.dismiss();
    }
    private String getDateKey(int year, int month, int dayOfMonth) {
        return year + "-" + (month + 1) + "-" + dayOfMonth;
    }

    private void showSchedule(int year, int month, int dayOfMonth, String memo) {
        LinearLayout scheduleContainer = findViewById(R.id.scheduleContainer);
        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 8, 16, 8);
        cardView.setLayoutParams(params);
        cardView.setCardElevation(4);
        cardView.setContentPadding(16, 16, 16, 16);
        cardView.setRadius(8);

        EditText editText = new EditText(this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        
        editText.setText(memo);

        cardView.addView(editText);
        scheduleContainer.addView(cardView);
    }
}


