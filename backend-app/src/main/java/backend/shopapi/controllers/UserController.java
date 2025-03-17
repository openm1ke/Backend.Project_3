package backend.shopapi.controllers;

import backend.shopapi.dto.AddressDTO;
import backend.shopapi.dto.PaginatedResponse;
import backend.shopapi.dto.UserCreationDTO;
import backend.shopapi.dto.UserDTO;
import backend.shopapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Clients", description = "Endpoints for managing clients")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users", responses = {
        @ApiResponse(responseCode = "200", description = "Users found")
    })
    public ResponseEntity<PaginatedResponse<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        PaginatedResponse<UserDTO> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    @Operation(summary = "Get user by name and surname", responses = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<UserDTO>> getUserByClientNameAndSurname(
        @RequestParam(name = "client_name")
        @Parameter(description = "The client's first name")
        String client_name,

        @RequestParam(name = "client_surname")
        @Parameter(description = "The client's surname")
        String client_surname) {
        List<UserDTO> users = userService.getUserByClientNameAndSurname(client_name, client_surname);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", responses = {
        @ApiResponse(responseCode = "200", description = "User found"),
    })
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    @Operation(summary = "Create new user", responses = {
        @ApiResponse(responseCode = "201", description = "User created")
    })
    public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserCreationDTO userCreationDTO) {
        UserDTO createdUser = userService.createUser(userCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID", responses = {
        @ApiResponse(responseCode = "204", description = "User deleted"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update user by ID", responses = {
        @ApiResponse(responseCode = "200", description = "User updated"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,@Validated @RequestBody AddressDTO addressDTO) {
        UserDTO updatedUser = userService.updateUserAddress(id, addressDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
