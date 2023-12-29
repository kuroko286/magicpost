package com.magicpostapi.controllers;

import com.magicpostapi.enums.Role;
import com.magicpostapi.dto.ResponseObject;
import com.magicpostapi.models.TransactionPoint;
import com.magicpostapi.models.User;
import com.magicpostapi.services.TransactionPointService;
import com.magicpostapi.services.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/transaction-point")
@CrossOrigin("*")
public class TransactionPointController {
    @Autowired
    TransactionPointService transactionPointService;
    @Autowired
    UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    ResponseEntity<List<TransactionPoint>> getAllTransactionPoint() {
        return ResponseEntity.ok(transactionPointService.findAllTransactionPoint());
    }

    // @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    ResponseEntity<ResponseObject> newTransactionPoint(@RequestBody Map<String, String> reqBody) {
        class TransactionPointWithAccount {
            @JsonProperty("transactionPoint")
            final TransactionPoint transactionPoint;
            @JsonProperty("user")
            final User user;

            public TransactionPointWithAccount(TransactionPoint transactionPoint, User user) {
                this.transactionPoint = transactionPoint;
                this.user = user;
            }
        }

        if (transactionPointService.isValidGatheringId(reqBody.get("idBranch"))) {
            TransactionPoint newTransactionPoint = transactionPointService.newTransactionPoint(
                    reqBody.get("name"),
                    reqBody.get("address"),
                    reqBody.get("city"),
                    reqBody.get("idBranch"));
            User newUser = transactionPointService.newAccountWithTransactionId(
                    Role.TRANSACTION_POINT_MANAGER,
                    newTransactionPoint.getId());
            return new ResponseEntity<>(new ResponseObject(
                    "200",
                    "Create successful",
                    new TransactionPointWithAccount(newTransactionPoint, newUser)), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getTransactionInfo(@PathVariable @NonNull String id) {

        class TransactionInfo {
            @JsonProperty("transactionPoint")
            private final TransactionPoint transactionPoint;
            @JsonProperty("users")
            private final List<User> users;

            public TransactionInfo(TransactionPoint transactionPoint, List<User> users) {
                this.transactionPoint = transactionPoint;
                this.users = users;
            }
        }

        if (transactionPointService.checkOfficeExist(id)) {
            return new ResponseEntity<>(new ResponseObject(
                    "200",
                    "Find successful",
                    new TransactionInfo(transactionPointService.findById(id), userService.getAllUserByIdBranch(id))),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseObject(
                    "400",
                    "No data found"), HttpStatus.BAD_REQUEST);
        }
    }
}
