package com.hps.admindashboardservice.controllers;

import com.hps.admindashboardservice.S3BucketConfig.S3Service;
import com.hps.admindashboardservice.dto.*;

import com.hps.admindashboardservice.entities.*;
import com.hps.admindashboardservice.entities.Currency;
import com.hps.admindashboardservice.repos.*;
import com.hps.admindashboardservice.security.JwtTokenProvider;
import com.hps.admindashboardservice.services.AdminDashboardService;

import com.hps.admindashboardservice.services.AuthService;
import com.hps.admindashboardservice.services.UserPicService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService adminDashboardService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserPicService userpic;

    @Autowired
    private userRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FeePercentageRepository feePercentageRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private FeeTypeRepository feeTypeRepository;

    @Autowired
    private SettlementTypeRepository settlementTypeRepository;

    @Autowired
    private CdfTypesRepository cdfTypesRepository;


    @Autowired
    private roleRepository roleRepository;

    @Autowired
    private permissionRepository permissionRepository;

    @Autowired
    private AuthService authService;

    private List<PermissionDTO> createDefaultPermissions() {
        List<PermissionDTO> permissionDTOs = Arrays.asList(
                new PermissionDTO("VIEW_DASHBOARD"), new PermissionDTO("VIEW_MERCHANT_MANAGEMENT"),
                new PermissionDTO("VIEW_CONTRACT_CREATION"), new PermissionDTO("VIEW_CASES_AND_EXCEPTIONS"),
                new PermissionDTO("VIEW_USER_MANAGEMENT"), new PermissionDTO("VIEW_DEVICE_MANAGEMENT"),
                new PermissionDTO("VIEW_ACCOUNT_PAGE"), new PermissionDTO("CDFTYPE_CREATE"),
                new PermissionDTO("CDFTYPE_READ"), new PermissionDTO("CDFTYPE_UPDATE"), new PermissionDTO("CDFTYPE_DELETE"),
                new PermissionDTO("VIEW_CDF_TYPES"), new PermissionDTO("CURRENCY_CREATE"), new PermissionDTO("CURRENCY_READ"),
                new PermissionDTO("CURRENCY_UPDATE"), new PermissionDTO("CURRENCY_DELETE"), new PermissionDTO("VIEW_CURRENCIES"),
                new PermissionDTO("FEEPERCENTAGE_CREATE"), new PermissionDTO("FEEPERCENTAGE_READ"),
                new PermissionDTO("FEEPERCENTAGE_UPDATE"), new PermissionDTO("FEEPERCENTAGE_DELETE"),
                new PermissionDTO("VIEW_FEE_PERCENTAGES"), new PermissionDTO("FEETYPE_CREATE"), new PermissionDTO("FEETYPE_READ"),
                new PermissionDTO("FEETYPE_UPDATE"), new PermissionDTO("FEETYPE_DELETE"), new PermissionDTO("VIEW_FEE_TYPES"),
                new PermissionDTO("SETTLEMENTTYPE_CREATE"), new PermissionDTO("SETTLEMENTTYPE_READ"),
                new PermissionDTO("SETTLEMENTTYPE_UPDATE"), new PermissionDTO("SETTLEMENTTYPE_DELETE"),
                new PermissionDTO("VIEW_SETTLEMENT_TYPES"), new PermissionDTO("EXCEPTION_VIEW_LIST"),
                new PermissionDTO("EXCEPTION_RESOLVE"), new PermissionDTO("EXCEPTION_REPORT"),
                new PermissionDTO("EXCEPTION_VIEW_DETAILS"), new PermissionDTO("EXCEPTION_ASSIGN"),
                new PermissionDTO("EXCEPTION_CLOSE"), new PermissionDTO("UNDERCREDIT_VIEW_CASES"),
                new PermissionDTO("UNDERCREDIT_HANDLE"), new PermissionDTO("OVERCREDIT_VIEW_CASES"),
                new PermissionDTO("OVERCREDIT_HANDLE"), new PermissionDTO("MERCHANT_VIEW_LIST"),
                new PermissionDTO("MERCHANT_CREATE"), new PermissionDTO("MERCHANT_UPDATE"),
                new PermissionDTO("MERCHANT_DELETE"), new PermissionDTO("CONTRACT_VIEW_LIST"),
                new PermissionDTO("CONTRACT_CREATE"), new PermissionDTO("CONTRACT_UPDATE"),
                new PermissionDTO("CONTRACT_DELETE"), new PermissionDTO("DEVICE_VIEW_LIST"),
                new PermissionDTO("DEVICE_CREATE"), new PermissionDTO("DEVICE_UPDATE"),
                new PermissionDTO("DEVICE_DELETE"), new PermissionDTO("USER_VIEW_LIST"),
                new PermissionDTO("USER_CREATE"), new PermissionDTO("USER_UPDATE"),
                new PermissionDTO("USER_DELETE")
        );

        // Create and save permissions, then convert to DTOs
        return permissionDTOs.stream()
                .map(dto -> {
                    Permission permission = new Permission(dto.getLabel());
                    permission = permissionRepository.save(permission);
                    dto.setId(permission.getId()); // Set the ID after saving
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private UserRoleDTO createDefaultRole(List<PermissionDTO> permissionDTOs) {
        // Create a new Role object
        Role adminRole = new Role();
        adminRole.setLabel("adminSystem");

        // Retrieve permissions from the repository based on the PermissionDTOs
        List<Permission> permissions = permissionDTOs.stream()
                .map(dto -> permissionRepository.findById(dto.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + dto.getId())))
                .collect(Collectors.toList());

        // Create a List of RolePermission based on the permissions retrieved
        Role finalAdminRole = adminRole;
        List<RolePermission> rolePermissions = permissions.stream()
                .map(permission -> {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRole(finalAdminRole);
                    rolePermission.setPermission(permission);
                    return rolePermission;
                })
                .collect(Collectors.toList());

        // Set the rolePermissions to the Role entity
        adminRole.setRolePermissions(rolePermissions);

        // Save the Role entity along with the rolePermissions
        adminRole = roleRepository.save(adminRole);

        // Construct and populate the UserRoleDTO
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setId(adminRole.getId());
        userRoleDTO.setLabel(adminRole.getLabel());

        // Convert RolePermission entities to RolePermissionDTOs
        List<RolePermissionDTO> rolePermissionDTOs = adminRole.getRolePermissions().stream()
                .map(rolePermission -> {
                    RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
                    rolePermissionDTO.setId(rolePermission.getId());
                    rolePermissionDTO.setRoleId(rolePermission.getRole().getId());
                    rolePermissionDTO.setPermissionId(rolePermission.getPermission().getId());
                    return rolePermissionDTO;
                })
                .collect(Collectors.toList());

        // Set the RolePermissionDTOs in the UserRoleDTO
        userRoleDTO.setRolePermissions(rolePermissionDTOs);

        return userRoleDTO;
    }

    @PostConstruct
    public void init() {
        if (adminDashboardService.getUserByUsername("adminSystem") == null) {
            // Create default permissions and get managed entities
            List<PermissionDTO> permissions = createDefaultPermissions();
            System.out.println("Default permissions created: " + permissions.size());

            // Create default role with permissions
            UserRoleDTO adminRoleDTO = createDefaultRole(permissions);
            System.out.println("Default role created with ID: " + adminRoleDTO.getId());

            // Create admin user with the default role
            UserDTO adminUserDTO = new UserDTO();
            adminUserDTO.setUsername("adminSystem");
            adminUserDTO.setPassword(passwordEncoder.encode("0000"));
            adminUserDTO.setEmail("yahyasaadaoui2019@gmail.com");
            adminUserDTO.setStatus("Active");

            // Assign role to user
            adminUserDTO.setUserRoles(Collections.singletonList(adminRoleDTO));

            // Save the admin user
            UserDTO createdUser = adminDashboardService.createUser(adminUserDTO);
            System.out.println("Admin user created with ID: " + createdUser.getId());
        } else {
            System.out.println("Admin user already exists.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        // Ensure that the DTO is validated
        if (userDTO.getUsername() == null || userDTO.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }

        UserDTO createdUser = adminDashboardService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id); // Ensure ID is set in DTO
        UserDTO updatedUser = adminDashboardService.updateUser(id, userDTO); // Pass ID and DTO to the service
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        adminDashboardService.deleteUser(id);
        return ResponseEntity.ok("User deleted event published");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            user authenticatedUser = (user) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(authenticatedUser);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<user> getUser(@PathVariable long id) {
        user user = adminDashboardService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<user> getUserByUsername(@PathVariable String username) {
        user userEntity = adminDashboardService.getUserByUsername(username);
        if (userEntity != null) {
            return ResponseEntity.ok(userEntity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<user>> getAllUsers() {
        Iterable<user> users = adminDashboardService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // CDF TYPES CRUD
    @PostMapping("/cdfTypes")
    public ResponseEntity<CdfTypes> createCdfType(@RequestBody CdfTypes cdfTypes) {
        return ResponseEntity.ok(adminDashboardService.createCdfType(cdfTypes));
    }

    @GetMapping("/cdfTypes/{id}")
    public ResponseEntity<CdfTypes> getCdfTypeById(@PathVariable long id) {
        return ResponseEntity.ok(adminDashboardService.getCdfTypeById(id));
    }

    @GetMapping("/cdfTypes")
    public ResponseEntity<List<CdfTypes>> getAllCdfTypes() {
        return ResponseEntity.ok(adminDashboardService.getAllCdfTypes());
    }

    @PutMapping("/cdfTypes/{id}")
    public ResponseEntity<CdfTypes> updateCdfType(@PathVariable long id, @RequestBody CdfTypes cdfTypes) {
        return ResponseEntity.ok(adminDashboardService.updateCdfType(id, cdfTypes));
    }

    @DeleteMapping("/cdfTypes/{id}")
    public ResponseEntity<Void> deleteCdfType(@PathVariable long id) {
        adminDashboardService.deleteCdfType(id);
        return ResponseEntity.ok().build();
    }

    // CURRENCY CRUD
    @PostMapping("/currency")
    public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency) {
        return ResponseEntity.ok(adminDashboardService.createCurrency(currency));
    }

    @GetMapping("/currency/{id}")
    public ResponseEntity<Currency> getCurrencyById(@PathVariable long id) {
        return ResponseEntity.ok(adminDashboardService.getCurrencyById(id));
    }

    @GetMapping("/currency")
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        return ResponseEntity.ok(adminDashboardService.getAllCurrencies());
    }

    @PutMapping("/currency/{id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable long id, @RequestBody Currency currency) {
        return ResponseEntity.ok(adminDashboardService.updateCurrency(id, currency));
    }

    @DeleteMapping("/currency/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable long id) {
        adminDashboardService.deleteCurrency(id);
        return ResponseEntity.ok().build();
    }

    // FeePercentage CRUD
    @PostMapping("/feePercentage")
    public ResponseEntity<FeePercentage> createFeePercentage(@RequestBody FeePercentage feePercentage) {
        return ResponseEntity.ok(adminDashboardService.createFeePercentage(feePercentage));
    }

    @GetMapping("/feePercentage/{id}")
    public ResponseEntity<FeePercentage> getFeePercentageById(@PathVariable long id) {
        return ResponseEntity.ok(adminDashboardService.getFeePercentageById(id));
    }

    @GetMapping("/feePercentage")
    public ResponseEntity<List<FeePercentage>> getAllFeePercentage() {
        return ResponseEntity.ok(adminDashboardService.getAllFeePercentages());
    }

    @PutMapping("/feePercentage/{id}")
    public ResponseEntity<FeePercentage> updateFeePercentage(@PathVariable long id, @RequestBody FeePercentage feePercentage) {
        return ResponseEntity.ok(adminDashboardService.updateFeePercentage(id, feePercentage));
    }

    @DeleteMapping("/feePercentage/{id}")
    public ResponseEntity<Void> deleteFeePercentage(@PathVariable long id) {
        adminDashboardService.deleteFeePercentage(id);
        return ResponseEntity.ok().build();
    }

    // ROLE CRUD
    @PostMapping("/role")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        return ResponseEntity.ok(adminDashboardService.createRole(role));
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable long id) {
        return ResponseEntity.ok(adminDashboardService.getRoleById(id));
    }

    @GetMapping("/role")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(adminDashboardService.getAllRoles());
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable long id, @RequestBody Role role) {
        return ResponseEntity.ok(adminDashboardService.updateRole(id, role));
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable long id) {
        adminDashboardService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    // PERMISSION CRUD
    @PostMapping("/permission")
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        return ResponseEntity.ok(adminDashboardService.createPermission(permission));
    }

    @GetMapping("/permission/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable long id) {
        return ResponseEntity.ok(adminDashboardService.getPermissionById(id));
    }

    @GetMapping("/permission")
    public ResponseEntity<List<Permission>> getAllPermissions() {
        return ResponseEntity.ok(adminDashboardService.getAllPermissions());
    }

    @PutMapping("/permission/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable long id, @RequestBody Permission permission) {
        return ResponseEntity.ok(adminDashboardService.updatePermission(id, permission));
    }

    @DeleteMapping("/permission/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable long id) {
        adminDashboardService.deletePermission(id);
        return ResponseEntity.ok().build();
    }


    // User Pic Upload
    @Autowired
    private S3Service s3Service;

    @Autowired
    private UserPicService userPicService;

    @PostMapping("/{id}/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = s3Service.uploadFile(file);
            userPicService.updateUserProfileImage(id, imageUrl);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }
}
