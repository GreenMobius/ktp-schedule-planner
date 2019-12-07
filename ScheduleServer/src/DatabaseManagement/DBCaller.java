package DatabaseManagement;

import DataStructures.ClassManagement.ClassInfo;
import DataStructures.ClassManagement.Section;
import DataStructures.StudentManagement.Student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DBCaller {

    public static void UploadCalendar(Student s, String filePath) throws Exception{
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
        System.out.println("saving schedule to database");

        AddStudentToSections(s, coveredSections);
    }


    /***
     * Adds all students to the DB with their given sections
     * @param s
     * @param coveredSections
     */
    private static void AddStudentToSections(Student s, ArrayList<Section> coveredSections) {

        Student student;
        if((student = TryGetStudent(s)) == null) {
            student = s;
        }

        for(Section sect : coveredSections){
            //TODO: add the student to each section in the DB
        }


    }

    private static Student TryGetStudent(Student s) {
        //TODO call the db to get a student, or return null if there is no student found in the DB

        return null;
    }

    private static ArrayList<Section> AddSectionToDBFromCalendar(List<String> buffer, ArrayList<Section> coveredSections) {
        //There should be 7 lines of information that are used to decode the .ics file
        String summaryClass = buffer.get(0).replace("SUMMARY:", ""); //get rid of the "SUMMARY:" start

        String attendanceAndProfInfo = buffer.get(1); //TODO: figure out how to get this information together
        String[] splitSections = attendanceAndProfInfo.split(";");
        String profName = splitSections[2].split(":")[1];

        String location = buffer.get(2).replace("LOCATION:", "");

        String startTime = buffer.get(3).replace("DTSTART:", "");
        String endTime = buffer.get(4).replace("DTEND:", "");
        startTime = startTime.substring(startTime.indexOf("T")+1);
        endTime = endTime.substring(startTime.indexOf("T")+1);

        String quarterAndYear = buffer.get(5).replace("CATEGORIES:","");

        String specificInfo = buffer.get(6);    //should be basically a repeat of summaryClass,
                                                // not sure we need this but I'll leave it here in case

        //String prof = attendanceAndProfInfo.substring(attendanceAndProfInfo.indexOf(":") + 1);

        ClassInfo classInfo;
        if((classInfo = TryGetClassInfo(summaryClass, quarterAndYear)) == null) {
            classInfo = new ClassInfo(summaryClass, quarterAndYear);
            StoreClassInfo(classInfo);
        }

        Section createdSection;
        if((createdSection = TryGetSectionForClassInfo(classInfo, location, startTime, endTime)) == null){
            createdSection = new Section(profName, classInfo, location, startTime, endTime);
            StoreSection(createdSection);
        }

        coveredSections.add(createdSection);


        return coveredSections;
    }

    /***
     * stores the given section into the database
     * @param createdSection
     */
    private static void StoreSection(Section createdSection) {
        //TODO call the DB to store our newly created section
    }

    /***
     * stores the given classinfo object into the database
     * @param classInfo
     */
    private static void StoreClassInfo(ClassInfo classInfo) {
        //TODO call the DB to store our newly created class
    }

    private static ClassInfo TryGetClassInfo(String summaryClass, String quarterAndYear) {
        //TODO call the db to get a class, or return null if there is no class found in the DB


        return null;
    }


    private static Section TryGetSectionForClassInfo(ClassInfo q, String location, String startTime, String endTime){
        //TODO call the db to get a section, or return null if there is no section found in the DB

        return null;
    }

}