package commissionCalculator;

import commissionCalculator.dbDataLoader.FeePercentageDataLoader;
import commissionCalculator.dbDataLoader.TransactionDataLoader;
import commissionCalculator.model.fee.Fee;
import commissionCalculator.model.fee.FeeRepository;
import commissionCalculator.model.transaction.Transaction;
import commissionCalculator.model.transaction.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CommissionCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommissionCalculatorApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            TransactionRepository transactionRepository,
            FeeRepository feeRepository
    ){
        return args -> {
            List<Transaction> transactions = TransactionDataLoader.readCsv();
            transactionRepository.deleteAll();
            transactionRepository.saveAll(transactions);

            List<Fee> fees = FeePercentageDataLoader.readCsv();
            feeRepository.deleteAll();
            feeRepository.saveAll(fees);
        };
    }
}
