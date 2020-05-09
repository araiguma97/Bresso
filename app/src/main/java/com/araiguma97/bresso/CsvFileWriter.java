package com.araiguma97.bresso;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

class CsvFileWriter {
    private Context context;
    CsvFileWriter(Context context) {
        this.context = context;
    }

    /**
     * CSVファイルとして書き込む。
     * @param fileName CSVファイルの名前
     */
    void writeToFile(List<List<String>> row, String fileName) throws Exception {
        String filePath = context.getFilesDir().getPath() + "/" + fileName;
        OutputStream outputStream = new FileOutputStream(filePath, false);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        for (List<String> col : row) {
            for (String val : col) {
                bufferedWriter.write(val + ",");
            }
            bufferedWriter.write("\n");
        }

        bufferedWriter.close();
    }
}
