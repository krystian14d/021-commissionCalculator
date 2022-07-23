package commissionCalculator.model.fee;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FeeTest {
    private Fee underTest;

    @Test
    void itShouldCreateFee() {
        //GIVEN
        String feeId = "1";
        Double transactionValue = 1000.00;
        double percentage = 5.00;

        //WHEN
        underTest = new Fee();
        underTest.setFeeId(feeId);
        underTest.setTransactionValue(transactionValue);
        underTest.setPercentage(percentage);

        //THEN
        assertThat(underTest.getFeeId()).isEqualTo(feeId);
        assertThat(underTest.getPercentage()).isEqualTo(percentage);
        assertThat(underTest.getTransactionValue()).isEqualTo(transactionValue);

    }
}