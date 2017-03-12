package org.abondar.experimental.countmeup.configurations;

import org.abondar.experimental.countmeup.mappers.Mapper;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by abondar on 3/10/17.
 */

@Configuration
@MapperScan("org.abondar.experimental.countmeup.mappers")
@PropertySource("classpath:db.properties")
public class DataBaseConfiguration {


    @Value("${driverClassName}")
    public String driverClassName;

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
    public DataSource dataSource(){
        BasicDataSource dataSource =  new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl("jdbc:mysql://"+ipAddress
                +":"+port+"/"+dbName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }


    @Bean
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());

    }


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        Resource resource = new ClassPathResource("/db_configuration.xml");
        sessionFactory.setConfigLocation(resource);

        return sessionFactory.getObject();
    }

    @Bean
    public Mapper mapper() throws Exception{
        final SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory());
        template.getConfiguration().addMapper(Mapper.class);

        return template.getMapper(Mapper.class);
    }
}
