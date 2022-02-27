public class Student {
    // fields
    private String firstN;
    private String lastN;
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
    private int assignedClass;
    

    // constructor
    public Student (String firstN, String lastN, int id, String gradQ, int gradY, 
                    int ta, boolean eb, boolean [][] dates, boolean [] taken) {
        this.firstN = firstN;
        this.lastN = lastN;
        this.id = id;
        this.gradQ = gradQ;
        this.gradY = gradY;
        this.taType = ta;
        this.eburg = eb;
        this.taken = taken;
        this.dates = dates;
        this.isAssigned = false;
        this.assignedClass = -1;
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

    public boolean getIsAssigned() {
        return this.isAssigned;
    }

    public void setIsAssigned(boolean input) {
        this.isAssigned = input;
    }

    public int getAssignedClass() {
        return this.assignedClass;
    }

    public void setAssignedClass(int input) {
        this.assignedClass = input;
    }

    public String getFirstN() {
        return firstN;
    }

    public String getLastN() {
        return lastN;
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

    //@Override
    // returns the student who graduates sooner
    public Student compareTo(Student other) {
        if(this.gradY > other.gradY) {
            return this;
        }
        if(this.gradY == other.gradY) {
            if (assignQValue(this.gradQ) < assignQValue(other.gradQ)) {
                return this;
            }
        }
        return other;
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
        return 0;

    }
}