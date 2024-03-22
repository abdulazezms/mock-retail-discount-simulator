package com.myretailbusiness.discountservice.service.discount;

import com.myretailbusiness.discountservice.controller.response.bill.BillDiscountResponse;
import com.myretailbusiness.discountservice.domain.Bill;
import com.myretailbusiness.discountservice.repository.VolumeDiscountRepository;
import com.myretailbusiness.discountservice.service.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VolumeDiscountService implements DiscountStrategy{
    private final VolumeDiscountRepository repository;
    private final RoleService roleService;

    @Autowired
    public VolumeDiscountService(VolumeDiscountRepository repository,
                                 RoleService roleService) {
        this.repository = repository;
        this.roleService = roleService;
    }

    /**
     * @param bill The bill of the user
     * @return optimal discount for the user on this bill, if applicable.
     */
    @Override
    public BillDiscountResponse getOptimalDiscountInfo(Bill bill) {
        return null;
    }
}
