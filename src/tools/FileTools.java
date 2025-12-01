package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileTools {

    public static String readFile(String filePath) {

        // data to be returned
        String data = "";

        // read file
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                data += line + "\n";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return data;
    }

}
