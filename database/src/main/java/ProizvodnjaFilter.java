import javax.xml.crypto.Data;
import java.io.*;
import java.nio.DoubleBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProizvodnjaFilter {




    public static void main(String[] args) {
        Map<String, String> proizvodnja = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File("H:\\Diplomski\\2.semestar\\Seminar\\Spojeni_Podatci\\Data\\SEDrava1.csv")));
             FileOutputStream fos = new FileOutputStream("H:\\Diplomski\\2.semestar\\Seminar\\Spojeni_Podatci\\Data\\SEDrava1_Filtered.csv", true);

        ) {

            String header = "power_timestamp, qyt\n";
            fos.write(header.getBytes(StandardCharsets.UTF_8));



            String line = br.readLine();


            while ((line = br.readLine()) != null) {
               String[] components = line.split(",");

               String timestamp = components[1] + " " + components[2];


               String newLine = timestamp + ',' + components[5] + "\n";


               proizvodnja.put(timestamp, newLine);

            }


            for(String timestamp : proizvodnja.keySet()){
                fos.write(proizvodnja.get(timestamp).getBytes(StandardCharsets.UTF_8));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
