package commissionCalculator.model.transaction;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private Transaction underTest;

    @Test
    void itShouldCreateTransaction() {
        //GIVEN
        String transactionId = "1";
        BigDecimal transactionAmount = BigDecimal.valueOf(1000);
        String customerFirstName = "John";
        String customerId = "2";
        String customerLastName = "Smith";
        LocalDateTime transactionDate = LocalDateTime.now();

        //WHEN
        underTest = new Transaction(
                transactionId,
                transactionAmount,
                customerFirstName,
                customerId,
                customerLastName,
                transactionDate
        );

        //THEN
        assertThat(underTest.getTransactionId()).isEqualTo(transactionId);
        assertThat(underTest.getTransactionAmount()).isEqualTo(transactionAmount);
        assertThat(underTest.getCustomerFirstName()).isEqualTo(customerFirstName);
        assertThat(underTest.getCustomerId()).isEqualTo(customerId);
        assertThat(underTest.getCustomerLastName()).isEqualTo(customerLastName);
        assertThat(underTest.getTransactionDate()).isEqualTo(transactionDate);

    }
}