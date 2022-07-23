package commissionCalculator.model.commission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommissionDto {

    private String customerId;
    private String customerFirstName;
    private String customerLastName;
    private int numberOfTransactions;
    double totalTransactionsValue;
    private double fee;
    private double commissionValue;
    private LocalDateTime latestTransactionDate;
}
