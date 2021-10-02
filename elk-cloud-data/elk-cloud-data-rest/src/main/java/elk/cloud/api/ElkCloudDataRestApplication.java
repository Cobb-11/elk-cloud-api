package elk.cloud.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages={"elk.cloud.api.feignclient"})
@SpringBootApplication
public class ElkCloudDataRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElkCloudDataRestApplication.class, args);
	}

}
