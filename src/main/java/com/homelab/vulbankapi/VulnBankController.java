package com.homelab.vulbankapi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

@RestController
@RequestMapping("/api")
public class VulnBankController {

    @Autowired private UserRepository userRepository;
    @Autowired private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LogManager.getLogger(VulnBankController.class);

    // Improper Asset Management
    @GetMapping("/legacy/status")
    public String legacyStatus() { return "Legacy API running!"; }

    // Excessive Data Exposure
    @GetMapping("/profile/{username}")
    public User profile(@PathVariable String username) { return userRepository.findByUsername(username); }

    // Broken User Authentication
    @PostMapping("/auth/login")
    public String login(@RequestBody Map<String, String> credentials) {
        User user = userRepository.findByUsername(credentials.get("username"));
        if (user != null && user.getPassword().equals(credentials.get("password"))) return "Login Successful!";
        return "Invalid Credentials";
    }

    // Mass Assignment
    @PostMapping("/update/{id}")
    public User update(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        User user = userRepository.findById(id).orElseThrow();
        updates.forEach((key, value) -> {
            try {
                Field field = User.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(user, value);
            } catch (Exception ignored) {}
        });
        return userRepository.save(user);
    }

    // Broken Function Level Authorization
    @GetMapping("/admin/all-users")
    public List<User> allUsers() { return userRepository.findAll(); }

    // BOLA
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) { return userRepository.findById(id).orElse(null); }

    // Rate Limiting
    private List<String> comments = new ArrayList<>();
    @PostMapping("/comments/add")
    public String addComment(@RequestBody String comment) {
        comments.add(comment);
        return "Comment added!";
    }

    // Security Misconfiguration
    @GetMapping("/debug/env")
    public Map<String, String> getEnv() { return System.getenv(); }

    // SQL Injection
    @GetMapping("/search")
    public List<Map<String, Object>> search(@RequestParam String name) {
        return jdbcTemplate.queryForList("SELECT * FROM user WHERE username = '" + name + "'");
    }

    // Insufficient Logging
    @PostMapping("/payment/process")
    public String processPayment(@RequestBody Map<String, String> paymentDetails) {
        return "Payment processed: " + paymentDetails;
    }

    // Log4Shell
    @GetMapping("/log4j/exploit")
    public String logExploit(@RequestParam String input) {
        logger.info("Logging user input: " + input);
        return "Logged: " + input;
    }
}
