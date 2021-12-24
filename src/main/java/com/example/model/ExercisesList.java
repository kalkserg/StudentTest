package com.example.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExercisesList {

    List<Exercise> exerciseList;

    public ExercisesList(List<Exercise> list) {
        this.exerciseList = list;
    }

    public int getAllScore(boolean status) {
        return (int) exerciseList.stream().filter(e -> e.getMark() == status).count();
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public int size(){
        return exerciseList.size();
    }

}
