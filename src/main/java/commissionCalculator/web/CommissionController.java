package commissionCalculator.web;

import commissionCalculator.model.commission.CommissionService;
import commissionCalculator.model.commission.dto.CommissionDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommissionController {

    private final CommissionService commissionService;

    @GetMapping("/api")
    public List<CommissionDto> calculateCommission(@RequestParam(name = "customer_id") List<String> customerIds) {
        return commissionService.calculateCommissionForUsers(customerIds);
    }
}
