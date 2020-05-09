package com.araiguma97.bresso;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigController configController = new ConfigController(this);
        Intent intent = configController.createIntent(configController.readMode());
        startActivity(intent);
    }
}
