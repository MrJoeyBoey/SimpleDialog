package com.j.simpledialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.acb_simple_dialog).setOnClickListener(view -> {
            new SimpleDialog.Builder(this)
                    .setTitle("测试")
                    .setPositiveButton(v -> {

                    }).show();
        });

        findViewById(R.id.acb_simple_dialog2).setOnClickListener(view -> {
            new SimpleDialog.Builder(this)
                    .setTitle("测试")
                    .setContent("测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容")
                    .setNegativeButton(view1 -> {

                    })
                    .setPositiveButton(v -> {

                    })
                    .setNeutralButton("稍后提醒我", view1 -> {

                    })
                    .setExNegativeButton(v -> {

                    })
                    .setExPositiveButton(v -> {

                    }).show();
        });

        findViewById(R.id.acb_simple_edit_dialog).setOnClickListener(view -> {
            new SimpleEditDialog.Builder(this)
                    .setTitle("测试")
                    .setPositiveButton(v -> {

                    }).show();
        });
    }
}