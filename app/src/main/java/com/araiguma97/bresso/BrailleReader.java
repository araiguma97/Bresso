package com.araiguma97.bresso;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * 点字読み込みクラス。
 */
class BrailleReader {
    private Context context;
    BrailleReader(Context context) {
       this.context = context;
    }

    /**
     * 点字を読む。
     * @param dots 点
     * @return 点字クラス
     */
    Braille read(List<Boolean> dots) {
        try {
            List<Braille> brailles = loadDictionary();
            for (Braille braille : brailles) {
                if (dots.equals(braille.getDots())) {
                    return braille;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 点字辞書を読み込む。
     * @return 点字クラスのリスト
     */
    List<Braille> loadDictionary() throws Exception {
        List<Braille> brailles = new ArrayList<>();

        CsvFileReader csvFileReader = new CsvFileReader(context);
        List<List<String>> brailleStrings = csvFileReader.readFromAssets("braille_jp.csv");
        for (List<String> brailleString : brailleStrings) {
            List<String> dotStrings = brailleString.subList(3, 9);
            List<Boolean> dots = new ArrayList<>();
            for (String dotString : dotStrings) {
                switch (Integer.parseInt(dotString)) {
                    case 0:
                        dots.add(false);
                        break;
                    case 1:
                        dots.add(true);
                        break;
                }
            }

            int id = Integer.parseInt(brailleString.get(0));
            String kana = brailleString.get(1);
            String romaji = brailleString.get(2);
            Braille braille = new Braille(id, dots, kana, romaji);
            brailles.add(braille);
        }

        return brailles;
    }
}
