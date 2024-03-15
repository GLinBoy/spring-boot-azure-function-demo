package com.glinboy.demo.azure.function.service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FormatDateTime implements Function<ZoneOffset, String> {
	
	@Value("${application.config.date-time-pattern}")
	private String dateTimePattern;
	
	@Override
	public String apply(ZoneOffset offset) {
		Instant now = Instant.now();
		return DateTimeFormatter
			.ofPattern(dateTimePattern)
			.withZone(offset)
			.format(now);
	}
}
