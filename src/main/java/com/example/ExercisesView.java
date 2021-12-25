package com.example;

import com.example.model.Exercise;
import com.example.model.ExercisesList;
import com.example.service.CalculateRaitingService;
import com.example.service.CsvToBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;
import java.util.Scanner;

@Service
public class ExercisesView {

    @Autowired
    CsvToBeanService csvToBeanService;

    @Autowired
    CalculateRaitingService calculateRaitingService;

    public void run(ResourceBundle rb) {

        ExercisesList exercisesList = csvToBeanService.getList();

        System.out.println(rb.getString("welcome"));

        Scanner myScanner = new Scanner(System.in);

        System.out.print(rb.getString("firstname"));
        String firstName = myScanner.nextLine();
        System.out.print(rb.getString("lastname"));
        String lastName = myScanner.nextLine();

        System.out.println(exercisesList.size() + " " + rb.getString("questions_for") + firstName.toUpperCase() + " " + lastName.toUpperCase() + ": ");
        int count = 0;
        for (Exercise e : exercisesList.getExerciseList()) {
            System.out.println(rb.getString("question") + (++count) + ": " + e.getQuestion() + " (" + e.getWeight() + " " + rb.getString("weight") + ")");
            System.out.print(rb.getString("answer") + count + ": ");
            String answer = myScanner.nextLine();
            if (answer.equals(e.getAnswer())) {
                System.out.println("\t" + rb.getString("right"));
                e.setMark(true);
            } else {
                System.out.println("\t" + rb.getString("wrong"));
                e.setMark(false);
            }
        }
        System.out.println(rb.getString("correct_answer") + exercisesList.getAllScore(true) + " (" + String.format("%.2f", exercisesList.getAllScore(true) * 100. / count) + "%)");

        calculateRaitingService.calculate(exercisesList, rb);
    }
}
