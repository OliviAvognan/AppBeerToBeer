package com.example.prog1vrai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    Button btnSave, btnLoad;
    EditText etInput;
    TextView tvLoad;
    String filename="";
    String filepath="";
    String fileContent="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSave=findViewById(R.id.btnSave);
        btnLoad=findViewById(R.id.btnLoad);
        etInput=findViewById(R.id.etInput);
        tvLoad=findViewById(R.id.tvLoad);
        filename="myFile.txt";
        filepath="MyfileDir";

        if(!isExternalStorageAvailableForRW()){
            btnSave.setEnabled(false);

        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvLoad.setText("");
                fileContent=etInput.getText().toString().trim();
                if(!fileContent.equals("")){
                    File myExternalFile=new File(getExternalFilesDir(filepath),filename);
                    FileOutputStream fos=null;

                    try {
                        fos= new FileOutputStream(myExternalFile);
                        fos.write(fileContent.getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    etInput.setText("");
                    Toast.makeText(MainActivity.this, "Information saved SD card", Toast.LENGTH_SHORT);

                } else{
                    Toast.makeText(MainActivity.this, "text field can not empty", Toast.LENGTH_SHORT);
                }

            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileReader fr=null;
                File myExternalFile=new File(getExternalFilesDir(filepath),filename);
                StringBuilder stringBuilder=new StringBuilder();
                try {
                    fr=new FileReader(myExternalFile);
                    BufferedReader br=new BufferedReader(fr);
                    String line=br.readLine();
                    while(line!=null){
                        stringBuilder.append(line).append('\n');
                        line=br.readLine();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    String fileContents="Vous avez saisi\n"+ stringBuilder.toString();
                    tvLoad.setText(fileContents);
                }
            }
        });
    }

    private boolean isExternalStorageAvailableForRW() {
        String exStorageState= Environment.getExternalStorageState();
        if(exStorageState.equals(Environment.MEDIA_MOUNTED)){
                return true;
        }
        return false;
    }
}

