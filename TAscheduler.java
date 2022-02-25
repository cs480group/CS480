import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;  
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.file.Paths;
import java.nio.file.Path; 

public class TAscheduler  {  
    public static int numClasses = 21;
    public static void main(String[] args) {
        List<Schedule> schedules = readScheduleFromCSV("schedule.csv");
        List<Student> students = readStudentFromCSV("students.csv");
        for(int i = 0; i < numClasses; i++){
            System.out.println(students.get(0).hasTaken(i)); //for debugging
        }

        int[] possibleTas =  new int[numClasses];
        for(int i = 0; i < numClasses; i++){
            possibleTas[i] = numPossible(i);
        }

        Arrays.sort(possibleTas);

        //Get a list of students that can TA each class starting with the class with the least amount of students avail.
        //Compare students to eachother to decide who to assign to the class
        //go the the next class, rinse and repeate for each class

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

    private static Schedule createScheduleObj(String[] data) {
        int cat = Integer.parseInt(data[1]);
        String sec = data[2];
        String days = data[6].replaceAll(" ", ""); //maybe not the final solution to this problem?
        String sTime = data[7];
        String eTime = data[8];

        return new Schedule(cat, sec, days, sTime, eTime);
    }

    private static Student createStudentObj(String[] data) {
        int id = Integer.parseInt(data[2]);
        String gradQ = data[4];
        int gradY = Integer.parseInt(data[5]);
        int taType = Integer.parseInt(data[6]);
        boolean eburg = false;
        
        if (data[7] == "Yes")
        {
            eburg = true;
        }

        boolean [][] dates = new boolean[4][8];
        int index = 8;
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
        boolean [] taken = new boolean[numClasses];

        for (int i = 0; i < taken.length; i++)
        {
            taken[i] = false;
            if (data[index].equals("X"))
            {
                taken[i] = true;
            }
            index++;
        }

        return new Student(id, gradQ, gradY, taType, eburg, dates, taken);
    
    }

    private static int numPossible(int classNum) {
        //Takes in a class number (0-20) and returns how many students can TA that class
    }

    private static List<Student> possibleStudents(int classNum) {
        //Takes in a class number (0-20) and returns a list of students who can TA the class
    }



}

