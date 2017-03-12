package org.abondar.experimental.countmeup.configurations;

import org.abondar.experimental.countmeup.model.Candidate;
import org.abondar.experimental.countmeup.model.Competition;
import org.abondar.experimental.countmeup.model.User;
import org.abondar.experimental.countmeup.model.Vote;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan("org.abondar.experimental.countmeup.mappers")
@PropertySource("classpath:db.properties")
public class DataBaseConfiguration {



    @Value("${ip_address}")
    public String ipAddress;

    @Value("${port}")
    public String port;

    @Value("${db_name}")
    public String dbName;

    @Value("${username}")
    public String username;

    @Value("${password}")
    public String password;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://" + ipAddress
                + ":" + port + "/" + dbName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }


    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());

    }


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        Resource resource = new ClassPathResource("/mapper.xml");
        Resource[] resources = new Resource[]{resource};
        sessionFactory.setMapperLocations(resources);

        Class<?>[] aliases = new Class<?>[]{
                User.class,
                Candidate.class,
                Competition.class,
                Vote.class
        };
        sessionFactory.setTypeAliases(aliases);

        return sessionFactory.getObject();
    }


}
