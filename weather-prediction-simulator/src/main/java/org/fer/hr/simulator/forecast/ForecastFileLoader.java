package org.fer.hr.simulator.forecast;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.experimental.UtilityClass;
import org.fer.hr.simulator.data.ForecastPojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@UtilityClass
class ForecastFileLoader {

    static List<ForecastPojo> load(String forecastFilePath, String forecastFileName) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(forecastFilePath, forecastFileName))) {

            CSVReader csvReader = new CSVReader(bufferedReader);

            return csvReader.readAll().stream()
                    .map(ForecastFileLoader::mapCsvLineToPojo)
                    .toList();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private static ForecastPojo mapCsvLineToPojo(String[] line) {
        return new ForecastPojo(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7], line[8]);
    }
}
