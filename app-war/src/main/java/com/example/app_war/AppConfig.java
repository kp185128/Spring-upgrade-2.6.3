package com.example.app_war;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ImportResource("classpath*:spring/*.xml")
public class AppConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    private static DataSource ds;

    @Bean(name = "dataSource")
    public DataSource jndiDataSource(Environment env) {
        logProfiles(env);
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        ds = dsLookup.getDataSource("jdbc/Connections");
        return ds;
    }

    private void logProfiles(Environment env) {
        for (String profile : env.getActiveProfiles()) {
            LOGGER.info("ACTIVE PROFILE:" + profile);
        }
    }

    @Bean(name = "jpaProperties")
    public Properties ddlAutoJPAProperties() {
        Properties p = new Properties();
        p.setProperty("hibernate.id.new_generator_mappings", "true");
        p.setProperty("hibernate.validator.apply_to_ddl", "false");
        p.setProperty("hibernate.validator.autoregister_listeners", "false");
        p.setProperty("hibernate.hbm2ddl.auto", "update");
        p.setProperty("hibernate.jdbc.batch_size", "20");
        p.setProperty("hibernate.cache.use_second_level_cache", "false");
        return p;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(PersistenceUnitManager persistenceUnitManager,
            Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPersistenceUnitManager(persistenceUnitManager);
        entityManagerFactory.setPersistenceUnitName("ConnectionsPU");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        checkSqlServer(jpaProperties);
        entityManagerFactory.setJpaProperties(jpaProperties);
        return entityManagerFactory;
    }

    private void checkSqlServer(Properties jpaProperties) {
        try {
            if (ds != null
                    && ds.getConnection() != null
                    && ds.getConnection().getMetaData() != null
                    && ds.getConnection().getMetaData().getDatabaseProductName() != null) {
                if (ds.getConnection().getMetaData().getDatabaseProductName().equals("Microsoft SQL Server")) {
                    jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
                } else if (ds.getConnection().getMetaData().getDatabaseProductName().equals("Oracle")) {
                    jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
                }
            }
        } catch (SQLException e) {
            LOGGER.info("Failure setting hibernate Dialect");
        }
    }
}
