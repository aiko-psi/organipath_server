package de.aklingauf.organipath;

import de.aklingauf.organipath.controller.AuthController;
import de.aklingauf.organipath.controller.UserController;
import de.aklingauf.organipath.payload.SignUpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganipathApplicationTests {

	@Autowired
	UserController userController;

	@Autowired
	AuthController authController;

	@Test
	public void contextLoads() {
		assertThat(userController).isNotNull();
		assertThat(authController).isNotNull();
	}

	@Test
	public void registerUser(){
		SignUpRequest req = new SignUpRequest();
		req.setName("aklingauff");
		req.setUsername("aik");
		req.setEmail("blackaiko@gmx.de");
		req.setPassword("TestTest");
		req.setVoucher("0264/V!947#");

		ResponseEntity resp = authController.registerUser(req);
		assertThat(resp.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
	}

}
