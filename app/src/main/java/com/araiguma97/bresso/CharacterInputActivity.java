package com.araiguma97.bresso;

import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class CharacterInputActivity extends InputActivity implements View.OnClickListener {
    SoundPool soundPool;
    int[] soundIds = new int[64];
    int soundIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_input);

        // ボタンのクリックリスナーの登録
        for (int brailleButtonId : brailleButtonIds) {
            findViewById(brailleButtonId).setOnClickListener(this);
        }
        findViewById(R.id.buttonClear).setOnClickListener(this);
        findViewById(R.id.buttonSpeak).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // サウンドプールを作成し、サウンドを読み込む
        soundPool = new SoundPool(64, AudioManager.STREAM_MUSIC, 0);
        BrailleReader brailleReader = new BrailleReader(this);
        try {
            List<Braille> brailles = brailleReader.loadDictionary();
            for (Braille braille : brailles) {
                final int resId = getResources().getIdentifier(braille.getRomaji(), "raw", getPackageName());
                soundIds[braille.getNo()] = soundPool.load(getApplicationContext(), resId, 0);
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
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        // 点字ボタン
        for (int i = 0; i < 6; i++) {
            if (view.getId() == brailleButtonIds[i]) {
                BrailleButton brailleButton = findViewById(brailleButtonIds[i]);
                brailleButton.toggle();
                dots.set(i, brailleButton.isChecked());
                updateCharacterViewCharacter();
            }
        }

        switch (view.getId()) {
            case R.id.buttonClear:  // クリアボタン
                for (int i = 0; i < 6; i++) {
                    ((BrailleButton) findViewById(brailleButtonIds[i])).setChecked(false);
                    dots.set(i, false);
                }
                updateCharacterViewCharacter();
                break;
            case R.id.buttonSpeak:  // スピークボタン
                soundPool.play(soundIds[soundIndex], 1.0F, 1.0F, 0, 0, 1.0F);
                break;
        }
    }

    /**
     * 文字ビューを更新する。
     */
    private void updateCharacterViewCharacter() {
        TextView textViewKana = findViewById(R.id.textViewKana);

        // 入力された点を点字として読む
        BrailleReader brailleReader = new BrailleReader(this);
        Braille braille = brailleReader.read(dots);

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
        soundIndex = braille.getNo();
    }
}
