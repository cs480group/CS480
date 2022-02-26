import java.lang.invoke.SwitchPoint;

public class Student implements Comparable<Student>{
    // fields
    private int id;
    private String gradQ;
    private int gradY;
    private int taType;
    // represent classes currently offered. CS102 idx 0; CS492 idx 44
    private Boolean eburg;
    // 4x7 array representing day and timeslots  
    private Boolean [][] dates; 
    private Boolean [] taken;
    

    // constructor
    public Student (int id, String gradQ, int gradY, int ta, Boolean eb, Boolean [][] dates, Boolean [] taken) {
        this.id = id;
        this.gradQ = gradQ;
        this.gradY = gradY;
        this.taType = ta;
        this.eburg = eb;
        this.taken = taken;
        this.dates = dates;
    }

    public int getId() { 
        return this.id; 
    }

    public String getGradQ() {
        return this.gradQ;
    }

    public void setGradQ(String gradDate) {
        this.gradQ = gradDate;
    }

    public int getGradY() {
        return this.gradY;
    }

    public void setGradY(int gradDate) {
        this.gradY = gradDate;
    }

    public int getTaType() {
        return this.taType;
    }

    public void setTaType(int type) {
        this.taType = type;
    }

    public Boolean inEburg() {
        return this.eburg;
    }

    // return true if a student has takened the given class
    public Boolean hasTaken (int cla) {
        return taken[cla];
    }

    // return truee if student does not have a time conflict
    public Boolean isFree (int day, int timeslot) {
        // classes are usually offer at the same time in CS Dep
        // May want to have parameter for day as well as timeslot
        return dates[day][timeslot];
    }

    @Override
    // student is "greater than" another student if they graduate later.
    public int compareTo(Student other) {
        if(this.gradY - other.gradY == 0) {
            return assignQValue(this.gradQ) - assignQValue(other.gradQ);
        }else{
            return this.gradY - other.gradY;
        }
    }

    private int assignQValue(String gradQ) {
        switch(gradQ) {
            case "Winter":
                return 1;
            case "Spring":
                return 2;
            case "Summer":
                return 3;
            case "Fall":
                return 4;
        }

    }
}