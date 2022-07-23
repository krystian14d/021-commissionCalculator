package commissionCalculator.model.transaction;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {

    List<Transaction> findAllByCustomerId(List<String> customerIds);
    List<Transaction> findAllByCustomerIdIsIn(List<String> customerIds);
}
