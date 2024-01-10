package com.example.doc_file_support;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

            webView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            button.setVisibility(View.GONE);

            /*Uri uri = data.getData();

            String result = uri.toString();
            webView.loadUrl("http://docs.google.com/gview?embedded=true&url="+result);*/
            Uri uri = data.getData();


            //.doc file opening in browser where file uploaded in drive the my url link is drive link
            String myUrl = "https://docs.google.com/document/d/1sTt5jqEDcDGk6QaStawnIFmUrKa2bOc5/edit";
            String fileId = myUrl.substring(myUrl.indexOf("/d/") + 3, myUrl.indexOf("/edit"));
            String embedUrl = "https://drive.google.com/file/d/" + fileId + "/preview";
            webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript
            webView.loadUrl(embedUrl);

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
}