package com.example.service.implementation;

import com.example.model.Exercise;
import com.example.model.ExercisesList;
import com.example.service.CalculateScoreService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

@PropertySource("classpath:raiting.properties")
@Service
public class CalculateScoreServiceImpl implements CalculateScoreService {

    @Value("${raiting.score}")
    private int RAITING;

    @Override
    public void calculate(ExercisesList exercisesList, ResourceBundle rb) {
        int counter = 0;
        for (Exercise e : exercisesList.getExerciseList()) {
            if (e.isPass()) counter += e.getScore();
        }
        if (counter >= RAITING) System.out.println(rb.getString("pass"));
        else System.out.println(rb.getString("not_pass"));
        System.out.println(rb.getString("score") + counter + " " + rb.getString("from") + RAITING);
    }
}
