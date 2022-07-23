package commissionCalculator.model.commission;

import commissionCalculator.model.commission.dto.CommissionDto;
import commissionCalculator.model.fee.Fee;
import commissionCalculator.model.fee.FeeRepository;
import commissionCalculator.model.transaction.Transaction;
import commissionCalculator.model.transaction.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

class CommissionServiceTest {

    @Autowired
    private CommissionService underTest;

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private FeeRepository feeRepository;
    @Mock
    private CommissionRequestRepository commissionRequestRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CommissionService(transactionRepository, feeRepository, commissionRequestRepository);
    }

    @Test
    void itShouldCalculateCommissionForOneUser() {
        //GIVEN
        List<String> customersId = List.of("1");
        given(transactionRepository.findAll()).willReturn(createTransactionsListForManyCustomers());
        given(transactionRepository.findAllByCustomerIdIsIn(customersId)).willReturn(createTransactionsListForOneCustomer());
        given(feeRepository.findAll()).willReturn(createFeeList());
        when(commissionRequestRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        //WHEN
        List<CommissionDto> commissions = underTest.calculateCommissionForUsers(customersId);
        //THEN
        assertThat(commissions.size()).isEqualTo(customersId.size());
        assertThat(commissions.get(0).getFee()).isEqualTo(2);
        assertThat(commissions.get(0).getCommissionValue()).isEqualTo(80);
    }

    @Test
    void itShouldCalculateCommissionForTwoUsers() {
        //GIVEN
        List<String> customersId = List.of("1", "2");
        given(transactionRepository.findAll()).willReturn(createTransactionsListForManyCustomers());
        given(transactionRepository.findAllByCustomerIdIsIn(customersId)).willReturn(createTransactionsListForManyCustomers());
        given(feeRepository.findAll()).willReturn(createFeeList());
        when(commissionRequestRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        //WHEN
        List<CommissionDto> commissions = underTest.calculateCommissionForUsers(customersId);
        //THEN
        assertThat(commissions.size()).isEqualTo(customersId.size());
        assertThat(commissions.get(0).getFee()).isEqualTo(1);
        assertThat(commissions.get(1).getFee()).isEqualTo(2);
        assertThat(commissions.get(0).getCommissionValue()).isEqualTo(50);
        assertThat(commissions.get(1).getCommissionValue()).isEqualTo(40);

    }

    @Test
    void itShouldCalculateCommissionForTwoUsersCornerCases() {
        //GIVEN
        List<String> customersId = List.of("1", "2");
        given(transactionRepository.findAll()).willReturn(createTransactionsListForManyCustomers());
        given(transactionRepository.findAllByCustomerIdIsIn(customersId)).willReturn(createTransactionsListForManyCustomersCornerTransactionValues());
        given(feeRepository.findAll()).willReturn(createFeeList());
        when(commissionRequestRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        //WHEN
        List<CommissionDto> commissions = underTest.calculateCommissionForUsers(customersId);
        //THEN
        assertThat(commissions.size()).isEqualTo(customersId.size());
        assertThat(commissions.get(0).getFee()).isEqualTo(0);
        assertThat(commissions.get(1).getFee()).isEqualTo(10.00);
        assertThat(commissions.get(0).getCommissionValue()).isEqualTo(0);
        assertThat(commissions.get(1).getCommissionValue()).isEqualTo(0);
    }

    @Test
    void itShouldCalculateCommissionForAllUsersWhenGivenIdNotExist() {
        //GIVEN
        List<Transaction> emptyList = new ArrayList<>();
        List<String> customersId = List.of("30");
        given(transactionRepository.findAll()).willReturn(createTransactionsListForManyCustomers());
        given(transactionRepository.findAllByCustomerIdIsIn(customersId)).willReturn(emptyList);
        given(feeRepository.findAll()).willReturn(createFeeList());
        when(commissionRequestRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        //WHEN
        List<CommissionDto> commissions = underTest.calculateCommissionForUsers(customersId);
        //THEN
        assertThat(commissions.size()).isEqualTo(2);
        assertThat(commissions.get(0).getFee()).isEqualTo(1);
        assertThat(commissions.get(1).getFee()).isEqualTo(2);
        assertThat(commissions.get(0).getCommissionValue()).isEqualTo(50);
        assertThat(commissions.get(1).getCommissionValue()).isEqualTo(40);
    }

    @Test
    void itShouldCalculateCommissionForAllUsers() {
        //GIVEN
        List<Transaction> emptyList = new ArrayList<>();
        List<String> customersId = List.of("all");
        given(transactionRepository.findAll()).willReturn(createTransactionsListForManyCustomers());
        given(transactionRepository.findAllByCustomerIdIsIn(customersId)).willReturn(emptyList);
        given(feeRepository.findAll()).willReturn(createFeeList());
        when(commissionRequestRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        //WHEN
        List<CommissionDto> commissions = underTest.calculateCommissionForUsers(customersId);
        //THEN
        assertThat(commissions.size()).isEqualTo(2);
        assertThat(commissions.get(0).getFee()).isEqualTo(1);
        assertThat(commissions.get(1).getFee()).isEqualTo(2);
        assertThat(commissions.get(0).getCommissionValue()).isEqualTo(50);
        assertThat(commissions.get(1).getCommissionValue()).isEqualTo(40);
    }

    static List<Transaction> createTransactionsListForManyCustomers() {

        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = new Transaction("1",
                BigDecimal.valueOf(1000),
                "John",
                "1",
                "Doe",
                LocalDateTime.now()
        );

        Transaction transaction2 = new Transaction(
                "2",
                BigDecimal.valueOf(4000),
                "John",
                "1",
                "Doe",
                LocalDateTime.now()
        );

        Transaction transaction3 = new Transaction(
                "3",
                BigDecimal.valueOf(2000),
                "Ann",
                "2",
                "Smith",
                LocalDateTime.now()
        );

        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);

        return transactions;
    }

    static List<Transaction> createTransactionsListForManyCustomersCornerTransactionValues() {

        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = new Transaction("1",
                BigDecimal.valueOf(9000),
                "John",
                "1",
                "Doe",
                LocalDateTime.now()
        );

        Transaction transaction2 = new Transaction(
                "2",
                BigDecimal.valueOf(4000),
                "John",
                "1",
                "Doe",
                LocalDateTime.now()
        );

        Transaction transaction3 = new Transaction(
                "3",
                BigDecimal.valueOf(0),
                "Ann",
                "2",
                "Smith",
                LocalDateTime.now()
        );

        transactions.add(transaction1);
        transactions.add(transaction2);
        transactions.add(transaction3);

        return transactions;
    }

    static List<Transaction> createTransactionsListForOneCustomer() {

        List<Transaction> transactions = new ArrayList<>();

        Transaction transaction1 = new Transaction("1",
                BigDecimal.valueOf(1000),
                "John",
                "1",
                "Doe",
                LocalDateTime.now()
        );

        Transaction transaction2 = new Transaction(
                "2",
                BigDecimal.valueOf(3000),
                "John",
                "1",
                "Doe",
                LocalDateTime.now()
        );

        transactions.add(transaction1);
        transactions.add(transaction2);

        return transactions;
    }

    private static List<Fee> createFeeList() {
        List<Fee> fees = new ArrayList<>();

        Fee fee1 = new Fee();
        fee1.setFeeId("1");
        fee1.setPercentage(2.00);
        fee1.setTransactionValue(4500.00);

        Fee fee2 = new Fee();
        fee2.setFeeId("2");
        fee2.setPercentage(1.00);
        fee2.setTransactionValue(6000.00);

        fees.add(fee1);
        fees.add(fee2);

        return fees;
    }
}