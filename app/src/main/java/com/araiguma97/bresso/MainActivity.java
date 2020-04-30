package com.araiguma97.bresso;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int[] brailleButtonIds = {
            R.id.brailleButton1, R.id.brailleButton2, R.id.brailleButton3,
            R.id.brailleButton4, R.id.brailleButton5, R.id.brailleButton6
    };

    // 入力された点
    List<Boolean> dots = new ArrayList<>(
            Arrays.asList(false, false, false, false, false, false)
    );

    SoundPool soundPool;
    int[] soundIds = new int[64];
    int soundIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // アクションバーの生成
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            getSupportActionBar().setElevation(0);
        }

        // ボタンのクリックリスナーの登録
        for (int brailleButtonId : brailleButtonIds) {
            findViewById(brailleButtonId).setOnClickListener(this);
        }
        findViewById(R.id.buttonClear).setOnClickListener(this);
        findViewById(R.id.buttonSpeak).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // サウンドプールを作成し、サウンドを読み込む
        soundPool = new SoundPool(64, AudioManager.STREAM_MUSIC, 0);
        BrailleConverter bc = new BrailleConverter(this);
        try {
            List<Braille> brailles = bc.readBrailleDictionary("braille_jp.csv");
            for (Braille braille : brailles) {
                final int resId = getResources().getIdentifier(braille.getRomaji(), "raw", getPackageName());
                soundIds[braille.getIndex()] = soundPool.load(getApplicationContext(), resId, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundPool.release();
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }

        // 点字ボタン
        for (int i = 0; i < 6; i++) {
            if (v.getId() == brailleButtonIds[i]) {
                BrailleButton brailleButton = findViewById(brailleButtonIds[i]);
                brailleButton.toggle();
                dots.set(i, brailleButton.isChecked());
                updateTextViewKana();
            }
        }

        // クリアボタン
        if (v.getId() == R.id.buttonClear) {
            for (int i = 0; i < 6; i++) {
                ((BrailleButton)findViewById(brailleButtonIds[i])).setChecked(false);
                dots.set(i, false);
            }
            updateTextViewKana();
        }

        // スピークボタン
        if (v.getId() == R.id.buttonSpeak) {
            soundPool.play(soundIds[soundIndex], 1.0F, 1.0F, 0, 0, 1.0F);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 「Bressoについて」が選択されたら、Bresso紹介ページに遷移
        if (item.getItemId() == R.id.option_menu_about) {
            Uri uri = Uri.parse("https://araiguma97.github.io/bresso.html");
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ひらがなテキストビューを更新する。
     */
    private void updateTextViewKana() {
        TextView textViewKana = findViewById(R.id.textViewKana);

        // 入力された点を点字として読む
        BrailleConverter bc = new BrailleConverter(this);
        Braille braille = bc.readBraille(dots);

        if (braille == null) {
            // 点字として読めなければ、何も表示しない
            textViewKana.setTextSize(72.0f);
            textViewKana.setText("");
            soundIndex = 0;
            return;
        }

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (braille.getKana().length() == 1) {
                // 点字がかなであれば、大きい文字サイズで表示する
                textViewKana.setTextSize(108.0f);
            } else {
                // 点字が記号類であれば、小さい文字サイズで表示する
                textViewKana.setTextSize(72.0f);
            }
        } else {
            // 画面が横向きであれば、小さい文字サイズで表示する
            textViewKana.setTextSize(56.0f);
        }
        textViewKana.setText(braille.getKana());
        soundIndex = braille.getIndex();
    }
}
