package org.abondar.experimental.countmeup.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.abondar.experimental.countmeup.app.CountApplication;
import org.abondar.experimental.countmeup.mappers.Mapper;
import org.abondar.experimental.countmeup.model.Competition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CountApplication.class)
@WebAppConfiguration
public class RestApiTest {
    static Logger logger = LoggerFactory.getLogger(RestApiTest.class);

    @Autowired
    private Mapper mapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper om;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(context).build();
    }

    @Test
    public void testAuthUser() throws Exception {
        logger.info("Auth user test");
        clearDb();

        mockMvc.perform(post("/count_me/authorize_user").param("token", "aaaaaa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
        clearDb();
    }


    @Test
    public void addCompetition() throws Exception {

        logger.info("Add competition test");
        clearDb();

        Date startDate = new Date();
        Competition competition = new Competition(startDate.toString());


        mockMvc.perform(post("/count_me/add_competition").content(om.writeValueAsBytes(competition)))
                .andExpect(status().isOk());


        clearDb();

    }

    @Test
    public void testGetCompetition() throws Exception {
        logger.info("Get competition test");
        clearDb();

        Date startDate = new Date();
        Competition competition = new Competition(startDate.toString());

        mapper.insertOrUpdateCompetition(competition);

        mockMvc.perform(get("/count_me/get_competition").param("competition", String.valueOf(competition.getId())))
                .andExpect(status().isOk());

        clearDb();
    }


    private void clearDb() {
        mapper.deleteAllVotes();
        mapper.deleteAllCandidates();
        mapper.deleteAllCompetitions();

    }

}
