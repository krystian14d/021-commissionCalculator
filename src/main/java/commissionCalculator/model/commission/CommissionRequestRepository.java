package commissionCalculator.model.commission;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommissionRequestRepository extends MongoRepository<CommissionRequest, String> {
}
