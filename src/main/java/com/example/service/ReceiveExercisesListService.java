package com.example.service;

import com.example.model.ExercisesList;

public interface ReceiveExercisesListService {

    void setCsvFileName(String csvFileName);

    ExercisesList getList();

}
