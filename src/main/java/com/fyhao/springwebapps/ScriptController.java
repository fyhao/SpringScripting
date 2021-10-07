package com.fyhao.springwebapps;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="script")
public class ScriptController {

	@RequestMapping("/")
	public @ResponseBody String home() {
		return "test home: " + new Date().toString();
	}
}
