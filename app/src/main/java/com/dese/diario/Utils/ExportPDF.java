package com.dese.diario.Utils;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Eduardo on 30/08/2017.
 */

public class ExportPDF {
    private String TAG= "ExportPDF";
    public Boolean write(String fname, String fcontent) {
        try {
            File apkStorage = null;
            String fpath = Environment.getExternalStorageDirectory() + Constants.mMainDirectory+"/" + fname + ".pdf";
            String fpath2 = "/sdcard/" + fname + ".pdf";
           /* File file = new File(fpath);

            if (!file.exists()) {
                file.createNewFile();
            }*/
            //Get File if SD card is present

            if (new CheckForSDCard().isSDCardPresent()) {

                apkStorage = new File(fpath);
            } else

            if (!apkStorage.exists()) {
                apkStorage.mkdir();
                Log.e(TAG, "Directory Created.");
            }


            com.itextpdf.text.Font bfBold12 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.BOLD, new BaseColor(0, 0, 0));
            com.itextpdf.text.Font bf12 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12);


            Document document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream(apkStorage.getAbsoluteFile()));
            document.open();

            document.add(new Paragraph(fcontent));

            document.close();

            return true;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage().toString());
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            Log.e(TAG, e.getMessage().toString());
            return false;
        }
    }
}
