package commissionCalculator.dbDataLoader;

import commissionCalculator.model.fee.Fee;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FeePercentageDataLoaderTest {

    @Test
    void itShouldReadDataFromCsvFile() throws IOException {
        //GIVEN
        int rows = 4;
        //WHEN
        List<Fee> fees = FeePercentageDataLoader.readCsv();
        //THEN
        assertThat(fees.size()).isEqualTo(rows);
    }
}