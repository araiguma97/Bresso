package com.araiguma97.bresso;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract public class InputActivity extends AppCompatActivity {
    final int[] brailleButtonIds = {
            R.id.brailleButton1, R.id.brailleButton2, R.id.brailleButton3,
            R.id.brailleButton4, R.id.brailleButton5, R.id.brailleButton6
    };

    // 入力された点
    List<Boolean> dots = new ArrayList<>(
            Arrays.asList(false, false, false, false, false, false)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // アクションバーの生成
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 「Bressoについて」が選択されたら、Bresso紹介ページに遷移
        switch (item.getItemId()) {
            case R.id.option_menu_setting:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.option_menu_about:
                Uri uri = Uri.parse("https://araiguma97.github.io/bresso.html");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
