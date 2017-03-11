package org.abondar.experimental.countmeup.controllers;

import org.abondar.experimental.countmeup.app.CountApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


/**
 * Created by abondar on 3/10/17.
 */
@RestController
@RequestMapping("/count_me")
public class RestAPIController {

    static Logger logger = LoggerFactory.getLogger(CountApplication.class);

    public RestAPIController() {
        logger.info("REST Service started");
    }


    @RequestMapping(value = "/authorize_user", method = RequestMethod.POST)
    @ResponseBody
    public String authUser(@RequestBody String body) {
        logger.info("rest works " + body);
        return "ss1";
    }


}
