package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TActivity extends AppCompatActivity {

    @BindView(R.id.buQ)
    Button buQ;
    @BindView(R.id.buW)
    Button buW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t);
        ButterKnife.bind(this);

        buQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TActivity.this, ShowActivity.class);
                startActivity(intent);
            }
        });
    }
}