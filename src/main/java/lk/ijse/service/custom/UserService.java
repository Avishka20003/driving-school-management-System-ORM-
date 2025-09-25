package lk.ijse.service.custom;


import lk.ijse.dto.UserDTO;
import lk.ijse.service.SuperService;

import java.util.List;
import java.util.Optional;

public interface UserService extends SuperService {
    List<UserDTO> getAllUsers() throws Exception;

    String getLastUserId() throws Exception;

    boolean saveUsers(UserDTO t) throws Exception;

    boolean updateUsers(UserDTO t) throws Exception;

    boolean deleteUsers(String id) throws Exception;

    List<String> getAllUserIds() throws Exception;

    Optional<UserDTO> findByUserId(String id) throws Exception;

    String generateNextUserId();

    public UserDTO getUserByEmail(String email) ;
}
