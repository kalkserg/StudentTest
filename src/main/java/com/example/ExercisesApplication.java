package com.example;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.example.localization.LocalizedResource;
import com.example.service.implementation.ReceiveExercisesListServiceImpl;
import com.example.view.ExercisesView;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;
import java.util.ResourceBundle;

public class ExercisesApplication {

    public static void main(String[] args) {

        //Logger show only error
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.toLevel("error"));

        //create context
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example");

        //set locale
        Locale currentLocale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("Localization", currentLocale);

        //get List of exercises from CSV file
        String filename = LocalizedResource.getLocalizedResource(currentLocale, "Question", "csv");
        ReceiveExercisesListServiceImpl receiveExercisesListServiceImpl = (ReceiveExercisesListServiceImpl) context.getBean("receiveExercisesListServiceImpl");
        receiveExercisesListServiceImpl.setCsvFileName(filename);

        //start
        ExercisesView exercisesView = (ExercisesView) context.getBean("exercisesView");
        exercisesView.start(rb);

    }
}
