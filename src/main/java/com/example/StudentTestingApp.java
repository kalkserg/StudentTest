package com.example;

import ch.qos.logback.classic.Level;
import com.example.service.CsvToBeanService;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StudentTestingApp {

    public static void main(String[] args) {
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.toLevel("error"));

        //set locale
//        Locale currentlocale = new Locale("");
        Locale currentlocale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("Localization", currentlocale);

        String filename = getLocalizedResource(currentlocale, "Question", "csv");

//        try (InputStream inputStream = StudentTestingApp.class.getResourceAsStream("/Question_ru.csv");
//             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//            String contents = reader.lines()
//                    .collect(Collectors.joining(System.lineSeparator()));
//            System.out.println(contents);
//        }catch (Exception ex){
//            System.out.println("AAAAAAAAAAAAAAAA");
//        }
//
//        try (InputStream inputStream = StudentTestingApp.class.getResourceAsStream("/Question_ru.csv");
//             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//            String contents = reader.lines()
//                    .collect(Collectors.joining(System.lineSeparator()));
//            System.out.println(contents);
//        }catch (Exception ex){
//            System.out.println("AAAAAAAAAAAAAAAA");
//        }

        //create context
        ApplicationContext context = new AnnotationConfigApplicationContext("com.example");

        //set file
        CsvToBeanService csvToBeanService = (CsvToBeanService) context.getBean("csvToBeanService");
//        csvToBeanService.setFile("tion_ru.csv");
        csvToBeanService.setFile(filename);

        //run
        ExercisesView exercisesView = (ExercisesView) context.getBean("exercisesView");
        exercisesView.run(rb);
    }

    private static String getLocalizedResource(Locale locale, String baseName, String suffix) {
        ResourceBundle.Control control = ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT);
        List<Locale> candidateLocales = control.getCandidateLocales(baseName, locale);
        for (Locale specificLocale : candidateLocales) {
            String bundleName = control.toBundleName(baseName, specificLocale);
            String resourceName = control.toResourceName(bundleName, suffix);
            URL url = StudentTestingApp.class.getClassLoader().getResource(resourceName);
            if (url != null) {
                File file = new File(url.getFile());
                return file.getName();
            }
        }
        return null;
    }
}
