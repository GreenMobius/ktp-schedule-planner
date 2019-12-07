package DatabaseManagement;

import DataStructures.ClassManagement.ClassInfo;
import DataStructures.ClassManagement.Quarter;
import DataStructures.ClassManagement.Section;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DBCaller {

    public static void UploadCalendar(String filePath) throws Exception{
        ArrayList<Section> coveredSections = new ArrayList<>(); //sections we've already added to the calendar
                                                                //will be used when recording stuff

        //check for .ics file
        if (!filePath.endsWith(".ics")) {
            throw new Exception("invalid file, should be .ics file downloaded from schedule lookup");
        }
        File f = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(f));

        String st;
        while ((st = reader.readLine()) != null) {
            if(st.equals("BEGIN:VEVENT")) {
                System.out.println("Recording event...");

                List<String> buffer = new ArrayList<String>();
                String readingFile;
                while((readingFile = reader.readLine()) != "END:VEVENT") {
                    buffer.add(readingFile);
                }
                if(buffer.size() != 7) {
                    throw new Exception("Error with .ics file, buffer size not large enough");
                }
                coveredSections = AddSectionToDBFromCalendar(buffer, coveredSections);

            }
            System.out.println(st);

        }
    }

    private static ArrayList<Section> AddSectionToDBFromCalendar(List<String> buffer, ArrayList<Section> coveredSections) {
        //There should be 7 lines of information that are used to decode the .ics file
        String summaryClass = buffer.get(0).replace("SUMMARY:", ""); //get rid of the "SUMMARY:" start
        String attendanceAndProfInfo = buffer.get(1); //TODO: figure out how to get this information together
        String location = buffer.get(2).replace("LOCATION:", "");
        String startTime = buffer.get(3).replace("DTSTART:", "");
        String endTime = buffer.get(4).replace("DTEND:", "");
        String quarterAndYear = buffer.get(5).replace("CATEGORIES:","");
        String specificInfo = buffer.get(6);    //should be basically a repeat of summaryClass,
                                                // not sure we need this but I'll leave it here in case



        Section createdSection = new Section();
        ClassInfo q = new ClassInfo();


        return coveredSections;
    }


    private static boolean CheckIfQuarterExistsInDB(ClassInfo q){

        return false;
    }

}