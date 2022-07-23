package commissionCalculator.model.commission;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CommissionRequestTest {

    private CommissionRequest underTest;

    @Test
    void itShouldCommissionRequest() {
        //GIVEN
        String id = "1";
        String customerId = "2";
        double calculatedCommission = 123.45;
        LocalDateTime requestDate = LocalDateTime.now();

        //WHEN
        underTest = new CommissionRequest();
        underTest.setId(id);
        underTest.setCustomerId(customerId);
        underTest.setCalculatedCommission(calculatedCommission);
        underTest.setRequestDate(requestDate);

        //THEN
        assertThat(underTest.getId()).isEqualTo(id);
        assertThat(underTest.getCustomerId()).isEqualTo(customerId);
        assertThat(underTest.getCalculatedCommission()).isEqualTo(calculatedCommission);
        assertThat(underTest.getRequestDate()).isEqualTo(requestDate);
    }
}