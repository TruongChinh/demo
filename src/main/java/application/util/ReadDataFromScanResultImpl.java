package application.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import application.common.Common;
import application.constant.Constant;
import application.data.entity.Issue;
import application.data.entity.Sample;
import application.data.entity.Summary;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class ReadDataFromScanResultImpl implements ReadDataFromScanResult {

    @Override
    public List<Issue> parserIssueSample() {
        List<Issue> listIsue = new ArrayList<>();
        String rawData = "";
        String directory = "";
        String fileName = Constant.ISSUE_SAMPLES;

        rawData = getDataFromFile(directory, fileName);
        JSONParser parser = new org.json.simple.parser.JSONParser();

        Object obj;

        try {
//            System.out.println("rawData" + rawData); // ChinhTV-debug
            obj = parser.parse(rawData);
            JSONArray listJsonObject = (JSONArray) obj;

            @SuppressWarnings("rawtypes")
            Iterator it = listJsonObject.iterator();

            while (it.hasNext()) {

//				List<Sample> listSampleObj = new ArrayList<Sample>();

                JSONObject jsonObject = (JSONObject) it.next();

                int severity = (int) (long) jsonObject.get(Constant.ISSUE_SEVERITY);
                int type = (int) (long) jsonObject.get(Constant.ISSUE_TYPE);

                Issue issue = new Issue(severity, type);

                JSONArray listSamplesOject = (JSONArray) jsonObject.get(Constant.ISSUE_SAMPLES);

                @SuppressWarnings("rawtypes")
                Iterator sI = listSamplesOject.iterator();

                while (sI.hasNext()) {
                    JSONObject jsonSampleObject = (JSONObject) sI.next();
                    String url = (String) jsonSampleObject.get(Constant.SAMPLE_URL);
                    String dir = (String) jsonSampleObject.get(Constant.SAMPLE_DIR);
                    String request = getDataFromFile(dir, Constant.SAMPLE_REQUEST);
                    String response = getDataFromFile(dir, Constant.SAMPLE_RESPONSE);
                    Sample sample = new Sample(url, request, response);
                    issue.getSamples().add(sample);
                }
                listIsue.add(issue);
            }

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        return listIsue;

    }

    @Override
    public Summary getSummaryObject() {
        String filePath = Constant.SCAN_OUTPUT;
        try {
            File file = getJsFile(filePath, Constant.ISSUE_SUMMARY);
            if (file != null) {
                FileReader reader = new FileReader(file);
                BufferedReader br = new BufferedReader(reader);
                // read line by line
                String line;
                String version = "";
                Date scanDate = new Date();
                int scanTime = 0;
                while ((line = br.readLine()) != null) {
                    if (line.contains("eS_version")) {
                        version = line.substring(line.indexOf("'"), line.lastIndexOf("'"));
                    } else if (line.contains("scan_ms")) {
                        scanTime = Integer.parseInt(line.substring(line.indexOf("=") + 1).replace(";", "").trim(), line.length() - 1);
                    }
                }

                Summary summary = new Summary(scanDate, scanTime, version);
                return summary;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getDataFromFile(String dir, String fileName) {
        if (dir != "") {
            dir = "/" + dir;
        }
        String filePath = Constant.SCAN_OUTPUT + dir;
        String data = "";
        File file;
        try {
            file = getJsFile(filePath, fileName);
            if (file != null) {
                data = readFile(file);
                if (Constant.ISSUE_SAMPLES.equals(fileName)) {
                    data = data.substring(data.indexOf("issue_samples"));
                    data = data.replaceAll("issue_samples = ", "").replaceAll("'", "\"").replaceAll(";", "").replaceAll("\n", "");
                } else if (Constant.SAMPLE_REQUEST.equals(fileName)) {
                    data = Common.trimStringOfNaughtyCharacters(data.replaceAll("var req = \\{'data':'", ""));
                } else if (Constant.SAMPLE_RESPONSE.equals(fileName)) {
                    data = Common.trimStringOfNaughtyCharacters(data.replaceAll("var res = \\{'data':'", ""));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Get file Sample.js from resource
     *
     * @param path
     * @return
     * @throws IOException
     */
    private File getJsFile(String path, String fileName) throws IOException {
        ResourceLoader loader = new FileSystemResourceLoader();
        Resource banner = loader.getResource(path);
        File folder = banner.getFile();

        // Implementing FileFilter to retrieve the files with name sample.js
        if (folder.isDirectory()) {
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File f, String name) {
                    return name.startsWith(fileName.trim());
                }
            };
            // Passing sizeFilter to listFiles() method
            File[] files = folder.listFiles(filter);
            for (File file : files) {
                return file;
            }
        }
        return null;
    }

    /**
     * Read data from File
     *
     * @param file
     * @return raw data which is read from file
     */
    private String readFile(File file) {
        String path = Paths.get(file.getAbsolutePath()).toString();
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    @Override
    public void checkRawData() {
        String rawData = "";
        String directory = "";
        String fileName = Constant.ISSUE_SAMPLES;
        rawData = getDataFromFile(directory, fileName);
        JSONParser parser = new org.json.simple.parser.JSONParser();
        Object obj;
        try {
            obj = parser.parse(rawData);
            JSONArray listJsonObject = (JSONArray) obj;
            List<Issue> listIsue = new ArrayList<>();
            @SuppressWarnings("rawtypes")
            Iterator it = listJsonObject.iterator();
            while (it.hasNext()) {
//				List<Sample> listSampleObj = new ArrayList<Sample>();
                JSONObject jsonObject = (JSONObject) it.next();

                int severity = (int) (long) jsonObject.get(Constant.ISSUE_SEVERITY);
                int type = (int) (long) jsonObject.get(Constant.ISSUE_TYPE);

                Issue issue = new Issue(severity, type);

                JSONArray listSamplesOject = (JSONArray) jsonObject.get(Constant.ISSUE_SAMPLES);
                @SuppressWarnings("rawtypes")
                Iterator sI = listSamplesOject.iterator();
                while (sI.hasNext()) {
                    JSONObject jsonSampleObject = (JSONObject) sI.next();
                    String url = (String) jsonSampleObject.get(Constant.SAMPLE_URL);
                    String dir = (String) jsonSampleObject.get(Constant.SAMPLE_DIR);
                    String request = getDataFromFile(dir, Constant.SAMPLE_REQUEST);
                    String response = getDataFromFile(dir, Constant.SAMPLE_RESPONSE);
                    Sample sample = new Sample(url, request, response);

                    issue.getSamples().add(sample);
                }
                listIsue.add(issue);
            }

//            for (Issue iss : listIsue) {
//                System.out.println(iss.toString());
//            }

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

    }
}

