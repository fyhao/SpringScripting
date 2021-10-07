package test;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.fyhao.springwebapps.SpringWebMain;
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
	}
    
}