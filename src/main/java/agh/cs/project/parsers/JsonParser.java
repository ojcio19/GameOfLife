package agh.cs.project.parsers;
import org.json.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class JsonParser {
    private HashMap<String,Double> stats;
    private JSONObject data = new JSONObject();


    public void addDayToStats(){

        int day = stats.get("Day").intValue();
        stats.put("Number", stats.remove("Day"));

        JSONArray jsonStats = new JSONArray();
        jsonStats.put(stats);
        data.put("Day " + day,jsonStats);
    }

    public void writeJson() {


        //Write JSON file
        try (FileWriter file = new FileWriter("stats.json")) {
            file.write(String.valueOf(data));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStats(HashMap<String, Double> stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        if (stats != null)
            return "Day:\t" + stats.get("Number").intValue() + "\n" +
                    "Animals:\t" + stats.get("Animals").intValue() + "\n" +
                    "Grasses:\t" + stats.get("Grasses").intValue() + "\n" +
                    "Gene:\t" + stats.get("Gene").intValue() + "\n" +
                    "Life length:\t" + stats.get("LifeLength") + "\n" +
                    "Children:\t" + stats.get("Children") + "\n" +
                    "Energy:\t" + stats.get("Energy")  + "\n";
        else return "";
    }
}
