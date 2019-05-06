package service.database.DAO.IDAO;

import model.DTO.SearchDTO;
import model.DTO.TopicDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ITopicDAO {

    TopicDTO getTopicsFromDB(SearchDTO searchDTO);

    String createWhereSQL(SearchDTO searchDTO);

    void fillParamsToSQL(SearchDTO searchDTO, PreparedStatement ps) throws SQLException;
}
