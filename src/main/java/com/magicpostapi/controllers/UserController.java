package com.magicpostapi.controllers;

import com.magicpostapi.enums.Role;
import com.magicpostapi.dto.ResponseObject;
import com.magicpostapi.models.User;
import com.magicpostapi.repositories.UserRepository;
import com.magicpostapi.services.GatheringPointService;
import com.magicpostapi.services.TransactionPointService;
import com.magicpostapi.services.UserService;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionPointService transactionPointService;
    @Autowired
    GatheringPointService gatheringPointService;
    @Autowired
    UserService userService;

    // Get all account (only highest manager can access here)
    // @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    ResponseEntity<List<User>> getAllUser() {
        List<User> accountsFound = userRepository.findAll();
        return ResponseEntity.ok(accountsFound);
    }

    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable @NonNull Long id) {
        User accountFound = userService.getUserById(id);
        return ResponseEntity.ok(accountFound);
    }

    // @PreAuthorize("hasAnyAuthority('TRANSACTION_POINT_MANAGER', 'ADMIN',
    // 'GATHERING_POINT_MANAGER')")
    @GetMapping("/offices/{idOffice}")
    ResponseEntity<List<User>> getAllOfficePointUser(@PathVariable @NonNull String idOffice) {
        if (idOffice.startsWith("TSP")) {
            if (!transactionPointService.checkOfficeExist(idOffice))
                throw new IllegalArgumentException(
                        "Workplace with id: " + idOffice + " does not exist!");
        } else if (idOffice.startsWith("GRP")) {
            if (!gatheringPointService.checkOfficeExist(idOffice))
                throw new IllegalArgumentException(
                        "Workplace with id: " + idOffice + " does not exist!");
        } else {
            throw new IllegalArgumentException("Office with id: " + idOffice + " does not exist!");
        }
        List<User> accountsFound = userService.getAllUserByIdBranch(idOffice);
        return ResponseEntity.ok(accountsFound);
    }

    // @PreAuthorize("hasAnyAuthority('ADMIN', 'GATHERING_POINT_MANAGER',
    // 'TRANSACTION_POINT_MANAGER')")
    @PostMapping("")
    ResponseEntity<User> createUser(@RequestBody Map<String, String> reqBody) {
        User user = userService.generatedUser(reqBody.get("email"),
                reqBody.get("password"),
                Role.valueOf(reqBody.get("role")),
                reqBody.get("location"));
        user.setPhone(reqBody.get("phone"));
        user.setUsername(reqBody.get("name"));
        User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/staffs/transaction")
    ResponseEntity<User> createStaffAccountWithTransactionId(@RequestBody Map<String, String> reqBody) {
        Long countStaff = userService.countStaffFromTransactionPoint(reqBody.get("location"));
        User user = userService.generatedUser(
                reqBody.get("location") + "_ST" + (countStaff + 1) + "@magicpost.com",
                "123456",
                Role.TRANSACTION_POINT_STAFF,
                reqBody.get("location"),
                reqBody.get("name"),
                reqBody.get("phone"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> editUser(@RequestBody Map<String, String> reqBody, @PathVariable Long id) {
        return new ResponseEntity<>(new ResponseObject(
                "ok",
                "Update successful",
                userService.editUser(id, reqBody)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'GATHERING_POINT_MANAGER', 'TRANSACTION_POINT_MANAGER')")
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new ResponseObject("200", "Delete success"), HttpStatus.OK);
    }

}
