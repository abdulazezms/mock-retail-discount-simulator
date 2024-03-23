package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.controller.response.bill.BillResponse;
import com.myretailbusiness.discountservice.domain.*;
import com.myretailbusiness.discountservice.repository.VolumeDiscountRepository;
import com.myretailbusiness.discountservice.service.role.RoleService;
import com.myretailbusiness.discountservice.utils.JwtUtils;
import com.myretailbusiness.discountservice.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VolumeDiscountServiceImpl implements VolumeDiscountService{
    private final VolumeDiscountRepository repository;
    private final RoleService roleService;

    @Autowired
    public VolumeDiscountServiceImpl(VolumeDiscountRepository repository,
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

        //    Conditions to be eligible for the volume discount:
        //      1- The user's role must be in the list of rolesApplicable
        //      2- The user's registration into the platform must've been
        //          at least discount.minimumTenureInYears ago.
        //      3- The discount must be active
        //      4- Sum of bill items' prices (which are not in the list of excluded categories)
        //          must be >= volumeDiscount.priceThreshold


        Role userRole = roleService.findByName(JwtUtils.getUserRoleClaim());
        int yearsSinceUserRegistration = TimeUtils.getYearsSinceEpoch(JwtUtils.getUserCreatedAtClaim());
        List<VolumeDiscount> applicableDiscounts = repository
                .findApplicableDiscounts(List.of(userRole), yearsSinceUserRegistration);

        BillResponse optimalDiscount = BillResponse.getNoDiscountFromBill(bill);
        for(VolumeDiscount candidate : applicableDiscounts) {
            BillResponse candidateDiscount = calculateDiscount(bill, candidate);
            if(optimalDiscount.compareTo(candidateDiscount) > 0.0) {
                optimalDiscount = candidateDiscount;
            }
        }
        return optimalDiscount;
    }

    private double getSumExcludingItemsInNotApplicableCategories(Bill bill, VolumeDiscount volumeDiscount) {
        double sum = 0.0;
        for(BillItem billItem : bill.getBillItemList()) {
            Product product = billItem.getProduct();
            if(!volumeDiscount.isCategoryNotApplicable(product.getCategory())) {
                sum += (double) billItem.getQuantity() * product.getPrice();
            }
        }
        return sum;
    }

    @Override
    public BillResponse calculateDiscount(Bill bill, VolumeDiscount volumeDiscount) {
        double sumExcludingItemsInNotApplicableCategories =
                getSumExcludingItemsInNotApplicableCategories(bill, volumeDiscount);

        // Calculate how many times price threshold fits into sumExcludingItemsInNotApplicableCategories
        double divisionResult = sumExcludingItemsInNotApplicableCategories / volumeDiscount.getPriceThreshold();

        // Check if the divisionResult is too large to fit into an int
        if (divisionResult > Integer.MAX_VALUE) {
            log.warn("The result of division is too large and exceeds the range of int.");
            throw new RuntimeException("division is too large");
        } else if(divisionResult == 0) {
            return BillResponse.getNoDiscountFromBill(bill);
        }
        else {
            // Safely cast the result to an int, knowing it won't overflow
            double discountAmount = divisionResult * volumeDiscount.getDiscountAmount();
            double discountRate = discountAmount / bill.getTotalBeforeDiscount();

            return BillResponse
                    .builder()
                    .discountAmount(discountAmount)
                    .discountType(DiscountType.VOLUME_DISCOUNT.name())
                    .discountRate(discountRate)
                    .totalBeforeDiscount(bill.getTotalBeforeDiscount())
                    .totalAfterDiscount(bill.getTotalBeforeDiscount() - discountAmount)
                    .discountDescription(volumeDiscount.getDescription())
                    .build();
        }
    }
}
