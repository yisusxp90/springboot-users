package com.springboot.usuarios.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.usuarios.app.model.Phone;
import com.springboot.usuarios.app.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpringbootUsuariosApplicationTests {

	@Autowired
	WebApplicationContext webApplicationContext;

	@Test
	@Order(1)
	void createUser() throws Exception {
		User user = new User();
		setUserInfo(user);

		MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		String inputJson = mapToJson(user);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/api/users/create")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertThat (status).isEqualTo(201);
	}

	@Test
	void updateUser() throws Exception {

		User user = new User();
		setUserInfo(user);

		MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		String inputJson = mapToJson(user);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/api/users/update/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertThat (status).isEqualTo(201);
	}

	@Test
	void getUserList() throws Exception {

		MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/api/users/list")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		User[] productlist = mapFromJson(content, User[].class);
		assertThat (productlist.length > 0);
	}

	private void setUserInfo(User user) {
		user.setName("Yisus");
		user.setEmail("yisusxp90@gmail.com");
		user.setPassword("Aa12345678");
		List<Phone> phones = new ArrayList<>();
		Phone phone = new Phone();
		phone.setNumber("+56984875896");
		phone.setCityCode("1");
		phone.setCountryCode("2");
		phones.add(phone);
		user.setPhones(phones);
	}

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(MapperFeature.USE_ANNOTATIONS);
		return objectMapper.writeValueAsString(obj);
	}
	private <T> T mapFromJson(String json, Class<T> clazz) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
