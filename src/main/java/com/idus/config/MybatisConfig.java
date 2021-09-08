package com.idus.config;

import com.idus.core.annotation.Master;
import com.idus.core.annotation.Slave;
import com.idus.properties.DatabaseProperties;
import com.idus.properties.IDusConstant;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = IDusConstant.BASE_PACKAGE, annotationClass = Master.class, sqlSessionFactoryRef = "masterSqlSessionFactory")
class MasterMyBatisConfig extends DatabaseConfig {

    @Primary
    @Bean(name = "masterProperties")
    @ConfigurationProperties("idus.datasource.master")
    public DatabaseProperties slaveDatabaseProperties() {
        return new DatabaseProperties();
    }

    @Primary
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource(@Qualifier("masterProperties") DatabaseProperties masterDatabaseProperties) {
        return dataSourceConfig(masterDatabaseProperties);
    }

    @Primary
    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        configureCommonSqlSessionFactory(masterDataSource, sessionFactoryBean);
        return sessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "masterTransaction")
    public PlatformTransactionManager transactionManager(@Qualifier("masterDataSource") DataSource masterDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(masterDataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
}

@Configuration
@MapperScan(basePackages = IDusConstant.BASE_PACKAGE, annotationClass = Slave.class, sqlSessionFactoryRef = "slaveSqlSessionFactory")
class SlaveMyBatisConfig extends DatabaseConfig {


    @Bean(name = "slaveProperties")
    @ConfigurationProperties("idus.datasource.slave")
    public DatabaseProperties slaveDatabaseProperties() {
        return new DatabaseProperties();
    }

    @Bean(name = "slaveDataSource")
    public DataSource masterDataSource(@Qualifier("slaveProperties") DatabaseProperties slaveDatabaseProperties) {
        return dataSourceConfig(slaveDatabaseProperties);
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slaveDataSource") DataSource slaveDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        configureCommonSqlSessionFactory(slaveDataSource, sessionFactoryBean);
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "slaveTransaction")
    public PlatformTransactionManager transactionManager(@Qualifier("slaveDataSource") DataSource slaveDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(slaveDataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
}