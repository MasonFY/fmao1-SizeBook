package com.example.fengyi.fmao1_sizebook;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JSONManagement {

    private final Context context;
    public FileManagement fileManagement;
    public JSONArray jsonArray;

    public JSONManagement(Context context) {

        this.context = context;
        this.fileManagement = new FileManagement(this.context);
        Log.d("JSONManagement", "going to read file...");
        String fileString = this.fileManagement.realAll();
        Log.d("JSONManagement", "read file: " + fileString);
        // 若文件为空，则新建对象；若不为空，读取
        if (fileString.isEmpty()) {
            //Log.d("JSONManagement", "file is empty, adding demo person...");
            this.jsonArray = new JSONArray();
            /*
            Person person = new Person();
            person.name = "Demo Person";
            person.date = "1990-05-21";
            person.comment = "This is a demo person.";
            person.neck = 15.5;
            person.bust = 32;
            this.add(person);
            Log.d("JSONManagement", "add demo person success");*/
        } else {
            try{
                this.jsonArray = new JSONArray(fileString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> names(){

        List<String> nameList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i ++){
            try{
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                nameList.add(jsonObject.optString("name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return nameList;
    }

    public Person personOf(int index){

        Person person = new Person();
        try {
            JSONObject jsonObject = jsonArray.optJSONObject(index);
            person.name = jsonObject.optString("name");
            person.date = jsonObject.optString("date");
            person.neck = jsonObject.optDouble("neck");
            person.bust = jsonObject.optDouble("bust");
            person.chest = jsonObject.optDouble("chest");
            person.waist = jsonObject.optDouble("waist");
            person.hip = jsonObject.optDouble("hip");
            person.inseam = jsonObject.optDouble("inseam");
            person.comment = jsonObject.optString("comment");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!person.hasName()) person.name = "ERROR: Person Name Not Found!";
        return person;
    }

    public Boolean add(Person person){

        if (person.hasName()){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", person.name);
                jsonObject.put("date", person.date);
                jsonObject.put("neck", person.neck);
                jsonObject.put("bust", person.bust);
                jsonObject.put("chest", person.chest);
                jsonObject.put("waist", person.waist);
                jsonObject.put("hip", person.hip);
                jsonObject.put("inseam", person.inseam);
                jsonObject.put("comment", person.comment);
                jsonArray.put(jsonObject);
                fileManagement.write(jsonArray.toString());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Boolean update(Person person, int index){

        if (person.hasName()){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", person.name);
                jsonObject.put("date", person.date);
                jsonObject.put("neck", person.neck);
                Log.d("JSONManagement", "updating neck: " + String.valueOf(person.neck));
                jsonObject.put("bust", person.bust);
                jsonObject.put("chest", person.chest);
                jsonObject.put("waist", person.waist);
                jsonObject.put("hip", person.hip);
                jsonObject.put("inseam", person.inseam);
                jsonObject.put("comment", person.comment);
                jsonArray.put(index, jsonObject);
                fileManagement.write(jsonArray.toString());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public Boolean delete(int index){

        try {
            jsonArray.remove(index);
            fileManagement.write(jsonArray.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
