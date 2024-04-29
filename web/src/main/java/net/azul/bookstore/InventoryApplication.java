package net.azul.bookstore;

import net.azul.bookstore.config.CoreModuleConfig;
import net.azul.bookstore.config.WebModuleConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {
        CoreModuleConfig.class,
        WebModuleConfig.class
})
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }
}
