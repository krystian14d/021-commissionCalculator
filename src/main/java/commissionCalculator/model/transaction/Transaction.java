package commissionCalculator.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document(collection = "transactions")
public class Transaction {

    @Id
    private String transactionId;
    private BigDecimal transactionAmount;
    private String customerFirstName;
    private String customerId;
    private String customerLastName;
    private LocalDateTime transactionDate;
}
