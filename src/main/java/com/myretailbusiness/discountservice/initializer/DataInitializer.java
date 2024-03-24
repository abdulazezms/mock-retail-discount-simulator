package com.myretailbusiness.discountservice.initializer;

import com.myretailbusiness.discountservice.constants.AppProfiles;
import com.myretailbusiness.discountservice.domain.*;
import com.myretailbusiness.discountservice.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.myretailbusiness.discountservice.constants.AppRolesConstants.*;
import static com.myretailbusiness.discountservice.initializer.ProductCategoryEnum.GROCERY;
import static com.myretailbusiness.discountservice.initializer.ProductCategoryEnum.NON_GROCERY;
import static com.myretailbusiness.discountservice.initializer.ProductEnum.*;

@Slf4j
@Component
@Profile(AppProfiles.LOCAL_PROFILE)
class DataInitializer {
    private final RoleRepository roleRepository;

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final VolumeDiscountRepository volumeDiscountRepository;

    private final PercentageDiscountRepository percentageDiscountRepository;

    private final List<String> appRoles = List.of(AFFILIATE, CUSTOMER, EMPLOYEE);

    private final List<String> appCategories = List.of(GROCERY.name(), NON_GROCERY.name());

    private final Map<ProductEnum, ProductCategoryEnum> products = Map
            .of(APPLE, GROCERY,
                BANANA, GROCERY,
                LAPTOP, NON_GROCERY,
                SMARTPHONE, NON_GROCERY);


    @Autowired
    private DataInitializer(RoleRepository roleRepository,
                            CategoryRepository categoryRepository,
                            ProductRepository productRepository,
                            VolumeDiscountRepository volumeDiscountRepository,
                            PercentageDiscountRepository percentageDiscountRepository) {
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.volumeDiscountRepository = volumeDiscountRepository;
        this.percentageDiscountRepository = percentageDiscountRepository;
    }

    @PostConstruct
    private void initializeData() {
        //insert roles on db
        insertRolesOnDB();
        //insert categories on db
        insertCategoriesOnDB();
        //insert products on db
        insertProductsOnDB();
        //insert some discounts
        insertVolumeDiscounts();
        insertPercentageDiscounts();

        log.info("Data initialized completely");
    }


    private void insertRolesOnDB() {
        roleRepository.deleteAll();
        roleRepository.saveAll(appRoles.stream().map(Role::new).collect(Collectors.toList()));
    }

    private void insertCategoriesOnDB() {
        categoryRepository.deleteAll();
        categoryRepository.saveAll(appCategories.stream().map(Category::new).collect(Collectors.toList()));
    }

    private void insertProductsOnDB() {
        productRepository.deleteAll();
        SecureRandom random = new SecureRandom(); // Compliant for security-sensitive use cases
        int groceryPriceBound = 10;
        int nonGroceryPriceBound = 1000;
        for(ProductEnum key : products.keySet()) {
            String productName = key.name();
            String productCategory = products.get(key).name();
            double productPrice;
            if(productCategory.equals(GROCERY.name()))
                productPrice = random.nextInt(groceryPriceBound);
            else
                productPrice = random.nextInt(nonGroceryPriceBound);

            Product product = new Product();
            product.setName(productName);
            product.setPrice(productPrice);
            product.setCategory(categoryRepository.findByName(productCategory).get());
            productRepository.save(product);
        }
    }


    private void insertVolumeDiscounts() {
        volumeDiscountRepository.deleteAll();
        double discountAmount = 5.0, priceThreshold = 100.0, derivedRate = discountAmount/priceThreshold;
        VolumeDiscount volumeDiscount = new VolumeDiscount();
        volumeDiscount.setRolesApplicable(roleRepository.findAll());
        volumeDiscount.setCategoriesNotApplicable(null);
        volumeDiscount.setIsActive(true);
        volumeDiscount.setDescription("5 on every 100 discount on all categories and apply to all roles");
        volumeDiscount.setMinimumTenureInYears(0);
        volumeDiscount.setDiscountAmount(discountAmount);
        volumeDiscount.setPriceThreshold(priceThreshold);
        volumeDiscount.setDerivedRate(derivedRate);
        volumeDiscountRepository.save(volumeDiscount);
    }

    private void insertPercentageDiscounts() {
        percentageDiscountRepository.deleteAll();

        Role employee = roleRepository.findByName(EMPLOYEE).get();
        Role affiliate = roleRepository.findByName(AFFILIATE).get();
        Role customer = roleRepository.findByName(CUSTOMER).get();

        //employees percentage discount
        PercentageDiscount percentageDiscountForEmployees = new PercentageDiscount();
        percentageDiscountForEmployees.setRolesApplicable(List.of(employee));
        percentageDiscountForEmployees.setCategoriesNotApplicable(List.of(categoryRepository.findByName(GROCERY.name()).get()));
        percentageDiscountForEmployees.setIsActive(true);
        percentageDiscountForEmployees.setDescription("employees 30% discount, excluding groceries");
        percentageDiscountForEmployees.setDiscountRate(0.3);
        percentageDiscountForEmployees.setMinimumTenureInYears(0);


        //affiliates percentage discount
        PercentageDiscount percentageDiscountForAffiliates = new PercentageDiscount();
        percentageDiscountForAffiliates.setRolesApplicable(List.of(affiliate));
        percentageDiscountForAffiliates.setCategoriesNotApplicable(List.of(categoryRepository.findByName(GROCERY.name()).get()));
        percentageDiscountForAffiliates.setIsActive(true);
        percentageDiscountForAffiliates.setDescription("affiliates 10% discount, excluding groceries");
        percentageDiscountForAffiliates.setDiscountRate(0.1);
        percentageDiscountForAffiliates.setMinimumTenureInYears(0);


        //2-years customer percentage discount
        PercentageDiscount percentageDiscountFor2YearsCustomer = new PercentageDiscount();
        percentageDiscountFor2YearsCustomer.setRolesApplicable(List.of(customer));
        percentageDiscountFor2YearsCustomer.setCategoriesNotApplicable(List.of(categoryRepository.findByName(GROCERY.name()).get()));
        percentageDiscountFor2YearsCustomer.setIsActive(true);
        percentageDiscountFor2YearsCustomer.setDescription("2-years customer 5% discount, excluding groceries");
        percentageDiscountFor2YearsCustomer.setDiscountRate(0.05);
        percentageDiscountFor2YearsCustomer.setMinimumTenureInYears(2);

        percentageDiscountRepository
                .saveAll(List.of(percentageDiscountForEmployees,
                        percentageDiscountForAffiliates,
                        percentageDiscountFor2YearsCustomer));
    }
}

