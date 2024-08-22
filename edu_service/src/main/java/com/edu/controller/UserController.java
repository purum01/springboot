package com.edu.controller;

import com.edu.dto.UserDTO;
import com.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get user by ID", description = "Fetch a user by their ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Found the user", content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    @Operation(summary = "Get all users", description = "Fetch all users")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Fetched all users", content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Create a new user", description = "Add a new user to the system")
    @ApiResponses({
        @ApiResponse(
            responseCode = "201", 
            description = "User created successfully", 
            content = @Content(schema = @Schema(implementation = UserDTO.class)),
            headers = @Header(name = "Location", description = "The URI of the newly created user", schema = @Schema(type = "string"))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody String name) {
        UserDTO createdUser = userService.createUser(name);
        return ResponseEntity.status(201).header("Location", "/api/users/" + createdUser.getId()).body(createdUser);
    }

    @Operation(summary = "Update user by ID", description = "Update a user's name by their ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid ID or input supplied"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserById(@PathVariable String id, @RequestBody String name) {
        UserDTO updatedUser = userService.updateUser(id, name);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete user by ID", description = "Delete a user by their ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{seq}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String seq) {
        boolean deleted = userService.deleteUser(seq);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}
