import app.ApplicationServiceImpl;

public class Main {
    public static void main(String[] args) {
        // >>> There is anonymous_object without reference variable refer to it , so it uses only at this line for start_application and after that cannot use it
        new ApplicationServiceImpl().startApplication();
    }
}