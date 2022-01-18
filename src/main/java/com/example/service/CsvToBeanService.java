package com.example.service;

import com.example.StudentTestingApp;
import com.example.model.Exercise;
import com.example.model.ExercisesList;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvToBeanService {

    private String fileName;

    public ExercisesList getList() {
        // Hashmap to map CSV data to Bean attributes.
        Map<String, String> mapping = new HashMap<String, String>();
        mapping.put("Id", "id");
        mapping.put("Question", "question");
        mapping.put("Answer", "answer");
        mapping.put("Weight", "weight");

        // HeaderColumnNameTranslateMappingStrategy
        // for  class
        HeaderColumnNameTranslateMappingStrategy<Exercise> strategy = new HeaderColumnNameTranslateMappingStrategy<Exercise>();
        strategy.setType(Exercise.class);
        strategy.setColumnMapping(mapping);

        // Create csvreader object
        CSVReader csvReader = null;
        try {
            fileName = "/" + fileName;
            InputStream inputStream = StudentTestingApp.class.getResourceAsStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

//            File file = ResourceUtils.getFile(fileName);
//            Reader reader = Files.newBufferedReader(file.toPath());

            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(';')
                    .withIgnoreQuotations(true)
                    .build();
            csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build();
        } catch (Exception e) {
            System.out.println("File not found " + fileName);
            System.exit(1);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.exit(1);
        }
        CsvToBean<Exercise> csvToBean = new CsvToBean<>();
        csvToBean.setCsvReader(csvReader);
        csvToBean.setMappingStrategy(strategy);
        List<Exercise> list = null;
        try {
            list = csvToBean.parse();
        } catch (Exception ex) {
            System.out.println("Wrong format file " + fileName);
            System.exit(1);
        }

        // print details of Bean object
//        for (Entity e : list) {
//            System.out.println(e.getQuestion().toString());
//            System.out.println(e.getAnswer().toString());
//        }
        return new ExercisesList(list);
    }

    public String getFile() {
        return fileName;
    }

    public void setFile(String fileName) {
        this.fileName = fileName;
    }

}
