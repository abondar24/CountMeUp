package org.abondar.experimental.countmeup.test;


import org.abondar.experimental.countmeup.app.CountApplication;
import org.abondar.experimental.countmeup.configurations.DataBaseConfiguration;
import org.abondar.experimental.countmeup.mappers.Mapper;
import org.abondar.experimental.countmeup.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@Import(DataBaseConfiguration.class)
@SpringBootApplication(scanBasePackageClasses = CountApplication.class)
public class DataBaseTest {
    static Logger logger = LoggerFactory.getLogger(DataBaseTest.class);

    @Autowired
    private Mapper mapper;

    @Test
    public void testInsertUser(){
        logger.info("Insert User Test");
        mapper.deleteAllUsers();

        UUID uuid = UUID.randomUUID();
        String userID = uuid.toString();
        User user = new User(uuid.toString(),"sadadsad");

        mapper.insertOrUpdateUser(user);

        User foundUser = mapper.findUserByUserId(userID);
        logger.info(foundUser.toString());
        assertTrue("User Id is fine", foundUser.getUserId().equals(userID));

    }
}
