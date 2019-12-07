package DataStructures.StudentManagement;

import DataStructures.ClassManagement.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * represents a Student's information for the quarter
 */
public class StudentQuarter {
    private Student student;
    private List<Section> quarterlyClasses;

    public StudentQuarter(Student s){
        this.student = s;
        quarterlyClasses = new ArrayList<Section>();
    }

    public void AddClassSectionToSchedule(Section sect){
        quarterlyClasses.add(sect);
    }
}
