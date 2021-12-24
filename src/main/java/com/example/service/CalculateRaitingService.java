package com.example.service;

import com.example.model.Exercise;
import com.example.model.ExercisesList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

@PropertySource("classpath:raiting.properties")
@Service
public class CalculateRaitingService {

    @Value("${raiting.score}")
    private int RAITING;

    public void calculate(ExercisesList exercisesList, ResourceBundle rb) {
        int counter = 0;
        for (Exercise e : exercisesList.getExerciseList()) {
            if (e.getMark()) counter += e.getWeight();
        }
        if (counter >= RAITING) System.out.println(rb.getString("pass"));
        else System.out.println(rb.getString("not_pass"));
        System.out.println(rb.getString("score") + counter + " " + rb.getString("from") + RAITING);
    }
}
