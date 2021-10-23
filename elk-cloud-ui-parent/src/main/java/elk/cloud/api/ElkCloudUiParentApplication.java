package elk.cloud.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages={"elk.cloud.api.feignclient"})
@EnableDiscoveryClient
@SpringBootApplication
public class ElkCloudUiParentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElkCloudUiParentApplication.class, args);
	}

}
