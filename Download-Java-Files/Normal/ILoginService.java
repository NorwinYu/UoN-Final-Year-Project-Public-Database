package service.IService;

import model.DTO.UserDTO;

public interface ILoginService {

    UserDTO checkUser(String name, String password);

}
