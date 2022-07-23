package commissionCalculator.model.commission;

import commissionCalculator.model.commission.dto.CommissionDto;
import commissionCalculator.model.fee.Fee;
import commissionCalculator.model.fee.FeeRepository;
import commissionCalculator.model.transaction.Transaction;
import commissionCalculator.model.transaction.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommissionService {

    private final TransactionRepository transactionRepository;
    private final FeeRepository feeRepository;
    private final CommissionRequestRepository commissionRequestRepository;

    public List<CommissionDto> calculateCommissionForUsers(List<String> customerIds) {

        List<CommissionDto> commissionsDto = new ArrayList<>();
        List<Transaction> transactions;

        if (customerIds.contains("all")) {
            transactions = transactionRepository.findAll();
        } else {
            transactions = transactionRepository.findAllByCustomerIdIsIn(customerIds);
            if (transactions.isEmpty()) {
                transactions = transactionRepository.findAll();
            }
        }

        Map<String, List<Transaction>> transactionsByCustomerId = transactions
                .stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId));

        transactionsByCustomerId
                .forEach(
                        (customerId, customerTransactions) -> {

                            String customerFirstName = getCustomerFirstName(customerTransactions);
                            String customerLastName = getCustomerLastName(customerTransactions);
                            double totalTransactionsValue = getTotalTransactionsValue(customerTransactions);
                            int numberOfTransactions = customerTransactions.size();
                            LocalDateTime latestTransactionDate = getLatestTransactionDate(customerTransactions);
                            double fee = getFeeByTransactionsValue(totalTransactionsValue);
                            double calculatedCommission = totalTransactionsValue * fee / 100;

                            commissionsDto.add(new CommissionDto(
                                    customerId,
                                    customerFirstName,
                                    customerLastName,
                                    numberOfTransactions,
                                    totalTransactionsValue,
                                    fee,
                                    calculatedCommission,
                                    latestTransactionDate
                            ));

                            saveCommissionRequest(customerId, calculatedCommission);
                        }
                );
        return commissionsDto;
    }

    private void saveCommissionRequest(String customerId, double calculatedCommission) {
        CommissionRequest commissionRequest = new CommissionRequest();
        commissionRequest.setCustomerId(customerId);
        commissionRequest.setCalculatedCommission(calculatedCommission);
        commissionRequest.setRequestDate(LocalDateTime.now());
        commissionRequestRepository.save(commissionRequest);
    }

    private LocalDateTime getLatestTransactionDate(List<Transaction> customerTransactions) {
        return customerTransactions
                .stream()
                .map(Transaction::getTransactionDate)
                .max(Comparator.naturalOrder())
                .get();
    }

    private double getTotalTransactionsValue(List<Transaction> customerTransactions) {
        return customerTransactions
                .stream()
                .map(Transaction::getTransactionAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private String getCustomerLastName(List<Transaction> customerTransactions) {
        return customerTransactions
                .stream()
                .map(Transaction::getCustomerLastName)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private String getCustomerFirstName(List<Transaction> customerTransactions) {
        return customerTransactions
                .stream()
                .map(Transaction::getCustomerFirstName)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private double getFeeByTransactionsValue(Double transactionValue) {

        if (transactionValue >= 10000.00) {
            return 0;
        } else if (transactionValue == 0.00 || transactionValue.isNaN()) {
            return 10.00;
        } else {
            return feeRepository.findAll()
                    .stream()
                    .filter(fee -> fee.getTransactionValue() >= transactionValue)
                    .map(Fee::getPercentage)
                    .max(Comparator.naturalOrder())
                    .orElse(10.00);
        }
    }
}
