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

        findViewById(R.id.acb_simple_edit_dialog).setOnClickListener(view -> {
            new SimpleEditDialog.Builder(this)
                    .setTitle("测试")
                    .setPositiveButton(v -> {

                    }).show();
        });
    }
}