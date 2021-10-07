package com.fyhao.springwebapps.business;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ScriptExecutor {

	public static String runcode(String src) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine scriptEngine = manager.getEngineByName("nashorn");
		Object result;
		try {
			result = scriptEngine.eval(src);
			return (String)result;
		} catch (ScriptException e) {
			return e.getMessage();
		}
	}
	public static String runcodeResource(String filename) {
		Resource resource = new ClassPathResource(filename);
    	String code1;
		try {
			code1 = Files.readString(Path.of(resource.getURI()));
			return runcode(code1);
		} catch (IOException e) {
			return e.getMessage();
		}
	}
}

