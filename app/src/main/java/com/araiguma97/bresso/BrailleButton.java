package com.araiguma97.bresso;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.appcompat.widget.AppCompatButton;

/**
 * 点字ボタンクラス。
 */
public class BrailleButton extends AppCompatButton implements Checkable {
    private boolean checked = false;

    public BrailleButton(Context context) {
        super(context);
    }

    public BrailleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        changeColor();
    }

    public BrailleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        changeColor();
    }

    // Checkable の実現化
    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        changeColor();
    }

    @Override
    public void toggle() {
        checked = !checked;
        changeColor();
    }

    /**
     * 色を変更する。
     */
    void changeColor() {
        if (checked) {
            this.setTextColor(Color.parseColor("#EEEEEE"));
            this.setBackgroundColor(Color.parseColor("#333333"));
            return;
        }

        this.setTextColor(Color.parseColor("#333333"));
        this.setBackgroundColor(Color.parseColor("#EEEEEE"));
    }
}
