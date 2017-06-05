package blog.config;

import blog.springrepo.RepoPackage;
import blog.springrepo.entity.EntityPackage;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "emf", 
    transactionManagerRef = "tm", 
    basePackageClasses = {RepoPackage.class}
    )
@EnableTransactionManagement
@PropertySource(value = {"classpath:jpa.properties"}, ignoreResourceNotFound = true)
public class OracleDBConfiguration {

    @Value("${persistenceUnit.name}")
    private String persistenceUnit;

   @Value("${hibernate.show.sql}")
    private String showSql;

    @Value("${datasource.jndi}")
    private String datasourceJndi;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(Boolean.valueOf(showSql));
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.ORACLE);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(datasourceJndi);
    }

    @Bean(name = "emf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(jpaVendorAdapter());
        factory.setDataSource(dataSource());
        factory.setPackagesToScan(getEntityPackage());
        factory.setPersistenceUnitName(persistenceUnit);
        return factory;
    }

    @Bean(name = "tm")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    private String[] getEntityPackage() {
        String className=EntityPackage.class.getName();
        String packageName=className.substring(0, className.lastIndexOf('.'));
        return new String[]{packageName};
    }

}
