package com.glinboy.demo.azure.function.functions;

import java.time.Instant;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;

@Component
public class TimerTriggerFunction {

	private static final Logger log = Logger.getLogger(TimerTriggerFunction.class.getName());

	@FunctionName("callAPIByTimer")
	public void timer(@TimerTrigger(name = "timeTrigger", schedule = "0 * * * * *") String timerInfo) {
		log.info("Triggered at: " + Instant.now().toString());
	}
}
