package util;

/**
 @author Kaliaskaruly Daneker
  * */

import model.Gender;
// Interface Segregation -> divide large interfaces to smaller interfaces grouping by relevant functions
public interface BuilderPattern { // Builder design pattern
    Builder setName(String name);

    Builder setSurname(String surName);

    Builder setAge(int age);

    Builder setGender(Gender gender);

    Builder setSalary(double salary);

    Builder setLogin(String login);

    Builder setCurrentAccount(double currentAccount);

    Builder setSavingAccount(double savingAccount);

    Builder build();
}
