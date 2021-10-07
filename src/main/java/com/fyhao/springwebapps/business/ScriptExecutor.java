package com.fyhao.springwebapps.business;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
}
