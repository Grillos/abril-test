package com.abril.test;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

}
