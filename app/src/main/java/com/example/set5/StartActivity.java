package com.example.set5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// Tsimafei Mardashou, student ID 46011, Introduction to Mobile Systems, Set 5.
public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // The start button opens a new game.
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, GameActivity.class));
            }
        });
    }
}
