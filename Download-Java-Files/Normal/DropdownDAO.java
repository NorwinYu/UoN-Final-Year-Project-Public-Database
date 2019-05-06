package service.database.DAO.implDAO;

import model.beans.DropdownBean;
import service.database.DAO.IDAO.IDropdownDAO;
import service.database.DBconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DropdownDAO implements IDropdownDAO {

    public List<DropdownBean> getLimitedResult() {

        List<DropdownBean> tagList = new ArrayList<>();

        Connection con = DBconnection.getConnection();
        if (con != null) try {
            Statement statement = con.createStatement();

            String searchQuery = "select Id, Tag from DocTags order by TopicCount desc limit " + numbOfTags;
            ResultSet rs = statement.executeQuery(searchQuery);

            while (rs.next()) {
                DropdownBean dropdownBean = new DropdownBean();

                dropdownBean.setId(rs.getString("Id"));
                dropdownBean.setTag(rs.getString("Tag"));

                tagList.add(dropdownBean);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBconnection.closeConnection(con);
        }

        return tagList;
    }
}
