package model;

import util.Builder;

/**
 @author Kaliaskaruly Daneker
  * */

public class Client extends Human {
    private final String login;
    private double currentAccount;
    private double savingAccount;

    public Client(Builder builder) { // implemented builder design pattern
        super(builder.getName(),builder.getSurName(), builder.getAge(),builder.getGender());
        this.login = builder.getName();
        this.currentAccount = builder.getCurrentAccount();
        this.savingAccount = builder.getSavingAccount();
    }

    public String getLogin() {
        return login;
    }

    public double getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(double currentAccount) {
        this.currentAccount = currentAccount;
    }

    public double getSavingAccount() {
        return savingAccount;
    }

    public void setSavingAccount(double savingAccount) {
        this.savingAccount = savingAccount;
    }

    public void info(){

        StringBuilder stringBuilder = new StringBuilder();  //  builder design pattern
        stringBuilder.append("name[" + getName());
        stringBuilder.append("] surname[" + getSurName());
        stringBuilder.append( "] age[" + getAge());
        stringBuilder.append( "] gender[" + getGender());
        stringBuilder.append(  "] sum in current account[" + currentAccount);
        stringBuilder.append(  "] sum in saving account[" + savingAccount + "]");

        System.out.println(stringBuilder);
    }

}
