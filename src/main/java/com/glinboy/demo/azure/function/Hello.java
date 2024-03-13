package com.glinboy.demo.azure.function;

import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class Hello implements Function<User, Greeting> {

  @Override
  public Greeting apply(User user) {
    return new Greeting("Hello, " + user.getName() + "!\n");
  }
}

