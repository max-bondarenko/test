package max.cf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.dataflow.server.EnableDataFlowServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@EnableEurekaClient
@EnableDataFlowServer
@SpringBootApplication
public class DflowSrvApplication {

	public static void main(String[] args) {
		SpringApplication.run(DflowSrvApplication.class, args);
	}
}
