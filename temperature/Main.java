package temperature;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    
    public static void processBatch(String filename) throws IOException {
        List<Double> temps = new ArrayList<>();
        int errors = 0;
        
        for (String line : Files.readAllLines(Paths.get(filename))) {
            line = line.trim();
            if (line.isEmpty()) continue;
            
            String[] parts = line.split(",");
            if (parts.length != 2 || parts[0].split(":").length != 3) {
                errors++;
                continue;
            }
            
            try {
                double temp = Double.parseDouble(parts[1].strip());
                if (temp >= -100 && temp <= 200) temps.add(temp);
                else errors++;
            } catch (NumberFormatException e) {
                errors++;
            }
        }
        
        if (temps.isEmpty()) {
            System.out.println("No valid temperature data found.");
            return;
        }
        
        double avg = temps.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        System.out.println("Total: " + temps.size() + " | Errors: " + errors);
        System.out.printf("Min: %.2f | Max: %.2f | Avg: %.2f%n", 
            Collections.min(temps), Collections.max(temps), avg);
    }
    
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage: java temperature.Main <filename>");
            return;
        }
        processBatch(args[0]);
    }
}