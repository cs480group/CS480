import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;  
import java.util.ArrayList;
import java.nio.file.Paths;
import java.nio.file.Path; 

public class TAscheduler  {  
    public static void main(String[] args) {
        List<Schedule> schedules = readScheduleFromCSV("schedule.csv");
        List<Student> students = readStudentFromCSV("students.csv");
        System.out.println(schedules.get(0).getCategory());
        System.out.println(students.get(0).getId());
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
                String[] data = line.split(",");

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
                //deliminates the data using ","
                //System.out.println(line); for debugging
                //split is giving different lengths of arrays when we beleive it shouldn't
                //need to fogure out how to split the data consistently for every line in the file
                String[] data = line.split(",");

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

        //currently not the final solution to this problem
        String days = data[6].replaceAll(" ", "");

        String sTime = data[7];
        String eTime = data[8];

        return new Schedule(cat, sec, days, sTime, eTime);
    }

    private static Student createStudentObj(String[] data) {
        int id = Integer.parseInt(data[2]);
        String gradQ = data[4];
        int gradY = Integer.parseInt(data[5]);
        int taType = Integer.parseInt(data[6]);
        Boolean eburg = false;
        if (data[7] == "Yes")
        {
            eburg = true;
        }

        Boolean [][] dates = new Boolean[4][8];
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
        Boolean [] taken = new Boolean[20];

        // System.out.println(data.length); for debugging
        // for (int i = 0; i < taken.length; i++)
        // {
        //     if (data[index] == "X")
        //     {
        //         taken[i] = true;
        //     }
        //     index++;
        // }

        return new Student(id, gradQ, gradY, taType, eburg, dates, taken);

    
    }
}

