package com.glinboy.demo.azure.function.functions;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

@Service
public class CurrentDateTimeFunction {
	
	@Value("${application.config.date-time-pattern}")
	private String dateTimePattern;
	
	@FunctionName("now")
	public HttpResponseMessage execute(
			@HttpTrigger(name = "request",
				methods = { HttpMethod.GET, HttpMethod.POST },
				authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<User>> request,
			ExecutionContext context) {
		Instant now = Instant.now();
		String nowUTC = DateTimeFormatter
			.ofPattern(dateTimePattern)
			.withZone(ZoneOffset.UTC)
			.format(now);
		
		return request
				.createResponseBuilder(HttpStatus.OK)
				.body(Map.of("UTC", String.format("%s", nowUTC)))
				.header("Content-Type", "application/json")
				.build();
	}
}
