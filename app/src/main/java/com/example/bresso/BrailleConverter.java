package com.example.bresso;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 点字変換クラス。
 */
class BrailleConverter {
    private Context context;

    BrailleConverter(Context context) {
       this.context = context;
    }

    /**
     * 点字を読む。
     * @param dots 点
     * @return 点字クラス
     */
    Braille readBraille(List<Boolean> dots) {
        try {
            List<Braille> brailles = readBrailleDictionary("braille_jp.csv");
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
     * 点字辞書を読む。
     * @param fileName 点字辞書として用いるCSVファイルの名前。
     * @return 点字クラスのリスト
     * @throws Exception
     */
    public List<Braille> readBrailleDictionary(String fileName) throws Exception {
        List<Braille> brailles = new ArrayList<Braille>();

        List<List<String>> brailleStrings = readCsv(fileName);
        for (List<String> brailleString : brailleStrings) {
            List<String> dotStrings = brailleString.subList(3, 9);
            List<Boolean> dots = new ArrayList<Boolean>();
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

    /**
     * CSVファイルを読む。
     * @param fileName CSVファイルの名前
     * @return 行ごと、カンマ毎に区切られた、CSVファイルのテキスト。
     * @throws Exception
     */
    private List<List<String>> readCsv(String fileName) throws Exception {
        List<List<String>> row = new ArrayList<List<String>>();

        AssetManager assetManager = context.getResources().getAssets();
        InputStream inputStream = assetManager.open(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferReader = new BufferedReader(inputStreamReader);

        String line;
        while ((line = bufferReader.readLine()) != null) {
            ArrayList<String> col = new ArrayList<String>(Arrays.asList(line.split(",")));
            row.add(col);
        }

        return row;
    }
}
