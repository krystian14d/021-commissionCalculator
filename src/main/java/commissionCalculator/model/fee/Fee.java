package commissionCalculator.model.fee;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "fees")
public class Fee {

    @Id
    private String feeId;
    private Double transactionValue;
    private double percentage;
}
