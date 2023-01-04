package edu.kpi5.dbcoursework.configuration;

import com.zaxxer.hikari.HikariDataSource;
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
//        basePackages = "edu.kpi5.dbcoursework.dbaccess.marksdb",
//        entityManagerFactoryRef = "marksEntityManagerFactory",
//        transactionManagerRef= "marksTransactionManager"
//)
public class MarksDataSourceConfiguration {
    @Bean
    @ConfigurationProperties("app.datasource.marks")
    public DataSourceProperties marksDataSourceProperties() {
        return new DataSourceProperties();
    }
    @Bean
    @ConfigurationProperties("app.datasource.marks.configuration")
    public DataSource marksDataSource() {
        return marksDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }
    @Bean(name = "marksEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean marksEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(marksDataSource())
                .packages("edu.kpi5.dbcoursework.entities.marksdb")
                .build();
    }
    @Bean
    public PlatformTransactionManager marksTransactionManager(
            final @Qualifier("marksEntityManagerFactory") LocalContainerEntityManagerFactoryBean marksEntityManagerFactory) {
        return new JpaTransactionManager(marksEntityManagerFactory.getObject());
    }
}