package com.example.fengyi.fmao1_sizebook;

public class Person {

    public String name, date, comment;
    public double neck, bust, chest, waist, hip, inseam;

    public Person(){
        this.name = "";
        this.date = "";
        this.comment = "";
        this.neck = -1;
        this.bust = -1;
        this.chest = -1;
        this.waist = -1;
        this.hip = -1;
        this.inseam = -1;
    }

    public Boolean hasName(){
        return !name.isEmpty();
    }

    public Boolean hasDate(){
        return !date.isEmpty();
    }

    public Boolean hasComment(){
        return !comment.isEmpty();
    }

    public Boolean hasNeck(){
        return neck > 0;
    }

    public Boolean hasBust(){
        return bust > 0;
    }

    public Boolean hasChest(){
        return chest > 0;
    }

    public Boolean hasWaist(){
        return waist > 0;
    }

    public Boolean hasHip(){
        return hip > 0;
    }

    public Boolean hasInseam(){
        return inseam > 0;
    }
}
