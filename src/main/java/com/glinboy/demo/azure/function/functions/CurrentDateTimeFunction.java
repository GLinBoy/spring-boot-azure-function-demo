package com.glinboy.demo.azure.function.functions;

import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.glinboy.demo.azure.function.dto.ZoneDTO;
import com.glinboy.demo.azure.function.service.FormatDateTime;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurrentDateTimeFunction {
	
	private final FormatDateTime formatDateTime;
	
	@FunctionName("now")
	public HttpResponseMessage execute(
			@HttpTrigger(name = "request",
				methods = { HttpMethod.GET, HttpMethod.POST },
				authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<ZoneDTO>> request,
			ExecutionContext context) {
		Set<ZoneOffset> zones = new HashSet<>();
		zones.add(ZoneOffset.UTC);
		request.getBody().filter(z -> z.zone() != null)
			.ifPresent(z -> zones.add(z.zone()));
		
		Map<String, String> response = zones.stream()
			.collect(Collectors.toMap(Object::toString, formatDateTime::apply));
		
		return request
				.createResponseBuilder(HttpStatus.OK)
				.body(response)
				.header("Content-Type", "application/json")
				.build();
	}
}
