package com.example.usk.glotus_final.SuperviserApp.ReceptionFiles;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.provider.SyncStateContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyPrintDocumentAdapter extends PrintDocumentAdapter {
    private int totalpages;
    private File pdfFile;

    public MyPrintDocumentAdapter(File pdfFile,int totalpages) {
        this.pdfFile = pdfFile;
        this.totalpages=totalpages;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onLayout(PrintAttributes oldAttributes,
                         PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal,
                         LayoutResultCallback callback,
                         Bundle metadata) {
        try {
            if (cancellationSignal.isCanceled()) {
                callback.onLayoutCancelled();
                return;
            }
            if (totalpages > 0) {
                PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                        .Builder(pdfFile.getName())
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(totalpages);

                PrintDocumentInfo info = builder.build();
                callback.onLayoutFinished(info, true);
            } else {
                totalpages = 0;
                callback.onLayoutFailed("Page count is zero.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onWrite(final PageRange[] pageRanges,
                        final ParcelFileDescriptor destination,
                        final CancellationSignal cancellationSignal,
                        final WriteResultCallback callback) {
        InputStream input = null;
        OutputStream output = null;

        try {
            input = new FileInputStream(pdfFile);
            output = new FileOutputStream(destination.getFileDescriptor());
            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        } catch (FileNotFoundException ee) {
            callback.onWriteFailed(ee.toString());
            return;
        } catch (Exception e) {
            callback.onWriteFailed(e.getMessage());
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
