package com.example;

import com.example.model.Exercise;
import com.example.model.ExercisesList;
import com.example.service.CalculateRaitingService;
import com.example.service.CsvToBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class ExercisesView {

    @Autowired
    CsvToBeanService csvToBeanService;

    @Autowired
    CalculateRaitingService calculateRaitingService;


    public void run() {
        Scanner myScanner = new Scanner(System.in);

        System.out.print("Enter Firstname: ");
        String firstName = myScanner.nextLine();
        System.out.print("Enter Lastname: ");
        String lastName = myScanner.nextLine();

        ExercisesList exercisesList = csvToBeanService.getList();
//        exerciseList = csvToBeanService.getExerciseList();
//        csvToBeanService.getList();

        System.out.println(exercisesList.size() + " questions for " + firstName.toUpperCase() + " " + lastName.toUpperCase() + ": ");
        int count = 0;
        for (Exercise e : exercisesList.getExerciseList()) {
            System.out.println("Question " + (++count) + ": " + e.getQuestion());
            System.out.print("Answer " + count + ": ");
            String answer = myScanner.nextLine();
            if (answer.equals(e.getAnswer())) {
                System.out.println("   Right!"); e.setMark(true);
            } else {
                System.out.println("   Wrong!"); e.setMark(false);
            };
        }
        System.out.println("Correct answers " + exercisesList.getAllScore(true) + " (" + String.format("%.2f",exercisesList.getAllScore(true)*100./count) + "%)");

        calculateRaitingService.calculate(exercisesList);
    }

}
