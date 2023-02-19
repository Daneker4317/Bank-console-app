package util;

/**
 @author Kaliaskaruly Daneker
  * */
// Interface Segregation -> divide large interfaces to smaller interfaces grouping by relevant functions
public interface App {
    void start() throws Exception;
    default void exit(){
        System.out.println("app closing");
        System.exit(0);
    }
}
