package commissionCalculator.dbDataLoader;


import commissionCalculator.model.transaction.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionDataLoader {

    private static String FILE_NAME = "transactions.csv";

    public static List<Transaction> readCsv() throws IOException {

        FileReader fileReader = new FileReader(FILE_NAME);
        BufferedReader br = new BufferedReader(fileReader);

        List<Transaction> transactions = new ArrayList<>();

        //skip header in the CSV file:
        String line = br.readLine();

        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");

            String transactionId = fields[0];
            String amount = fields[1] + "." + fields[2];
            String customerFirstName = fields[3];
            String customerId = fields[4];
            String customerLastName = fields[5];
            String transactionDateString = fields[6];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime transactionDate = LocalDateTime.parse(transactionDateString, formatter);

            Transaction transaction = new Transaction(
                    transactionId,
                    new BigDecimal(amount.substring(1, amount.length() - 1)),
                    customerFirstName,
                    customerId,
                    customerLastName,
                    transactionDate
            );

            transactions.add(transaction);
        }

        return transactions;
    }
}
