package vivek.github.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "vivek.github.api")
@EnableFeignClients
public class GitHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(GitHubApplication.class, args);
    }
}