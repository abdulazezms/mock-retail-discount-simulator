package com.myretailbusiness.discountservice.config.db;

import com.myretailbusiness.discountservice.constants.AppProfiles;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoAuditing
@EnableMongoRepositories(basePackages = {"com.myretailbusiness.discountservice.repository"})
@Profile("!" + AppProfiles.UNIT_TEST_PROFILE)
@Configuration
public class MongoDBConfig {
}
