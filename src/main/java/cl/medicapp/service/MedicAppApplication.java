package cl.medicapp.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MedicAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicAppApplication.class, args);
    }

}
