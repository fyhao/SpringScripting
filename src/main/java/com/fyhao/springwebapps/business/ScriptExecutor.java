package com.fyhao.springwebapps.business;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class ScriptExecutor {

	public static String runcode(String src) {
		try {
			return String.valueOf(evaluate(src));
		} catch (ScriptException e) {
			return e.getMessage();
		}
	}

	public static Object evaluate(String src) throws ScriptException {
		return createEngine().eval(src);
	}

	public static Object evaluate(String src, Map<String, ?> variables) throws ScriptException {
		ScriptEngine scriptEngine = createEngine();
		Bindings bindings = scriptEngine.createBindings();
		bindings.putAll(variables);
		return scriptEngine.eval(src, bindings);
	}

	private static ScriptEngine createEngine() {
		ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
		if (scriptEngine == null) {
			throw new IllegalStateException("Nashorn script engine is not available");
		}
		return scriptEngine;
	}

	public static String runcodeResource(String filename) {
		Resource resource = new ClassPathResource(filename);
		try (InputStream input = resource.getInputStream()) {
			return runcode(new String(input.readAllBytes(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			return e.getMessage();
		}
	}
}
