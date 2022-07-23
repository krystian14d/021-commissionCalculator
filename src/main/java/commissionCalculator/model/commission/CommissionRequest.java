package commissionCalculator.model.commission;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "commissionRequestsLog")
public class CommissionRequest {

    @Id
    private String id;
    private String customerId;
    private double calculatedCommission;
    private LocalDateTime requestDate;

}
