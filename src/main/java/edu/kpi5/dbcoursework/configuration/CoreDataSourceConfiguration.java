package edu.kpi5.dbcoursework.configuration;

import com.zaxxer.hikari.HikariDataSource;
import edu.kpi5.dbcoursework.entities.coredb.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        basePackages = "edu.kpi5.dbcoursework.dbaccess.coredb",
//        entityManagerFactoryRef = "coreEntityManagerFactory",
//        transactionManagerRef= "coreTransactionManager"
//)
public class CoreDataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.core")
    public DataSourceProperties coreDataSourceProperties() {
        return new DataSourceProperties();
    }
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.core.configuration")
    public DataSource coreDataSource() {
        return coreDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "coreEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean coreEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(coreDataSource())
                .packages("edu.kpi5.dbcoursework.entities.coredb")
                .build();
    }
    @Primary
    @Bean
    public PlatformTransactionManager coreTransactionManager(
            final @Qualifier("coreEntityManagerFactory") LocalContainerEntityManagerFactoryBean coreEntityManagerFactory) {
        return new JpaTransactionManager(coreEntityManagerFactory.getObject());
    }
}
