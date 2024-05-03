import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class VremenskaReformat {

    public static void main(String[] args) {


        try (BufferedReader br = new BufferedReader(new FileReader(new File("H:\\Diplomski\\2.semestar\\Seminar\\Spojeni_Podatci\\Data\\SEDrava1_VrijemePrognoza.csv")));
             FileOutputStream fos = new FileOutputStream("H:\\Diplomski\\2.semestar\\Seminar\\Spojeni_Podatci\\Data\\SEDrava1_VrijemePrognoza_Filtered.csv", true);

        ) {

            String header = "tof, vt, barometer, outtemp, windspeed, winddir, rain, radiation, cloud_cover\n";
            fos.write(header.getBytes(StandardCharsets.UTF_8));



            String line = br.readLine();


            while ((line = br.readLine()) != null) {
                String[] components = line.split("\",\"");


                String tof = components[0].substring(1);
                String vt = components[1];
                String barometer = components[2].replace(',', '.');
                String outtemp = components[3].replace(',', '.');
                String windspeed = components[4].replace(',', '.');
                String winddir = components[5];
                String rain = components[6].replace(',', '.');
                String radiation = components[7];

                String cloud_cover = components[8].replace(',', '.');
                cloud_cover = cloud_cover.substring(0, cloud_cover.length() - 1);


               String newLine = tof
                       + ','
                       + vt
                       + ','
                       + barometer
                       + ','
                       + outtemp
                       + ','
                       + windspeed
                       + ','
                       + winddir
                       + ','
                       + rain
                       + ','
                       + radiation
                       + ','
                       +cloud_cover + '\n';

               fos.write(newLine.getBytes(StandardCharsets.UTF_8));

//               for(int i = 0; i<  components.length; i++){
//                   newLine.append(components[i]);
//                   if(i != components.length - 1) newLine.append("  ");
//               }
//                System.out.println(newLine);

            }



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
