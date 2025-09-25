package lk.ijse.dao.custom;


import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;

public interface UserDAO extends CrudDAO<User> {
    public User getUserByEmail(String email);
}
