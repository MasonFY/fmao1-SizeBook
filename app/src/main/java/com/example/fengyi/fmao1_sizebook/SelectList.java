package com.example.fengyi.fmao1_sizebook;

import java.util.ArrayList;
import java.util.List;

public class SelectList {

    List<String> year, month, day, neck, bust, chest, waist, hip, inseam;

    public SelectList(){

        year = new ArrayList<String>();
        for (int i = 1900; i <= 2017; i ++) {
            year.add(String.valueOf(i));
        }

        month = new ArrayList<String>();
        for (int i = 1; i <= 12; i ++) {
            month.add(String.valueOf(i));
        }

        day = new ArrayList<String>();
        for (int i = 1; i <= 30; i ++) {
            day.add(String.valueOf(i));
        }

        String format = "%-4.1f";

        neck = new ArrayList<String>();
        for (double i = 0.5; i <= 50; i += 0.5) {
            neck.add(String.format(format, i));
        }

        bust = new ArrayList<String>();
        for (double i = 0.5; i <= 100; i += 0.5) {
            bust.add(String.format(format, i));
        }

        chest = new ArrayList<String>();
        for (double i = 0.5; i <= 110; i += 0.5) {
            chest.add(String.format(format, i));
        }

        waist = new ArrayList<String>();
        for (double i = 0.5; i <= 100; i += 0.5) {
            waist.add(String.format(format, i));
        }

        hip = new ArrayList<String>();
        for (double i = 0.5; i <= 110; i += 0.5) {
            hip.add(String.format(format, i));
        }

        inseam = new ArrayList<String>();
        for (double i = 0.5; i <= 35; i += 0.5) {
            inseam.add(String.format(format, i));
        }
    }
}
