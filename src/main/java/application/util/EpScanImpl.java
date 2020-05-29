package application.util;


import application.common.Common;
import application.constant.Constant;
import org.omg.CORBA.portable.OutputStream;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class EpScanImpl implements EpScan {

    @Override
    public void scan(String urlToScan, int scanTime) {
        deleteScanResult();
        File file = new File("ePortScan");
        String path = file.getAbsolutePath();
        String timeToScan = formatSeconds(scanTime);
        String timeConfig = " -k " + timeToScan + " -o " + Constant.SCAN_OUTPUT + " ";
        // create a file to store scan log
        File disp = new File(new File(Constant.SCAN_OUTPUT).getAbsolutePath()+"/"+Constant.SCAN_LOG);
        System.out.println(disp.getAbsolutePath());
        Process process = null;
        try {
            String command = path + timeConfig + urlToScan;
            System.out.println("The command to run epScan: " + command);
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            System.out.println("Start to scan!");
            FileOutputStream os  = new FileOutputStream(disp);
            Common common = new Common();
            while ((line = reader.readLine()) != null) {
                line = common.trimStringOfNaughtyCharacters(line+"\n");
                os.write(line.getBytes());
                System.out.println(line);
            }
            os.flush();
            os.close();
            System.out.println("Scanning is done!");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteScanResult() {

        // delete result1 folder
        File tempFile = new File(Constant.SCAN_OUTPUT);

        // make sure directory exists
        if (!tempFile.exists()) {

            System.out.println("Directory does not exist.");
//			System.exit(0);

        } else {

            try {
                System.out.println("Start delete the output-result");
                delete(tempFile);

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }

        System.out.println("Deleting is Done");
    }

    public static void delete(File file) throws IOException {

        if (file.isDirectory()) {

            // directory is empty, then delete it
            if (file.list().length == 0) {

                file.delete();
                System.out.println("Directory is deleted : " + file.getAbsolutePath());

            } else {

                // list all the directory contents
                String files[] = file.list();

                for (String temp : files) {
                    // construct the file structure
                    File fileDelete = new File(file, temp);

                    // recursive delete
                    delete(fileDelete);
                }

                // check the directory again, if empty then delete it
                if (file.list().length == 0) {
                    file.delete();
                    System.out.println("Directory is deleted : " + file.getAbsolutePath());
                }
            }

        } else {
            // if file, then delete it
            file.delete();
            System.out.println("File is deleted : " + file.getAbsolutePath());
        }
    }

    public static String formatSeconds(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int secondsLeft = timeInSeconds - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String formattedTime = "";
        if (hours < 10)
            formattedTime += "0";
        formattedTime += hours + ":";

        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds;

        return formattedTime;
    }

}
