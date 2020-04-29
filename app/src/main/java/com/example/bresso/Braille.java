package com.example.bresso;

import java.util.List;

/**
 * 点字クラス。
 */
class Braille {
    private int           index;   // ユニークな点字インデックス。
    private List<Boolean> dots;    // 点
    private String        kana;    // かな
    private String        romaji;  // かな（ローマ字）

    Braille(int index, List<Boolean> dots, String kana, String romaji) {
        this.index  = index;
        this.dots   = dots;
        this.kana   = kana;
        this.romaji = romaji;
    }

    int getIndex() { return index; }
    List<Boolean> getDots() { return dots; }
    String getKana() { return kana; }
    String getRomaji() { return romaji; }
}
