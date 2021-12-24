package com.example.service;

import com.example.model.Exercise;
import com.example.model.ExercisesList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:raiting.properties")
@Service
public class CalculateRaitingService {

    @Value("${raiting.score}")
    private int RAITING;

    public void calculate(ExercisesList exercisesList) {
        int counter = 0;
        for (Exercise e : exercisesList.getExerciseList()) {
            if (e.getMark()) counter += e.getWeight();
        }
        if (counter >= RAITING) System.out.println("PASS");
        else System.out.println("NOT PASS");
        System.out.println("Score " + counter + " from " + RAITING);
    }
}
