package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.domain.*;
import com.myretailbusiness.discountservice.repository.PercentageDiscountRepository;
import com.myretailbusiness.discountservice.service.role.RoleService;
import com.myretailbusiness.discountservice.utils.JwtUtils;
import com.myretailbusiness.discountservice.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PercentageDiscountServiceImpl implements PercentageDiscountService{
    private final PercentageDiscountRepository repository;
    private final RoleService roleService;

    @Autowired
    public PercentageDiscountServiceImpl(PercentageDiscountRepository repository,
                                         RoleService roleService) {
        this.repository = repository;
        this.roleService = roleService;
    }

    /**
     * @param bill The bill of the user
     * @return optimal discount for the user on this bill, if applicable.
     */
    @Override
    public BillResponse getOptimalDiscountInfo(Bill bill) {

        //    Conditions to be eligible for the percentage discount:
        //      1- The user's role must be in the list of rolesApplicable
        //      2- The user's registration into the platform must've been
        //          at least discount.minimumTenureInYears ago.
        //      3- The discount must be active

        Role userRole = roleService.findByName(JwtUtils.getUserRoleClaim());
        int yearsSinceUserRegistration = TimeUtils.getYearsSinceEpoch(JwtUtils.getUserCreatedAtClaim());
        log.info("User has been registered since " + yearsSinceUserRegistration + " ago");
        List<PercentageDiscount> applicableDiscounts = repository
                .findApplicableDiscounts(List.of(userRole), yearsSinceUserRegistration);

        BillResponse optimalDiscount = BillResponse.getNoDiscountFromBill(bill);
        for(PercentageDiscount candidate : applicableDiscounts) {
            BillResponse candidateDiscount = calculateDiscount(bill, candidate);
            if(optimalDiscount.compareTo(candidateDiscount) > 0.0) {
                optimalDiscount = candidateDiscount;
            }
        }
        return optimalDiscount;
    }

    @Override
    public BillResponse calculateDiscount(Bill bill, PercentageDiscount percentageDiscount) {
        BillResponse discount = BillResponse.getNoDiscountFromBill(bill);

        boolean discountApplied = false;
        double discountAmount = 0.0;

        for(BillItem item : bill.getBillItemList()) {
            Category itemCategory = item.getProduct().getCategory();
            if(percentageDiscount.isCategoryNotApplicable(itemCategory))
                log.info("Item of type: " + itemCategory.getName() +
                        " not applicable to percentage discount: " + percentageDiscount.getDescription());
            else {
                log.info("Item of type: " + itemCategory.getName() +
                        " is applicable to percentage discount: " + percentageDiscount.getDescription());
                discountApplied = true;
                discountAmount += percentageDiscount.getDiscountRate() * item.getQuantity() * item.getProduct().getPrice();
            }
        }

        discount.setTotalBeforeDiscount(bill.getTotalBeforeDiscount());
        discount.setTotalAfterDiscount(bill.getTotalBeforeDiscount() - discountAmount);
        discount.setDiscountRate(discountAmount / bill.getTotalBeforeDiscount());
        discount.setDiscountType(discountApplied? DiscountType.PERCENTAGE_DISCOUNT.name():DiscountType.NONE.name());
        discount.setDiscountAmount(discountAmount);
        discount.setDiscountDescription(percentageDiscount.getDescription());
        return discount;
    }
}
