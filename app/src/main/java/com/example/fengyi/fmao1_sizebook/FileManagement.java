package com.example.fengyi.fmao1_sizebook;

import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileManagement {

    private final String fileName = "persons_data.json";
    public final Context context;

    public FileManagement(Context context){
        this.context = context;
    }

    public Boolean write(String string){
        FileOutputStream outputStream;
        try {
            Log.d("FileManagement", "writing...");
            Log.d("FileManagement", "writing string: " + string);
            if (null == this.context) {
                Log.d("FileManagement", "context is null");
            }
            outputStream = this.context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
            Log.d("FileManagement", "write success");
            return true;
        } catch (Exception e) {
            Log.d("FileManagement", "write failed");
            e.printStackTrace();
            return false;
        }
    }

    public String realAll(){
        FileInputStream inputStream;
        try {
            inputStream = context.openFileInput(fileName);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();
            return new String(bytes);
        } catch (Exception e){
            Log.d("FileManagement", "read exception");
            e.printStackTrace();
            return "";
        }
    }
}
