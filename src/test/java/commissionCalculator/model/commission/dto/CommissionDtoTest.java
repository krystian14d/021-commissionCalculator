package commissionCalculator.model.commission.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommissionDtoTest {

    private CommissionDto underTest;

    @Test
    void itShouldCreateCommissionDto() {
        //GIVEN
        String customerId = "1";
        String customerFirstName = "John";
        String customerLastName = "Doe";
        int numberOfTransactions = 5;
        double totalTransactionsValue = 3000.00;
        double fee = 2.00;
        double commissionValue = 123.45;
        LocalDateTime latestTransactionDate = LocalDateTime.now();

        //WHEN
        underTest = new CommissionDto(
                customerId,
                customerFirstName,
                customerLastName,
                numberOfTransactions,
                totalTransactionsValue,
                fee,
                commissionValue,
                latestTransactionDate
                );

        //THEN
        assertThat(underTest.getCustomerId()).isEqualTo(customerId);
        assertThat(underTest.getCustomerFirstName()).isEqualTo(customerFirstName);
        assertThat(underTest.getCustomerLastName()).isEqualTo(customerLastName);
        assertThat(underTest.getNumberOfTransactions()).isEqualTo(numberOfTransactions);
        assertThat(underTest.getCommissionValue()).isEqualTo(commissionValue);
        assertThat(underTest.getLatestTransactionDate()).isEqualTo(latestTransactionDate);

    }
}