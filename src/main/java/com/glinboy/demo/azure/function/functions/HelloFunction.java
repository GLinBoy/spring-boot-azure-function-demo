package com.glinboy.demo.azure.function.functions;

import java.util.Map;
import java.util.Optional;

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
public class HelloFunction {

	@FunctionName("hello")
	public HttpResponseMessage execute(
			@HttpTrigger(name = "request",
				methods = { HttpMethod.GET},
				authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<User>> request,
			ExecutionContext context) {
		
		String name = request.getQueryParameters().getOrDefault("name", "world");
		
		return request
				.createResponseBuilder(HttpStatus.OK)
				.body(Map.of("message", String.format("Hello %s!", name)))
				.header("Content-Type", "application/json")
				.build();
	}
}
