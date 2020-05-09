package com.araiguma97.bresso;

import java.util.List;

/**
 * 点字クラス。
 */
class Braille {
    private int           no;      // 点字番号
    private List<Boolean> dots;    // 点
    private String        kana;    // かな
    private String        romaji;  // かな（ローマ字）

    Braille(int no, List<Boolean> dots, String kana, String romaji) {
        this.no     = no;
        this.dots   = dots;
        this.kana   = kana;
        this.romaji = romaji;
    }

    int getNo() { return no; }
    List<Boolean> getDots() { return dots; }
    String getKana() { return kana; }
    String getRomaji() { return romaji; }
}
