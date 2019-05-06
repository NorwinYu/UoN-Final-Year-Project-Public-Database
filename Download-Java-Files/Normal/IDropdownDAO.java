package service.database.DAO.IDAO;

import model.beans.DropdownBean;
import utils.properties.ConfigurationProperties;

import java.util.List;

public interface IDropdownDAO {

    int numbOfTags = ConfigurationProperties.SHOW_NUMB_OF_TAGS;
    List<DropdownBean> getLimitedResult();
}
