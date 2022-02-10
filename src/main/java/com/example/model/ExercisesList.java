package com.example.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExercisesList {

    List<Exercise> exerciseList;

    public ExercisesList(List<Exercise> list) {
        this.exerciseList = list;
    }

    public int getAllScore(boolean status) {
        return (int) exerciseList.stream().filter(e -> e.isPass() == status).count();
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public int size() {
        return exerciseList.size();
    }
}
