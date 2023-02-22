package model;


/**
 @author Kaliaskaruly Daneker
  * */

public abstract class Human {// parent abstract class  DJ
    private String name;
    private String surName;
    private int age;
    private Gender gender;

    public Human(String name, String surName, int age, Gender gender) {
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


}
