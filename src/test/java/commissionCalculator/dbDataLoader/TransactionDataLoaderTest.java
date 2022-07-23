package commissionCalculator.dbDataLoader;

import commissionCalculator.model.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionDataLoaderTest {

    @Test
    void itShouldLoadCsvData() throws IOException {
        //GIVEN
        int rows = 52;
        //WHEN
        List<Transaction> transactions = TransactionDataLoader.readCsv();
        //THEN
        assertThat(transactions.size()).isEqualTo(rows);

    }
}