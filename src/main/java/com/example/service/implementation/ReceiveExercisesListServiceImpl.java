package com.example.service.implementation;

import com.example.ExercisesApplication;
import com.example.model.Exercise;
import com.example.model.ExercisesList;
import com.example.service.ReceiveExercisesListService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReceiveExercisesListServiceImpl implements ReceiveExercisesListService {

    private static String csvFileName;

    public static void setCsvFileName(String csvFileName) {
        ReceiveExercisesListServiceImpl.csvFileName = csvFileName;
    }

    @Override
    public ExercisesList getList() {

        // Hashmap to map CSV data to Bean attributes.
        Map<String, String> mapping = new HashMap<String, String>();
        mapping.put("Id", "id");
        mapping.put("Question", "question");
        mapping.put("Answer", "answer");
        mapping.put("Score", "Score");

        // HeaderColumnNameTranslateMappingStrategy
        HeaderColumnNameTranslateMappingStrategy<Exercise> strategy = new HeaderColumnNameTranslateMappingStrategy<Exercise>();
        strategy.setType(Exercise.class);
        strategy.setColumnMapping(mapping);

        // Create csv reader object
        CSVReader csvReader = null;
        try {
            csvFileName = "/" + csvFileName;
            InputStream inputStream = ExercisesApplication.class.getResourceAsStream(csvFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .withIgnoreQuotations(true)
                    .build();
            csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build();
        } catch (Exception e) {
            System.out.println("File not found " + csvFileName);
            System.exit(1);
        }
        CsvToBean<Exercise> csvToBean = new CsvToBean<>();
        csvToBean.setCsvReader(csvReader);
        csvToBean.setMappingStrategy(strategy);
        List<Exercise> list = null;
        try {
            list = csvToBean.parse();
        } catch (Exception ex) {
            System.out.println("Wrong format file " + csvFileName);
            System.exit(1);
        }
        return new ExercisesList(list);
    }

}
