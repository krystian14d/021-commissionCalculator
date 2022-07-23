package commissionCalculator.dbDataLoader;


import commissionCalculator.model.fee.Fee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeePercentageDataLoader {

    private static String FILE_NAME = "fee_wages.csv";

    public static List<Fee> readCsv() throws IOException {

        FileReader fileReader = new FileReader(FILE_NAME);
        BufferedReader br = new BufferedReader(fileReader);

        List<Fee> fees = new ArrayList<>();

        //skip header in the CSV file:
        String line = br.readLine();

        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");

            String transactionValueString = fields[0];
            String percentage = fields[1] + "." + fields[2];

            Fee fee = new Fee();
            fee.setTransactionValue(Double.parseDouble(transactionValueString));
            fee.setPercentage(Double.parseDouble(percentage.substring(1, percentage.length() - 1)));

            fees.add(fee);
        }

        return fees;
    }
}

