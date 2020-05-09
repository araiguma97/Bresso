package com.araiguma97.bresso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViewById(R.id.buttonSettingSave).setOnClickListener(this);
        findViewById(R.id.buttonSettingCancel).setOnClickListener(this);

        ConfigController configController = new ConfigController(this);
        setMode(configController.readMode());
    }

    @Override
    public void onClick(View view) {
        ConfigController configController = new ConfigController(this);
        Intent intent;
        switch (view.getId()) {
            case R.id.buttonSettingSave:
                configController.writeMode(getMode());
                intent = configController.createIntent(getMode());
                startActivity(intent);
                break;
            case R.id.buttonSettingCancel:
                intent = configController.createIntent(configController.readMode());
                startActivity(intent);
                break;
        }
    }

    private int getMode() {
        if (((Switch) findViewById(R.id.switchSettingMode)).isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }

    private void setMode(int mode) {
        switch (mode) {
            case 0:
                ((Switch) findViewById(R.id.switchSettingMode)).setChecked(false);
                break;
            case 1:
                ((Switch) findViewById(R.id.switchSettingMode)).setChecked(true);
                break;
        }
    }
}
