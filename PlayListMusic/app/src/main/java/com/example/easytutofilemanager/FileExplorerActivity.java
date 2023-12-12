package com.example.easytutofilemanager;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileExplorerActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_CODE = 1;

    private TextView view;
    private File currentFolder;
    private List<String> fileList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explorer);

         view=findViewById(R.id.textView);
        ListView listView = findViewById(R.id.listView);
        Button btnUp = findViewById(R.id.btnUp);
        Button btnSelect = findViewById(R.id.btnSelect);
        Button btnExit = findViewById(R.id.btnExit);
        fileList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);
        listView.setAdapter(adapter);

        if (checkPermission()) {
            loadRootFolder();
        } else {
            requestPermission();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFile = fileList.get(position);
                File clickedFile = new File(currentFolder, selectedFile);

                if (clickedFile.isDirectory()) {
                    displayFolderContents(clickedFile);
                }
            }
        });

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFolder.getParentFile() != null) {
                    displayFolderContents(currentFolder.getParentFile());
                }
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFolder != null && currentFolder.isDirectory()) {
                    File[] files = currentFolder.listFiles();

                    if (files != null && files.length > 0) {
                        boolean containsMp3File = false;

                        for (File file : files) {
                            if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                                containsMp3File = true;
                                break;
                            }
                        }

                        if (containsMp3File) {
                            // Thực hiện các hành động khi có ít nhất một tệp .mp3 trong thư mục
                            String selectedFileName = view.getText().toString();
                            if (!selectedFileName.isEmpty()) {
                                String filePath = currentFolder.getPath() ;
                                Log.d("Testnha", "file name "+ filePath);
                                playMusic(filePath);
                            } else {
                                Toast.makeText(FileExplorerActivity.this, "Không có tệp nào được chọn", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(FileExplorerActivity.this, "Thư mục không chứa tệp .mp3", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(FileExplorerActivity.this, "Thư mục trống rỗng", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void loadRootFolder() {
        File rootFolder = Environment.getExternalStorageDirectory();
        displayFolderContents(rootFolder);
    }

    private void displayFolderContents(File folder) {

        currentFolder = folder;
        fileList.clear();

        view.setText(folder.toString());
        File f = new File(folder.getPath());
        File[] files = f.listFiles();
        if(files != null) {
            for (File fItem : files) {
                Log.d("FileExplorer", "file name "+ fItem.getName());
                fileList.add(fItem.getName());
            }
        }
        adapter.notifyDataSetChanged();

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else
            return false;
    }

    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(this,"Storage permission is requires,please allow from settings",Toast.LENGTH_SHORT).show();
        }else
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                loadRootFolder();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    private void playMusic(String filePath) {
        Intent intent = new Intent(this, PlaymusicActivity.class);
        intent.putExtra("FILE_PATH", filePath);
        startActivity(intent);
    }

}
