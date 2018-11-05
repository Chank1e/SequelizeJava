package sequelize;

public class ErrorHandler {
    private static Boolean exitOnError = true;

    public static void handle(Exception e){

        e.printStackTrace();

        if(exitOnError)
            System.exit(1);


    }
}
