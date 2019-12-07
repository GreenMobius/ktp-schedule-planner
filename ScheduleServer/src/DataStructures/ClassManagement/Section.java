package DataStructures.ClassManagement;

public class Section {
    private String professorUsername;
    private ClassInfo overheadClass;
    private String timeSlot;
    private int hourLength;
    private String location;

    public Section(String prof, ClassInfo overheadClass, String location, String startTime, String endTime){
        this.professorUsername = prof;
        this.overheadClass = overheadClass;
        this.timeSlot = startTime;

    }
}
