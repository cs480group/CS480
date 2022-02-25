public class Student {
    // fields
    private int id;
    private String gradQ;
    private int gradY;
    private int taType;
    private boolean eburg;
    // 4x8 array representing day and timeslots  
    private boolean [][] dates; 
    //array to hold info for what a student has taken.
    //true if the student has taken the class
    private boolean [] taken;
    private boolean isAssigned; 
    

    // constructor
    public Student (int id, String gradQ, int gradY, int ta, boolean eb, boolean [][] dates, boolean [] taken) {
        this.id = id;
        this.gradQ = gradQ;
        this.gradY = gradY;
        this.taType = ta;
        this.eburg = eb;
        this.taken = taken;
        this.dates = dates;
        this.isAssigned = false;
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

    public boolean inEburg() {
        return this.eburg;
    }

    // return true if a student has takened the given class
    public boolean hasTaken (int cla) {
        return taken[cla];
    }

    // return truee if student does not have a time conflict
    public boolean isFree (int day, int timeslot) {
        // classes are usually offer at the same time in CS Dep
        // May want to have parameter for day as well as timeslot
        return dates[day][timeslot];
    }
}