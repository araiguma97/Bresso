package com.araiguma97.bresso;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CsvFileReader {
    private Context context;
    CsvFileReader(Context context) {
        this.context = context;
    }

    /**
     * CSVファイルを読み込む。
     * @param fileName CSVファイルの名前
     */
    List<List<String>> readFromAssets(String fileName) throws Exception {
        AssetManager assetManager = context.getResources().getAssets();
        return readFromInputStream(assetManager.open(fileName));
    }

    List<List<String>> readFromFile(String fileName) throws Exception {
        String filePath = context.getFilesDir().getPath() + "/" + fileName;
        return readFromInputStream(new FileInputStream(filePath));
    }

    private List<List<String>> readFromInputStream(InputStream inputStream) throws Exception {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        List<List<String>> row = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            ArrayList<String> col = new ArrayList<>(Arrays.asList(line.split(",")));
            row.add(col);
        }

        bufferedReader.close();
        return row;
    }
}
