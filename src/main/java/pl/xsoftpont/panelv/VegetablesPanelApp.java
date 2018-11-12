package pl.xsoftpont.panelv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication(scanBasePackages = "pl")
@EnableJpaRepositories
@EnableWebMvc
@EnableSpringDataWebSupport
public class VegetablesPanelApp {

    public static void main(String[] args) {
        SpringApplication.run(VegetablesPanelApp.class, args);
    }
}
