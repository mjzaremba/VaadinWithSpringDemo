package org.vaadin.spring.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//exclude={DataSourceAutoConfiguration.class}
@SpringBootApplication()
@EntityScan("org.vaadin.spring.tutorial.Models")
@EnableJpaRepositories("org.vaadin.spring.tutorial.Repositories")
public class TutorialApplication extends SpringBootServletInitializer {

    /**
     * The main method makes it possible to run the application as a plain Java
     * application which starts embedded web server via Spring Boot.
     *
     * @param args command line parameters
     */

    public static void main(String[] args) {
        SpringApplication.run(TutorialApplication.class, args);
    }
    //public PrismaRepository repository;
}
