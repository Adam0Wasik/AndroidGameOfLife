package com.example.canv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MyCanv mCanv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCanv = new MyCanv(this, null);
        setContentView(R.layout.activity_main);

        Button BtStep = findViewById(R.id.bIdStep);
        Button BtClear = findViewById(R.id.bIdClear);

        BtStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCanv.oneStep();
                com.example.canv.MyCanv myCanvas = (com.example.canv.MyCanv) findViewById(R.id.myCanv);
                //redraw canvas
                myCanvas.invalidate();

            }
        });
        BtClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCanv.clear();
                com.example.canv.MyCanv myCanvas = (com.example.canv.MyCanv) findViewById(R.id.myCanv);
                //redraw canvas
                myCanvas.invalidate();

            }
        });

    }
}