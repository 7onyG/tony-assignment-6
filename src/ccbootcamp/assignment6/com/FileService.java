package ccbootcamp.assignment6.com;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileService {
    public static void main(String[] args) throws IOException {
        generateReport("model3.csv", "Model 3");
        generateReport("modelS.csv", "Model S");
        generateReport("modelX.csv", "Model X");
    }

    public static void generateReport(String fileName, String modelName) {
        try (Stream<String> lines = new BufferedReader(new FileReader(fileName)).lines()) {
            Map<String, Double> totalSalesByYear = new HashMap<>();
            Map<String, Map<String, Double>> monthlySalesByYear = new HashMap<>();

            lines.skip(1)
                 .map(line -> line.split(",")) // Use tab as the delimiter
                 .forEach(fields -> {
                     String date = fields[0];
                     String[] parts = date.split("-");
                     String year = "20" + parts[0]; // Extract year from date
                     String month = parts[1]; // Extract month from date
                     double salesAmount = Double.parseDouble(fields[1]);

                     totalSalesByYear.merge(year, salesAmount, Double::sum);

                     monthlySalesByYear.putIfAbsent(year, new HashMap<>());
                     monthlySalesByYear.get(year).merge(month, salesAmount, Double::sum);
                 });

            System.out.println(modelName + " Yearly Sales Report");
            System.out.println("---------------------------");

            totalSalesByYear.forEach((year, totalSales) -> {
                System.out.println(year + " -> $" + totalSales);
            });

            String bestMonth = findBestMonth(monthlySalesByYear);
            String worstMonth = findWorstMonth(monthlySalesByYear);

            System.out.println("The best month for " + modelName + " was: " + bestMonth);
            System.out.println("The worst month for " + modelName + " was: " + worstMonth);
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String findBestMonth(Map<String, Map<String, Double>> monthlySalesByYear) {
    	  String bestMonth = "";
    	    double maxSales = Double.MIN_VALUE;

    	    for (Map.Entry<String, Map<String, Double>> entry : monthlySalesByYear.entrySet()) {
    	        Map<String, Double> monthlySales = entry.getValue();
    	        for (Map.Entry<String, Double> monthSales : monthlySales.entrySet()) {
    	            if (monthSales.getValue() > maxSales) {
    	                maxSales = monthSales.getValue();
    	                bestMonth = entry.getKey() + "-" + monthSales.getKey();
    	            }
    	        }
    	    }

    	    return bestMonth;
    }

    public static String findWorstMonth(Map<String, Map<String, Double>> monthlySalesByYear) {
    	String worstMonth = "";
        double minSales = Double.MAX_VALUE;

        for (Map.Entry<String, Map<String, Double>> entry : monthlySalesByYear.entrySet()) {
            Map<String, Double> monthlySales = entry.getValue();
            for (Map.Entry<String, Double> monthSales : monthlySales.entrySet()) {
                if (monthSales.getValue() < minSales) {
                    minSales = monthSales.getValue();
                    worstMonth = entry.getKey() + "-" + monthSales.getKey();
                }
            }
        }

        return worstMonth;
    }
}

