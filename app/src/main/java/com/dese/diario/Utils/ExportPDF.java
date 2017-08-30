package com.dese.diario.Utils;
/*
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;*/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Eduardo on 30/08/2017.
 */

public class ExportPDF {
    public Boolean write(String fname, String fcontent) {
       /* try {
            String fpath = "/sdcard"+Constants.mDownloadDirectory + fname + ".pdf";
            File file = new File(fpath);

            if (!file.exists()) {
                file.createNewFile();
            }

            com.itextpdf.text.Font bfBold12 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12, com.itextpdf.text.Font.BOLD, new BaseColor(0, 0, 0));
            com.itextpdf.text.Font bf12 = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12);


            Document document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream(file.getAbsoluteFile()));
            document.open();

            document.add(new Paragraph(fcontent));

            //    document.add(new Paragraph("@DavidHackro"));
            document.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }*/
        return false;
    }
}
