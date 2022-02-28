import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;  
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.nio.file.Paths;
import java.nio.file.Path; 

public class TAscheduler  {  
    public static int numClassesNoSec = 21;
    private static List<Student> allStudents;
    private static List<Student> remainingStudents;
    private static List<Schedule> classes;
    private static boolean firstRoundDone = false;
    private static boolean secondRoundDone = false;
    
    public static void main(String[] args) {
        /*  classes = list of all Schedule objects from a file
            allStudents = list of all Student objects from a file
            remainingStudents = list of Student objects that represents students who have not been assigned a TA position
            currCandidates = the current list of Students who are being considered to assign to a class during assignment rounds
            allPossibleTas = an array to keep track of how many TAs are avail to teach each class section */
        classes = readScheduleFromCSV("schedule.csv");
        allStudents = readStudentFromCSV("students.csv");
        remainingStudents = readStudentFromCSV("students.csv");
        List<Student> currCandidates;
        int[] allPossibleTas =  new int[classes.size()];

        // // UNIT TEST to see if the dates 2d array is properly filled in a Schedule object
        // int testIndex = 1;
        // boolean[][] dates = classes.get(testIndex).getDates();
        // if(dates != null){
        //     System.out.println("CS " + classes.get(testIndex).getCategory() + " Section " + classes.get(testIndex).getSection() + " dates array info:");
        //     for(int i = 0; i < dates.length; i++){
        //         for(int j = 0; j < dates[i].length; j++){
        //             if(dates[i][j] == true) {
        //                 System.out.println("Day " + i + " @TimeIndex: " + j + " There is class");
        //             }else{
        //                 System.out.println("no class");
        //             }
        //         }
        //     }
        // }else{
        //     System.out.println("CS " + classes.get(testIndex).getCategory() + " Section " + classes.get(testIndex).getSection() + " Has no class times given");
        // } 
        
        // // UNIT TEST to see if the dates 2d array is properly filled in a Student object
        // int testIndex = 0;
        // System.out.println("Looking at student: " + allStudents.get(testIndex).getName());
        // boolean[][] dates = allStudents.get(testIndex).getDates();
        // for(int i = 0; i < dates.length; i++){
        //     for(int j = 0; j < dates[i].length; j++){
        //         if(dates[i][j] == true) {
        //             System.out.println("Day " + i + " @TimeIndex: " + j + " Student is Available");
        //         }else{
        //             System.out.println("not avail");
        //         }
        //     }
        // }

        // // UNIT TEST to check that hasTaken works and that the student objects are uniform
        // for(int i = 0; i < numClassesNoSec; i++){
        //     System.out.println(allStudents.get(0).hasTaken(i)); 
        // }

        // // UNIT TEST to check that possibleStudents works 
        // List<Student> qualified = possibleStudents(0);
        // for(int i = 0; i < qualified.size(); i++){
        //     System.out.println(qualified.get(i).getId()); 
        // }

        // // UNIT TEST to check that numPossible works
        // int[] possibleTas =  new int[classes.size()];
        // for(int i = 0; i < classes.size(); i++){
        //     possibleTas[i] = numPossible(i);
        //     System.out.println(possibleTas[i]); 
        // }
        
        //Get a list of students that can TA each class starting with the class with the least amount of students avail.
        //Compare students to eachother to decide who to assign to the class, go the the next class, rinse and repeate for each class
        
        // Part 1 UNIT TEST to help track of the number of students before and after assignment rounds
        System.out.println("Number of allStudents: " + allStudents.size());
        System.out.println("Number of remainingStudents: " + remainingStudents.size());
        System.out.println();

        //first round
        while(!firstRoundDone) {
            for(int i = 0; i < classes.size(); i++) {
                allPossibleTas[i] = numPossible(classes.get(i));
            }

            //gets the class index that has the lowest number of possible TA's
            Schedule currClass = classSelect(allPossibleTas, 1);
            
            //if there are no more classes to assign a first TA to, break
            if(currClass == null) {
                break;
            }

            //gets list of students for the current class, and finds the "best" student to assign
            currCandidates = possibleStudents(currClass);
            Student bestOption = currCandidates.get(0);
            if(currCandidates.size() != 1){
                for(int i = 1; i < currCandidates.size(); i++){
                    bestOption = bestOption.compareTo(currCandidates.get(i));
                }
            }

            assignStudent(bestOption, currClass, 1);
            //allPossibleTas[currClass] = Integer.MAX_VALUE; DOESNT MATTER ANYMORE??
        }

        // Useful for Debugging round 1
        System.out.println("First round done.");
        System.out.println("Number of allStudents: " + allStudents.size());
        System.out.println("Number of remainingStudents: " + remainingStudents.size());
        System.out.println();

        //second round
        while(!secondRoundDone) {
            for(int i = 0; i < classes.size(); i++) {
                allPossibleTas[i] = numPossible(classes.get(i));
            }

            //gets the class index that has the lowest number of possible TA's
            Schedule currClass = classSelect(allPossibleTas, 2);
            
            //if there are no more classes to assign a first TA to, break
            if(currClass == null) {
                break;
            }

            //gets list of students for the current class, and finds the "best" student to assign
            currCandidates = possibleStudents(currClass);
            Student bestOption = currCandidates.get(0);
            if(currCandidates.size() != 1){
                for(int i = 1; i < currCandidates.size(); i++){
                    bestOption = bestOption.compareTo(currCandidates.get(i));
                }
            }

            assignStudent(bestOption, currClass, 2);
            //allPossibleTas[currClass] = Integer.MAX_VALUE; DOESNT MATTER ANYMORE??
        }
        // Useful for debugging round 2
        System.out.println("Second round done.");
        System.out.println("Number of allStudents: " + allStudents.size());
        System.out.println("Number of remainingStudents: " + remainingStudents.size());
        System.out.println("Number of total classes with unique sections: " + classes.size());
        System.out.println();

        // // UNIT TEST to see if the student objects store info about which class the student has been assigned to
        // System.out.println("Assigned Students");
        // for(int i = 0; i < allStudents.size(); i++) {
        //     if(allStudents.get(i).getIsAssigned() == true) {
        //         System.out.println("Student ID: " + allStudents.get(i).getId() + "  Student Name: " + allStudents.get(i).getName());
        //         System.out.println("    Assigned to: CS" + allStudents.get(i).getAssignedClass().getCategory());
        //         System.out.println("    Has taken it? " + allStudents.get(i).hasTaken(allStudents.get(i).getAssignedClass()));
        //     }
        // }

        // // UNIT TEST to see if the class ojects store info about which students have been assigned to the class
        // System.out.println("Classes and their TA's");
        // for(int i = 0; i < classes.size(); i++) {
        //     System.out.println("CS: " + classes.get(i).getCategory() + "Section: " + classes.get(i).getSection() + " has the following TA's");
        //     System.out.println("        - " + classes.get(i).getTANames()[0]);
        //     System.out.println("        - " + classes.get(i).getTANames()[1]);
        // }

    }

    private static List<Schedule> readScheduleFromCSV(String fileName) {
        List<Schedule> classes = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            //reads first line, then skips over it
            String line = br.readLine();
            line = br.readLine();

            while (line != null) {
                //deliminates the data using ","
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                //creates a schedule object form the data using helper method
                Schedule schedule = createScheduleObj(data, classes.size());

                //adds the schedule object to the array list
                classes.add(schedule);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return classes;
    }

    //identical to readScheduleFromCSV but for reading student info
    private static List<Student> readStudentFromCSV(String fileName) {
        List<Student> students = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)){
            //reads first line, then skips over it
            String line = br.readLine();
            line = br.readLine();
            line = br.readLine();

            while (line != null) {
                //System.out.println(line) //for debugging

                //deliminates the data using ","
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); //for debugging

                //creates a schedule object form the data using helper method
                Student student = createStudentObj(data);

                //adds the schedule object to the array list
                students.add(student);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return students;
    }

    //creates the schedule objects given the data from a CSV file line
    private static Schedule createScheduleObj(String[] data, int classIndex) {
        int cat = Integer.parseInt(data[1]);
        String sec = data[2];
        String days = data[5].replaceAll(" ", "");
        String sTime = data[6];
        //String eTime = data[7]; //IMPORTANT if classes can be longer than an hr?????
        boolean [][] dates = new boolean[4][8];
        boolean [] dayIndicator = getClassDays(days);

        //populates the 2d dates array
		if(days.isEmpty()){
			dates = null;
		}else {
            //System.out.println("Assigning dates for CS " + cat + " " + sec + ". sTime substring: " + sTime.substring(0,2)); //FOR DEBUGGING
			switch(sTime.substring(0,2)) {
                case "8:": assignDates(dates, dayIndicator, 0); break;
                case "9:": assignDates(dates, dayIndicator, 1); break;
                case "10": assignDates(dates, dayIndicator, 2); break;
                case "11": assignDates(dates, dayIndicator, 3); break;
                case "12": assignDates(dates, dayIndicator, 4); break;
                case "1:": assignDates(dates, dayIndicator, 5); break;
                case "2:": assignDates(dates, dayIndicator, 6); break;
                case "3:": assignDates(dates, dayIndicator, 7); break;
            }
		}
        return new Schedule(cat, sec, dates, classIndex);
    }

    //creates the student objects given the data from a CSV file line
    private static Student createStudentObj(String[] data) {
        String firstN = data[0];
        String lastN = data[1];
        int id = Integer.parseInt(data[2]);
        String gradQ = data[4];
        int gradY = Integer.parseInt(data[5]);
        int taType = Integer.parseInt(data[6]);
        boolean eburg = false;
        boolean [] taken = new boolean[numClassesNoSec];
        boolean [][] dates = new boolean[4][8];
        int index = 8;
        
        if (data[7] == "Yes") {
            eburg = true;
        }

        for (int i = 0; i < dates.length; i++) {
            for (int k = 0; k < dates[i].length; k++) {
                if (data[index].equals("Open")) {
                    dates[i][k] = true;
                }
                index++;
            }
        }

        index = 42;
        for (int i = 0; i < taken.length; i++) {
            taken[i] = false;
            if (data[index].equals("X")) {
                taken[i] = true;
            }
            index++;
        }
        return new Student(firstN, lastN, id, gradQ, gradY, taType, eburg, dates, taken);
    }

    //Takes in a class object and returns how many students can TA that class
    private static int numPossible(Schedule givenClass) {
    	int num = 0;
    	for(int i = 0; i < remainingStudents.size(); i++) {
    		if(remainingStudents.get(i).hasTaken(givenClass) && remainingStudents.get(i).isFree(givenClass.getDates())) {
    			num++;
    		}
    	}
		return num;
    }
    
    //Takes in a class object and returns a list of students who can TA the class
    private static List<Student> possibleStudents(Schedule givenClass) {
    	List<Student> qualified = new ArrayList<>();
    	for(int i = 0; i < remainingStudents.size(); i++) {
    		if(remainingStudents.get(i).hasTaken(givenClass) && remainingStudents.get(i).isFree(givenClass.getDates())) {
    			qualified.add(remainingStudents.get(i));
    		}
    	}
		return qualified;
    }

    //returns the Schedule object for the class that has the lowest amount of TA's avail and hasnt been assigned in the given round
    private static Schedule classSelect(int[] possibleTAs, int roundNum) {
        int index = 0;
        int lowestValue = Integer.MAX_VALUE;
        for(int i = 0; i < possibleTAs.length; i++) {
            if(roundNum == 1) {
                if(possibleTAs[i] < lowestValue && classes.get(i).getFirstTA() == false && possibleTAs[i] != 0) {
                    lowestValue = possibleTAs[i];
                    index = i;
                }
            }else {
                if(possibleTAs[i] < lowestValue && classes.get(i).getSecondTA() == false && possibleTAs[i] != 0) {
                    lowestValue = possibleTAs[i];
                    index = i;
                }
            }
        }
        if(lowestValue == Integer.MAX_VALUE) {
            return null;
        }
        return classes.get(index);
    }

    private static void assignStudent(Student student, Schedule currClass, int roundNum) {
        if(roundNum == 1){
            currClass.setFirstTA(true);
            currClass.setTAName(student.getName(), 1);
        }else {
            currClass.setSecondTA(true);
            currClass.setTAName(student.getName(), 2);
        }
        int studentID = student.getId();
        for(int i = 0; i < allStudents.size(); i++) {
            if(studentID == allStudents.get(i).getId()) {
                allStudents.get(i).setAssignedClass(currClass);
                allStudents.get(i).setIsAssigned(true);
            }
        }
        int removableIndex = 0;
        for(int i = 0; i < remainingStudents.size(); i++) {
            if(student.getId() == remainingStudents.get(i).getId()) {
                removableIndex = i;
            }
        }
        remainingStudents.remove(removableIndex);
    }

    private static boolean[] getClassDays(String days) {
        boolean [] results = new boolean[4];
        //System.out.println("Days given to getClassDays switch: " + days); //FOR DEBBUGGING
        switch(days) {
            case("M"): results[0] = true; break;
            case("MT"): results[0] = true; results[1] = true; break;
            case("MW"): results[0] = true; results[2] = true; break;
            case("MTH"): results[0] = true; results[3] = true; break;
            case("MTW"): results[0] = true; results[1] = true; results[2] = true; break;
            case("MTTH"): results[0] = true; results[1] = true; results[3] = true; break;
            case("MWTH"): results[0] = true; results[2] = true; results[3] = true; break;
            case("MTWTH"): results[0] = true; results[1] = true; results[2] = true; results[3] = true; break;
            case("T"): results[1] = true; break;
            case("TW"): results[1] = true; results[2] = true; break;
            case("TTH"): results[1] = true; results[3] = true; break;
            case("TWTH"): results[1] = true; results[2] = true; results[3] = true; break;
            case("W"): results[2] = true; break;
            case("WTH"): results[2] = true; results[3] = true; break;
            case("TH"): results[3] = true; break;
        }
        return results;
    }

    //helper function to fill in the 2d dates array for Schedule objects
    private static void assignDates(boolean[][] result, boolean[] dayIndicator, int timeSlot) {
        for(int i = 0; i < dayIndicator.length; i++) {
            if (dayIndicator[i] == true) {
                result[i][timeSlot] = true;
                //System.out.println("DayIndex : " + i + " timeSlot: " + timeSlot);  //FOR DEBUGGING
            }
        }
    }
}