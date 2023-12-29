package com.magicpostapi.services;

import com.magicpostapi.components.Genarator;
import com.magicpostapi.enums.Role;
import com.magicpostapi.models.User;
import com.magicpostapi.repositories.GatheringPointRepository;
import com.magicpostapi.repositories.TransactionPointRepository;
import com.magicpostapi.repositories.UserRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Genarator genarator;
    @Autowired
    private TransactionPointRepository transactionPointRepository;
    @Autowired
    private GatheringPointRepository gatheringPointRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final int defaultPasswordLength = 10;

    public User generatedUser(String email, String password, @Nonnull Role role, String idBranch) {
        User newUser = new User();

        if (password.length() == 0)
            password = genarator.genaratedString(defaultPasswordLength);

        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(role);
        if (idBranch != null) {
            if (idBranch.startsWith("GRP")) {
                newUser.setGatheringPoint(gatheringPointRepository.findById(idBranch).orElseThrow(
                        () -> new IllegalArgumentException("Gathering Point not found")));
                newUser.setTransactionPoint(null);
            } else if (idBranch.startsWith("TSP")) {
                newUser.setTransactionPoint(transactionPointRepository.findById(idBranch).orElseThrow(
                        () -> new IllegalArgumentException("Transaction Point not found")));
                newUser.setGatheringPoint(null);
            }
        }

        userRepository.save(newUser);
        newUser.setPassword(password);
        return newUser;
    }

    public User generatedUser(String email, String password, @Nonnull Role role, String idBranch, String name,
            String phone) {
        User newUser = new User();

        if (password.length() == 0)
            password = genarator.genaratedString(defaultPasswordLength);

        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(role);
        newUser.setName(name);
        newUser.setPhone(phone);
        if (idBranch != null) {
            if (idBranch.startsWith("GRP")) {
                newUser.setGatheringPoint(gatheringPointRepository.findById(idBranch).orElseThrow(
                        () -> new IllegalArgumentException("Gathering Point not found")));
                newUser.setTransactionPoint(null);
            } else if (idBranch.startsWith("TSP")) {
                newUser.setTransactionPoint(transactionPointRepository.findById(idBranch).orElseThrow(
                        () -> new IllegalArgumentException("Transaction Point not found")));
                newUser.setGatheringPoint(null);
            }
        }

        userRepository.save(newUser);
        newUser.setPassword(password);
        return newUser;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found"));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUserByIdBranch(String id) {
        if (id.startsWith("GRP"))
            return userRepository.findByGatheringPoint_Id(id);
        else if (id.startsWith("TSP"))
            return userRepository.findByTransactionPoint_Id(id);
        else
            return null;
    }

    public User editUser(Long id, Map<String, String> details) {
        User updateUser = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found"));
        if (details.get("username") != null)
            updateUser.setUsername(details.get("username"));
        if (details.get("password") != null)
            updateUser.setPassword(passwordEncoder.encode(details.get("password")));
        return userRepository.save(updateUser);
    }

    public Long countStaffFromTransactionPoint(String id) {

        return userRepository.findByTransactionPoint_Id(id).stream()
                .filter(user -> user.getRole() == Role.TRANSACTION_POINT_STAFF.toString()).count();

    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
