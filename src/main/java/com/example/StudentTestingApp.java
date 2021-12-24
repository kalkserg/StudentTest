package com.example;


import ch.qos.logback.classic.Level;
import com.example.service.CsvToBeanService;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StudentTestingApp {

    public static void main(String[] args) {

        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.toLevel("error"));

        //create context
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example");

        //set filename
        CsvToBeanService csvToBeanService = (CsvToBeanService) context.getBean("csvToBeanService");
        csvToBeanService.setFile("classpath:Question.csv");

        //run
        ExercisesView exercisesView = (ExercisesView) context.getBean("exercisesView");
        exercisesView.run();

    }

}
