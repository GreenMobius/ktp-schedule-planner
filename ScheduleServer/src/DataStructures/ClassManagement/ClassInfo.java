package DataStructures.ClassManagement;

public class ClassInfo {
    private String name;
    private String description;
    private Quarter quarter;
    private int year; //Should be the year that the school year ends, so fall, winter, spring, and summer (in order) for 2019-20 would be 2020


    public ClassInfo(String name, String quarterAndYear /*202010 is the example format, 2020 is the year, 10 is fall quarter*/){
        this.name = name;
        this.year = Integer.parseInt(quarterAndYear.substring(0, 4));
        switch(quarterAndYear.substring(4)) {
            case "10":
                quarter = Quarter.FALL;
                break;
            case "20":
                quarter = Quarter.WINTER;
                break;
            case "30":
                quarter = Quarter.SPRING;
                break;
            case "40":
                quarter = Quarter.SUMMER;
                break;
        }


    }
}
