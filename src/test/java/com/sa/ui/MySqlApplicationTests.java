package com.sa.ui;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * Checks that the Spring context starts. No database or Eureka server is needed:
 * the connection pool only connects on first use, and Eureka is switched off here.
 */
@SpringBootTest(properties = {
		"STUDYAID_DB_PASSWORD=unused-in-this-test",
		"eureka.client.enabled=false"
})
class MySqlApplicationTests {

	@Test
	void contextLoads() {
	}

}
