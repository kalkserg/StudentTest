package com.example.view;

import com.example.model.Exercise;
import com.example.model.ExercisesList;
import com.example.service.CalculateScoreService;
import com.example.service.ReceiveExercisesListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

@Component
public class ExercisesView {

    @Autowired
    ReceiveExercisesListService receiveExercisesListService;

    @Autowired
    CalculateScoreService calculateScoreService;


    public void start(ResourceBundle rb) {

        ExercisesList exercisesList = receiveExercisesListService.getList();
        Scanner myScanner = new Scanner(System.in, Charset.defaultCharset().name());
        System.out.println(rb.getString("welcome"));
        System.out.print(rb.getString("firstname"));
        String firstName = myScanner.nextLine();
        System.out.print(rb.getString("lastname"));
        String lastName = myScanner.nextLine();

        System.out.println(exercisesList.size() + " " + rb.getString("questions_for") + firstName.toUpperCase() + " " + lastName.toUpperCase() + ": ");
        int count = 0;
        for (Exercise e : exercisesList.getExerciseList()) {
            System.out.println(rb.getString("question") + (++count) + ": " + e.getQuestion() + " (" + e.getScore() + " " + rb.getString("weight") + ")");
            System.out.print(rb.getString("answer") + count + ": ");
            String answer = myScanner.nextLine();
            if (answer.trim().toLowerCase().equals(e.getAnswer().trim().toLowerCase(Locale.ROOT))) {
                System.out.println("\t" + rb.getString("right"));
                e.setPass(true);
            } else {
                System.out.println("\t" + rb.getString("wrong"));
                e.setPass(false);
            }
        }
        System.out.println(rb.getString("correct_answer") + exercisesList.getAllScore(true) + " (" + String.format("%.2f", exercisesList.getAllScore(true) * 100. / count) + "%)");

        calculateScoreService.calculate(exercisesList, rb);
    }
}
