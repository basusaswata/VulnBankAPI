package com.homelab.vulbankapi;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;

class VulnBankControllerTest {

    @Test
    void doesNotExposeEnvironmentVariablesEndpoint() {
        boolean exposed = Arrays.stream(VulnBankController.class.getDeclaredMethods())
                .map(m -> m.getAnnotation(GetMapping.class))
                .filter(a -> a != null)
                .flatMap(a -> Arrays.stream(a.value()))
                .anyMatch(path -> path.contains("debug/env"));
        assertFalse(exposed, "/api/debug/env must not be exposed");
    }

    @Test
    void doesNotReturnProcessEnvironment() {
        boolean returnsEnv = Arrays.stream(VulnBankController.class.getDeclaredMethods())
                .map(Method::getName)
                .anyMatch(name -> name.equals("getEnv"));
        assertFalse(returnsEnv, "controller must not provide a getEnv handler");
    }
}
