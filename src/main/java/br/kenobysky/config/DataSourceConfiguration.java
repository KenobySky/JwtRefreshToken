package br.kenobysky.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class DataSourceConfiguration {

    private static HikariDataSource dataSource;

    public DataSourceConfiguration() {

    }

    @Bean(name = "dataSource")
    public DataSource dataSource(Environment env) {
        String url = env.getRequiredProperty("spring.datasource.url");
        String classname = env.getRequiredProperty("spring.datasource.driver-class-name");

        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        Integer maxPoolSize = env.getProperty("spring.datasource.hikari.max-pool-size", Integer.class);

        if (dataSource == null) {
            dataSource = new HikariDataSource();
            //

            //
            dataSource.setDriverClassName(classname);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setJdbcUrl(url);
            dataSource.setMaximumPoolSize(maxPoolSize); //10 is the default
            dataSource.setPoolName("Hikari-Pool");

        }

        return dataSource;
    }

}
