package test;


import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fyhao.springwebapps.SpringWebMain;
import com.fyhao.springwebapps.business.CustomService;
import com.fyhao.springwebapps.business.ScriptExecutor;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SpringWebMain.class)
public class TestingWebApplicationTests {

	@LocalServerPort
	private int port;
	
	
	@Autowired
	private TestRestTemplate restTemplate;

    
    @Test
	public void firstNashornTest() throws Exception {
    	String jscode = "var a = '1'; a;";
    	String result = ScriptExecutor.runcode(jscode);
    	assertThat(result).isEqualTo("1");
    	String datestr = new java.util.Date().toString();
    	jscode = "var Date = java.util.Date; var a = new Date().toString(); a;";
    	result = ScriptExecutor.runcode(jscode);
    	assertThat(result).isEqualTo(datestr);
    	jscode = "var C = Java.type('com.fyhao.springwebapps.business.CustomService'); ";
    	jscode += "var a = new C(); var b = a.add(1,2); '' + b;";
    	result = ScriptExecutor.runcode(jscode);
    	assertThat(result).isEqualTo("3");
    	jscode = "var C = Java.type('com.fyhao.springwebapps.business.CustomService'); ";
    	jscode += "C.c++; 'e'; ";
    	ScriptExecutor.runcode(jscode);
    	assertThat(CustomService.c).isEqualTo(1);
    	result = ScriptExecutor.runcodeResource("test1.js");
    	String[] arr = result.split("\\|");
    	assertThat(arr[0]).isEqualTo(arr[1]);
	}
    
}