package com.glqdlt.exsecurityjwt;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebAppConfiguration
@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExSecurityJwtApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;


	private MockMvc mvc;

	@Before
	public void construnt(){
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void getToken() throws Exception {

//		http://javadox.com/org.springframework.security.oauth/spring-security-oauth2/1.0.5.RELEASE/org/springframework/security/oauth2/provider/endpoint/TokenEndpoint.java.html
//		 실제 스프링 시큐리티 개발 스팩은 저렇게 되어있음.


//		You need to pass data as form data instead of url parameters. Your command should be ..
//		curl -u aClient:aSecret --data "grant_type=password&username=mauricio.coder&password=123" -X POST -H "Content-Type:application/x-www-form-urlencoded" http://localhost:9000/oauth/token
//		라고 하는 코멘트를 보아서 수정해보기로 함.

		MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
		map.add("username","admin");
		map.add("password","admin1234");
		map.add("grant_type","password");


		MvcResult mvcResult = mvc.perform(post("http://foo:bar@127.0.0.1:8080/oauth/token").contentType(MediaType.APPLICATION_FORM_URLENCODED).params(map))
				.andReturn();

		System.out.println("status:"+mvcResult.getResponse().getStatus());
		System.out.println("body:"+mvcResult.getResponse().getContentAsString());



	}

}
