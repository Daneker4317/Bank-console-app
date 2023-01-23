package user;

import user.Gender;
import user.Human;

public class Client extends Human {
    private final String login;
    private double currentAccount;
    private double savingAccount;


    public Client(String name, String surName, int age, Gender gender , String login , double currentAccount , double savingAccount) {
        super(name, surName, age, gender);
        this.login = login;
        this.currentAccount = currentAccount;
        this.savingAccount = savingAccount;
    }
}
