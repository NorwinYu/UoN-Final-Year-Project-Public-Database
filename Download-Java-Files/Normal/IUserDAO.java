package service.database.DAO.IDAO;

import model.DTO.UserDTO;

public interface IUserDAO {

    void insertUser(UserDTO userDTO);

    UserDTO checkUser(UserDTO userDTO);

}
