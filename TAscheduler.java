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
    public static int numClasses = 21;
    private static List<Student> allStudents;
    private static List<Student> remainingStudents;
    private static List<Schedule> classes;
    private static final HashMap<Integer,Integer> classMap = new HashMap<Integer,Integer>();
    private static boolean firstRoundDone = false;
    private static boolean secondRoundDone = false;
    static {
    	classMap.put(0, 102);
    	classMap.put(1, 105);
    	classMap.put(2, 107);
    	classMap.put(3, 109);
    	classMap.put(4, 110);
    	classMap.put(5, 111);
    	classMap.put(6, 112);
    	classMap.put(7, 301);
    	classMap.put(8, 302);
    	classMap.put(9, 311);
    	classMap.put(10, 312);
    	classMap.put(11, 361);
    	classMap.put(12, 362);
    	classMap.put(13, 380);
    	classMap.put(14, 420);
    	classMap.put(15, 427);
    	classMap.put(16, 430);
    	classMap.put(17, 440);
    	classMap.put(18, 467);
    	classMap.put(19, 470);
    	classMap.put(20, 480);
    }
    
    public static void main(String[] args) {
        /*  classes = list of all Schedule objects from a file
            allStudents = list of all Student objects from a file
            remainingStudents = list of Student objects that represents students who have not been assigned a TA position */
        classes = readScheduleFromCSV("schedule.csv");
        allStudents = readStudentFromCSV("students.csv");
        remainingStudents = readStudentFromCSV("students.csv");
        
        // Useful for Debugging
        System.out.println("Number of allStudents: " + allStudents.size());
        System.out.println("Number of remainingStudents: " + remainingStudents.size());
        System.out.println();

        // // UNIT TEST to check that hasTaken works and that the student objects are uniform
        // for(int i = 0; i < numClasses; i++){
        //     System.out.println(allStudents.get(0).hasTaken(i)); 
        // }

        // // UNIT TEST to check that possibleStudents works 
        // List<Student> qualified = possibleStudents(0);
        // for(int i = 0; i < qualified.size(); i++){
        //     System.out.println(qualified.get(i).getId()); 
        // }

        // // UNIT TEST to check that numPossible works
        // int[] possibleTas =  new int[numClasses];
        // for(int i = 0; i < numClasses; i++){
        //     possibleTas[i] = numPossible(i);
        //     System.out.println(possibleTas[i]); 
        // }
        

        //Get a list of students that can TA each class starting with the class with the least amount of students avail.
        //Compare students to eachother to decide who to assign to the class, go the the next class, rinse and repeate for each class
        List<Student> currCandidates;
        int[] allPossibleTas =  new int[numClasses];
        
        //first round
        while(!firstRoundDone) {
            for(int i = 0; i < numClasses; i++) {
                allPossibleTas[i] = numPossible(i);
            }

            //gets the class index that has the lowest number of possible TA's
            int currClass = classSelect(allPossibleTas, 1);
            
            //if there are no more classes to assign a first TA to, break
            if(currClass == -1) {
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
            allPossibleTas[currClass] = Integer.MAX_VALUE;
        }

        // // UNIT TEST to see the students who have been assigned to a class after the first round
        // for(int i = 0; i < allStudents.size(); i++) {
        //     if(allStudents.get(i).getIsAssigned() == true) {
        //         System.out.println("Student ID: " + allStudents.get(i).getId() + "    Assigned to: " + classMap.get(allStudents.get(i).getAssignedClass()));
        //     }
        // }

        // Useful for Debugging round 1
        System.out.println("First round done.");
        System.out.println("Number of allStudents: " + allStudents.size());
        System.out.println("Number of remainingStudents: " + remainingStudents.size());
        System.out.println();

        //second round
        while(!secondRoundDone) {
            for(int i = 0; i < numClasses; i++) {
                allPossibleTas[i] = numPossible(i);
            }
            
            //gets the class index that has the lowest number of possible TA's
            int currClass = classSelect(allPossibleTas, 2);
            
            //if there are no more classes to assign a first TA to, break
            if(currClass == -1) {
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
            allPossibleTas[currClass] = Integer.MAX_VALUE;
        }

        // Useful for 2ebugging round 2
        System.out.println("Second round done.");
        System.out.println("Number of allStudents: " + allStudents.size());
        System.out.println("Number of remainingStudents: " + remainingStudents.size());
        System.out.println();

        // UNIT TEST to see the students who have been assigned to a class after both rounds
        System.out.println("Assigned Students");
        for(int i = 0; i < allStudents.size(); i++) {
            if(allStudents.get(i).getIsAssigned() == true) {
                System.out.println("Student ID: " + allStudents.get(i).getId() + 
                                    "    Student Name: " + allStudents.get(i).getLastN() + ", " + allStudents.get(i).getFirstN());
                System.out.println("    Assigned to: CS" + classMap.get(allStudents.get(i).getAssignedClass()));
            }
        }

    }

    private static List<Schedule> readScheduleFromCSV(String fileName) {
        List<Schedule> schedules = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)){
            //reads first line, then skips over it
            String line = br.readLine();
            line = br.readLine();

            while (line != null) {
                //deliminates the data using ","
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                //creates a schedule object form the data using helper method
                Schedule schedule = createScheduleObj(data);

                //adds the schedule object to the array list
                schedules.add(schedule);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return schedules;
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
    private static Schedule createScheduleObj(String[] data) {
        int cat = Integer.parseInt(data[1]);
        String sec = data[2];
        String days = data[6].replaceAll(" ", ""); //maybe not the final solution to this problem?
        String sTime = data[7];
        String eTime = data[8];

        return new Schedule(cat, sec, days, sTime, eTime);
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
        boolean [] taken = new boolean[numClasses];
        boolean [][] dates = new boolean[4][8];
        int index = 8;
        
        if (data[7] == "Yes")
        {
            eburg = true;
        }

        for (int i = 0; i < dates.length; i++) 
        {
            for (int k = 0; k < dates[i].length; k++) 
            {
                if (data[index] == "Open") 
                {
                    dates[i][k] = true;
                }
                index++;
            }
        }

        index = 42;
        for (int i = 0; i < taken.length; i++)
        {
            taken[i] = false;
            if (data[index].equals("X"))
            {
                taken[i] = true;
            }
            index++;
        }
        return new Student(firstN, lastN, id, gradQ, gradY, taType, eburg, dates, taken);
    }

    //Takes in a class number (0-20) and returns how many students can TA that class
    private static int numPossible(int classNum) {
    	int num = 0;
    	for(int i = 0; i < remainingStudents.size(); i++) {
    		if(remainingStudents.get(i).hasTaken(classNum)) {
    			num++;
    		}
    	}
		return num;
    }

    // //Takes in a class NUMBER and returns a list of students who can TA the class
    // private static List<Student> possibleStudents_classNum(int classNum) {
    // 	int Index = classMap.get(classNum);
    // 	List<Student> qualified = new ArrayList<>();
    // 	for(int i = 0; i < remainingStudents.size(); i++) {
    // 		if(remainingStudents.get(i).hasTaken(Index)) {
    // 			qualified.add(remainingStudents.get(i));
    // 		}
    // 	}
	// 	return qualified;
    // }
    
    //Takes in a class INDEX and returns a list of students who can TA the class
    private static List<Student> possibleStudents(int Index) {
    	List<Student> qualified = new ArrayList<>();
    	for(int i = 0; i < remainingStudents.size(); i++) {
    		if(remainingStudents.get(i).hasTaken(Index)) {
    			qualified.add(remainingStudents.get(i));
    		}
    	}
		return qualified;
    }

    //returns the class index for the class that has the lowest amount of TA's avail and hasnt been assigned in the given round
    private static int classSelect(int[] possibleTAs, int roundNum) {
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
            return -1;
        }
        return index;
    }

    private static void assignStudent(Student student, int currClass, int roundNum) {
        if(roundNum == 1){
            classes.get(currClass).setFirstTA(true);
        }else {
            classes.get(currClass).setSecondTA(true);
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
}