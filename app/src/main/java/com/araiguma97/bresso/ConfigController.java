package com.araiguma97.bresso;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ConfigController {
    private Context context;
    private static final String fileName = "config.csv";

    ConfigController(Context context) {
        this.context = context;
    }

    Intent createIntent(int mode) {
        if (mode == 1) {
            return new Intent(context, StringInputActivity.class);
        } else {
            return new Intent(context, CharacterInputActivity.class);
        }
    }

    int readMode() {
        int mode = 0;

        // 設定ファイルから設定リストを読み込む
        List<List<String>> configList;
        try {
            configList = readConfigList();
        } catch (Exception e) {
            return mode;
        }

        // 設定リストからモードを読み込む
        for (List<String> config : configList) {
            if("mode".equals(config.get(0))) {
                mode = Integer.parseInt(config.get(1));
                break;
            }
        }
        return mode;
    }

    void writeMode(int mode) {
        // 設定リストからモードを読み込み、設定リストに書き込む
        List<List<String>> configList;
        try {
            configList = readConfigList();
        } catch (Exception e) {
            configList = new ArrayList<>();
        }

        boolean itemExists = false;
        for (List<String> config : configList) {
            if ("mode".equals(config.get(0))) {
                itemExists = true;
                config.set(1, String.valueOf(mode));
                break;
            }
        }

        if (! itemExists) {
            List<String> config = Arrays.asList("mode", String.valueOf(mode));
            configList.add(config);
        }

        // 設定リストを設定ファイルに書き込む
        CsvFileWriter csvFileWriter = new CsvFileWriter(context);
        try {
            csvFileWriter.writeToFile(configList, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<List<String>> readConfigList() throws Exception {
        CsvFileReader csvFileReader = new CsvFileReader(context);
        return csvFileReader.readFromFile(fileName);
    }
}
