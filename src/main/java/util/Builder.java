package util;

import model.Gender;


/**
 @author Kaliaskaruly Daneker
  * */
public class Builder implements BuilderPattern{ // Dependency inversion -> Abstractions should not depend on details. Details should depend on abstractions.

    private String name;
    private String surName;
    private int age;
    private Gender gender;
    private double salary;
    private String login;
    private double currentAccount;
    private double savingAccount;

    public Builder() {}

    @Override
    public Builder setName(String name){
        this.name = name;
        return this;
    }
    @Override
    public Builder setSurname(String surName){
        this.surName = surName;
        return this;
    }
    @Override
    public Builder setAge(int age){
        this.age = age;
        return this;
    }
    @Override
    public Builder setGender(Gender gender){
        this.gender = gender;
        return this;
    }
    @Override
    public Builder setSalary(double salary){
        this.salary = salary;
        return this;
    }
    @Override
    public Builder setLogin(String login){
        this.login = login;
        return this;
    }
    @Override
    public Builder setCurrentAccount(double currentAccount){
        this.currentAccount = currentAccount;
        return this;
    }
    @Override
    public Builder setSavingAccount(double savingAccount){
        this.savingAccount = savingAccount;
        return this;
    }


    public Builder build(){
        return this;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public double getSalary() {
        return salary;
    }

    public String getLogin() {
        return login;
    }

    public double getCurrentAccount() {
        return currentAccount;
    }

    public double getSavingAccount() {
        return savingAccount;
    }
}