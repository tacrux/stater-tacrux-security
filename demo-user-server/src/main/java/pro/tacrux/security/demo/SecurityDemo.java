package pro.tacrux.security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("pro.tacrux.security")
public class SecurityDemo {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemo.class, args);
    }

}
