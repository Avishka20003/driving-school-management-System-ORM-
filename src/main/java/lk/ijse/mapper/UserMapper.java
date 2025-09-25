package lk.ijse.mapper;


import lk.ijse.dto.UserDTO;
import lk.ijse.dto.tm.UserTM;
import lk.ijse.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .userId(user.getUserId().toString())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUserId(Long.parseLong(dto.getUserId()));
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());

        return user;
    }

    public static UserTM toTM(UserDTO dto) {
        if (dto == null) return null;

        UserTM user = new UserTM();
        user.setUserId(dto.getUserId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole().name());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());

        return user;
    }
}
