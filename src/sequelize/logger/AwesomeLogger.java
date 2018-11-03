package sequelize.logger;

import java.util.Date;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class AwesomeLogger {
    private File file = new File("./log.txt");

    private FileOutputStream fop;
    private String message;
    private AwesomeLoggerTypes type;
    private static AwesomeLogger ourInstance = new AwesomeLogger();

    public static AwesomeLogger getInstance() {
        return ourInstance;
    }

    private AwesomeLogger() {
        try {
            this.fop = new FileOutputStream(file, true);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public AwesomeLogger setMessage(String message) {
        this.message = message;
        return this;
    }

    public AwesomeLogger setType(AwesomeLoggerTypes type){
        this.type = type;
        return this;
    }

    public void log(){
        Date now = new Date();

        String log = now.toString() +" ["+type.what()+"] " + message + "\n";
        this.writeToFile(log);
        System.out.print(log);
    }

    private void writeToFile(String msg){
        try {

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = msg.getBytes();

            fop.write(contentInBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
