package com.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("1", "John Doe");
        users.put("2", "Jane Doe");
    }

    @Operation(summary = "Get user by ID", description = "Fetch a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "User not found") })
    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable String id) {
        if (users.containsKey(id)) {
            return ResponseEntity.ok(users.get(id));
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input") })
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody String name) {
        String id = String.valueOf(users.size() + 1);
        users.put(id, name);
        return ResponseEntity.status(201).body("User created with ID: " + id);
    }
}
