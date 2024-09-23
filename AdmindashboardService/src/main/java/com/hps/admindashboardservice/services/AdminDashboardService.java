package com.hps.admindashboardservice.services;
import com.hps.admindashboardservice.dto.*;
import com.hps.admindashboardservice.entities.*;
import com.hps.admindashboardservice.entities.Currency;
import com.hps.admindashboardservice.repos.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AdminDashboardService {
    @Autowired
    private userRepo userRepository;

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
    private PasswordEncoder passwordEncoder;


    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent() ||
                userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("User with username " + userDTO.getUsername() + " or email already exists");
        }

        user user = new user();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setImageUrl(userDTO.getImageUrl());
        user.setEmail(userDTO.getEmail());
        user.setStatus(userDTO.getStatus());
        user.setCreated_at(userDTO.getCreated_at());
        user.setUpdated_at(userDTO.getUpdated_at());
        user.setDeleted_at(userDTO.getDeleted_at());
        user.setDeleted_by(userDTO.getDeleted_by());
        user.setCreated_by(userDTO.getCreated_by());
        user.setUpdated_by(userDTO.getUpdated_by());
        user.setLast_login_attempt_time(userDTO.getLast_login_attempt_time());

        // Set roles using UserRole intermediate entity
        if (userDTO.getUserRoles() != null) {
            List<UserRole> userRoles = new ArrayList<>();
            for (UserRoleDTO roleDTO : userDTO.getUserRoles()) {
                Role role = roleRepository.findById(roleDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Role not found"));

                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(role);
                userRoles.add(userRole);
            }
            user.setUserRoles(userRoles);
        }

        // Save the user
        user = userRepository.save(user);

        // Map saved user entity back to UserDTO
        UserDTO savedUserDTO = mapUserToDTO(user);

        return savedUserDTO;
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        Optional<user> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            if (userRepository.findByUsername(userDTO.getUsername()).isPresent() ||
                    userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                throw new RuntimeException("User with username " + userDTO.getUsername() + " or email already exists");
            }
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }

        user user = optionalUser.get();

        // Check if username or email already exists and is not the current user's username/email
        if ((userRepository.findByUsername(userDTO.getUsername()).isPresent() &&
                !userDTO.getUsername().equals(user.getUsername())) ||
                (userRepository.findByEmail(userDTO.getEmail()).isPresent() &&
                        !userDTO.getEmail().equals(user.getEmail()))) {
            throw new RuntimeException("User with username " + userDTO.getUsername() + " or email already exists");
        }

        // Update user details
        user.setUsername(userDTO.getUsername());
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        user.setImageUrl(userDTO.getImageUrl());
        user.setEmail(userDTO.getEmail());
        user.setStatus(userDTO.getStatus());
        user.setCreated_at(userDTO.getCreated_at());
        user.setUpdated_at(userDTO.getUpdated_at());
        user.setDeleted_at(userDTO.getDeleted_at());
        user.setDeleted_by(userDTO.getDeleted_by());
        user.setCreated_by(userDTO.getCreated_by());
        user.setUpdated_by(userDTO.getUpdated_by());
        user.setLast_login_attempt_time(userDTO.getLast_login_attempt_time());

        // Update roles and their permissions through UserRole and RolePermission
        if (userDTO.getUserRoles() != null) {
            List<UserRole> userRoles = new ArrayList<>();
            for (UserRoleDTO roleDTO : userDTO.getUserRoles()) {
                Role role = roleRepository.findById(roleDTO.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleDTO.getId()));

                // Map the UserRole association
                UserRole userRole = new UserRole();
                userRole.setRole(role);
                userRole.setUser(user);

                userRoles.add(userRole);
            }
            user.setUserRoles(userRoles);
        }

        // Save the updated user entity
        user = userRepository.save(user);

        // Map the updated user entity back to UserDTO
        UserDTO updatedUserDTO = mapUserToDTO(user);

        return updatedUserDTO;
    }

    public void deleteUser(long id) {
        user user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    public user getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<user> getAllUsers() {
        return userRepository.findAll();
    }

    public user getUserByUsername(String username) {
        Optional<user> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            user userEntity = userOpt.get();
            return userEntity;
        }

        return null; // or throw an exception if user not found
    }

    private UserDTO mapUserToDTO(user user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setImageUrl(user.getImageUrl());
        dto.setStatus(user.getStatus());
        dto.setCreated_at(user.getCreated_at());
        dto.setUpdated_at(user.getUpdated_at());
        dto.setDeleted_at(user.getDeleted_at());
        dto.setDeleted_by(user.getDeleted_by());
        dto.setCreated_by(user.getCreated_by());
        dto.setUpdated_by(user.getUpdated_by());
        dto.setLast_login_attempt_time(user.getLast_login_attempt_time());

        // Map UserRole to UserRoleDTO
        List<UserRoleDTO> roleDTOs = user.getUserRoles().stream()
                .map(userRole -> mapRoleToDTO(userRole.getRole()))
                .collect(Collectors.toList());
        dto.setUserRoles(roleDTOs);

        return dto;
    }

    private UserRoleDTO mapRoleToDTO(Role role) {
        // Create a new UserRoleDTO object
        UserRoleDTO dto = new UserRoleDTO();
        dto.setId(role.getId());
        dto.setLabel(role.getLabel());

        // Map RolePermission entities to RolePermissionDTOs
        List<RolePermissionDTO> rolePermissionDTOs = role.getRolePermissions().stream()
                .map(rolePermission -> {
                    RolePermissionDTO rolePermissionDTO = new RolePermissionDTO();
                    rolePermissionDTO.setId(rolePermission.getId());
                    rolePermissionDTO.setRoleId(rolePermission.getRole().getId());
                    rolePermissionDTO.setPermissionId(rolePermission.getPermission().getId());
                    return rolePermissionDTO;
                })
                .collect(Collectors.toList());

        // Set the RolePermissionDTOs in the UserRoleDTO
        dto.setRolePermissions(rolePermissionDTOs);

        return dto;
    }

    private PermissionDTO mapPermissionToDTO(Permission permission) {
        PermissionDTO dto = new PermissionDTO(permission.getLabel());
        dto.setId(permission.getId());
        return dto;
    }

    // CDF TYPES SERVICES

    // Create CdfType
    public CdfTypes createCdfType(CdfTypes cdfType) {
        return cdfTypesRepository.save(cdfType);
    }

    // Get CdfType by ID
    public CdfTypes getCdfTypeById(long id) {
        return cdfTypesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CdfType not found"));
    }

    // Get all CdfTypes
    public List<CdfTypes> getAllCdfTypes() {
        return cdfTypesRepository.findAll();
    }

    // Update CdfType
    public CdfTypes updateCdfType(long id, CdfTypes updatedCdfType) {
        CdfTypes existingCdfType = getCdfTypeById(id);
        existingCdfType.setDescription(updatedCdfType.getDescription());
        existingCdfType.setBREAKPOINT_1(updatedCdfType.getBREAKPOINT_1());
        existingCdfType.setBREAKPOINT_2(updatedCdfType.getBREAKPOINT_2());
        existingCdfType.setBREAKPOINT_3(updatedCdfType.getBREAKPOINT_3());
        return cdfTypesRepository.save(existingCdfType);
    }

    // Delete CdfType
    public void deleteCdfType(long id) {
        CdfTypes cdfType = getCdfTypeById(id);
        cdfTypesRepository.delete(cdfType);
    }

    // currency services

    // Create Currency
    public Currency createCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    // Get Currency by ID
    public Currency getCurrencyById(long id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found"));
    }

    // Get all Currencies
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    // Update Currency
    public Currency updateCurrency(long id, Currency updatedCurrency) {
        Currency existingCurrency = getCurrencyById(id);
        existingCurrency.setDescription(updatedCurrency.getDescription());
        return currencyRepository.save(existingCurrency);
    }

    // Delete Currency
    public void deleteCurrency(long id) {
        Currency currency = getCurrencyById(id);
        currencyRepository.delete(currency);
    }

    // FeePercentage services

    // Create FeePercentage
    public FeePercentage createFeePercentage(FeePercentage feePercentage) {
        return feePercentageRepository.save(feePercentage);
    }

    // Get FeePercentage by ID
    public FeePercentage getFeePercentageById(long id) {
        return feePercentageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FeePercentage not found"));
    }

    // Get all FeePercentages
    public List<FeePercentage> getAllFeePercentages() {
        return feePercentageRepository.findAll();
    }

    // Update FeePercentage
    public FeePercentage updateFeePercentage(long id, FeePercentage updatedFeePercentage) {
        FeePercentage existingFeePercentage = getFeePercentageById(id);
        existingFeePercentage.setDescription(updatedFeePercentage.getDescription());
        return feePercentageRepository.save(existingFeePercentage);
    }

    // Delete FeePercentage
    public void deleteFeePercentage(long id) {
        FeePercentage feePercentage = getFeePercentageById(id);
        feePercentageRepository.delete(feePercentage);
    }

    // FeeType services

    // Create FeeType
    public FeeType createFeeType(FeeType feeType) {
        return feeTypeRepository.save(feeType);
    }

    // Get FeeType by ID
    public FeeType getFeeTypeById(long id) {
        return feeTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FeeType not found"));
    }

    // Get all FeeTypes
    public List<FeeType> getAllFeeTypes() {
        return feeTypeRepository.findAll();
    }

    // Update FeeType
    public FeeType updateFeeType(long id, FeeType updatedFeeType) {
        FeeType existingFeeType = getFeeTypeById(id);
        existingFeeType.setDescription(updatedFeeType.getDescription());
        return feeTypeRepository.save(existingFeeType);
    }

    // Delete FeeType
    public void deleteFeeType(long id) {
        FeeType feeType = getFeeTypeById(id);
        feeTypeRepository.delete(feeType);
    }

    // SettlementType services
    // Create SettlementType
    public SettlementType createSettlementType(SettlementType settlementType) {
        return settlementTypeRepository.save(settlementType);
    }

    // Get SettlementType by ID
    public SettlementType getSettlementTypeById(long id) {
        return settlementTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SettlementType not found"));
    }

    // Get all SettlementTypes
    public List<SettlementType> getAllSettlementTypes() {
        return settlementTypeRepository.findAll();
    }

    // Update SettlementType
    public SettlementType updateSettlementType(long id, SettlementType updatedSettlementType) {
        SettlementType existingSettlementType = getSettlementTypeById(id);
        existingSettlementType.setDescription(updatedSettlementType.getDescription());
        return settlementTypeRepository.save(existingSettlementType);
    }

    // Delete SettlementType
    public void deleteSettlementType(long id) {
        SettlementType settlementType = getSettlementTypeById(id);
        settlementTypeRepository.delete(settlementType);
    }

    // Role services

    // Create Role
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    // Get Role by ID
    public Role getRoleById(long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    // Get all Roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Update Role
    public Role updateRole(long id, Role updatedRole) {
        Role existingRole = getRoleById(id);

        // Update the label
        existingRole.setLabel(updatedRole.getLabel());

        // Update the RolePermissions list
        // Clear the existing role permissions and set the new ones
        existingRole.getRolePermissions().clear();

        // Recreate the relationship between Role and RolePermissions
        for (RolePermission rolePermission : updatedRole.getRolePermissions()) {
            rolePermission.setRole(existingRole); // Ensure proper relationship
            existingRole.getRolePermissions().add(rolePermission);
        }

        return roleRepository.save(existingRole);
    }

    // Delete Role
    public void deleteRole(long id) {
        Role role = getRoleById(id);
        roleRepository.delete(role);
    }

    // Permission services
    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    // Get Permission by ID
    public Permission getPermissionById(long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
    }

    // Get all Permissions
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    // Update Permission
    public Permission updatePermission(long id, Permission updatedPermission) {
        Permission existingPermission = getPermissionById(id);
        existingPermission.setLabel(updatedPermission.getLabel());
        return permissionRepository.save(existingPermission);
    }

    // Delete Permission
    public void deletePermission(long id) {
        Permission permission = getPermissionById(id);
        permissionRepository.delete(permission);
    }

}
