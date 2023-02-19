package util;

import java.util.Scanner;


/**
 @author Kaliaskaruly Daneker
  * */
// Interface Segregation -> divide large interfaces to smaller interfaces grouping by relevant functions
// each class which implemented this interface use only one scanner
public interface SingletonScanner {
    Scanner in = new Scanner(System.in);
}
