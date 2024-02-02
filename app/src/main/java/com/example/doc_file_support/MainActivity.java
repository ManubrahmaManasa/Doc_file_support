package com.example.doc_file_support;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xslf.util.PDFFontMapper;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_FILE_MANAGER = 2;

    TextView textView;
    WebView webView;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        textView = findViewById(R.id.textview);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileManager();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFileManager();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FILE_MANAGER && resultCode == RESULT_OK) {

            /*webView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            button.setVisibility(View.GONE);*/

            Uri uri = data.getData();



            displayDocFile(String.valueOf(uri));
           /* displayExcelFile(String.valueOf(uri));*/

            /*Uri uri = data.getData();

            String result = uri.toString();
            webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+result);*/
            /*Uri uri = data.getData();


            //.doc file opening in browser where file uploaded in drive the my url link is drive link
            String myUrl = "https://docs.google.com/document/d/1sTt5jqEDcDGk6QaStawnIFmUrKa2bOc5/edit";
            String fileId = myUrl.substring(myUrl.indexOf("/d/") + 3, myUrl.indexOf("/edit"));
            String embedUrl = "https://drive.google.com/file/d/" + fileId + "/preview";
            webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript
            webView.loadUrl(embedUrl);
*/
            /*String myUrl = "https://drive.google.com/drive/folders/1Fx6pU39UhqrznH4DuygqRLaiZxBdq3Rk?usp=drive_link";
            String folderId = myUrl.substring(myUrl.indexOf("/folders/") + 9, myUrl.indexOf("?"));
            String folderUrl = "https://drive.google.com/drive/folders/" + folderId;
            webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript
            webView.loadUrl(folderUrl);*/

        }
    }

    private void openFileManager() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, REQUEST_CODE_FILE_MANAGER);

        }
    }

   /* private void convertDocToDocx(String inputPath) throws Exception {
        WebView webView1 = findViewById(R.id.webview);
        webView1.setVisibility(View.VISIBLE);

        if (inputPath.endsWith(".doc")) {
            // For .doc files
            HWPFDocument doc = new HWPFDocument(Files.newInputStream(Paths.get(inputPath)));
            Range range = doc.getRange();

            // Create a new .docx document
            XWPFDocument docx = new XWPFDocument();

            // Copy content from .doc to .docx
            for (int i = 0; i < range.numParagraphs(); i++) {
                String text = range.getParagraph(i).text();
                docx.createParagraph().createRun().setText(text);
            }

            // Convert .docx to HTML
            String htmlContent = convertDocxToHtml(docx);

            // Load HTML content into WebView
            webView1.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
            webView1.setWebViewClient(new WebViewClient());
        } else {
            System.out.println("Input file is not a .doc file");
        }
    }*/

    /*private String convertDocxToHtml(XWPFDocument docx) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            XHTMLConverter.getInstance().convert(docx, byteArrayOutputStream, null);

            return byteArrayOutputStream.toString();
        }
    }*/



    private void displayDocFile(String fileUriString) {
        try {
            WebView webView = findViewById(R.id.webview);
            webView.setVisibility(View.VISIBLE);

            Uri fileUri = Uri.parse(fileUriString);
            InputStream inputStream = getContentResolver().openInputStream(fileUri);

            HWPFDocument doc = new HWPFDocument(inputStream);
            WordExtractor extractor = new WordExtractor(doc);

            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>");
            for (String paragraph : extractor.getParagraphText()) {
                sb.append("<p>").append(paragraph).append("</p>");
            }
            sb.append("</body></html>");

            inputStream.close();

            webView.loadDataWithBaseURL(null, sb.toString(), "text/html", "UTF-8", null);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading document", Toast.LENGTH_SHORT).show();
        }
    }

    }



