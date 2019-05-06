package service.database.DAO.implDAO;

import model.DTO.ErrorDTO;
import model.DTO.SearchDTO;
import model.DTO.TopicDTO;
import model.beans.TopicBean;
import service.database.DAO.IDAO.ITopicDAO;
import service.database.DBconnection;
import utils.constants.ErrorType;
import utils.properties.ConfigurationProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicDAO implements ITopicDAO {

    public TopicDTO getTopicsFromDB(SearchDTO searchDTO) {
        List<TopicBean> topics = new ArrayList<>();
        String tagId = searchDTO.getTagId();
        String searchInput = searchDTO.getSearchInput();
        int pageNumber = searchDTO.getPageNumb();
        int numbOfTitles = ConfigurationProperties.SHOW_NUMB_OF_TITLES;
        int numbOfRecords = 0;

        searchDTO.setPsNextIndex(0); //del apsaugos sau, kad neturime dar indexo

        Connection con = DBconnection.getConnection();
        if (con == null){
            String message = "No connection to database. Please check.";
            ErrorDTO errorDTO = new ErrorDTO(ErrorType.NO_CONNECTION_TO_DATABASE, message);
            return new TopicDTO("", "", errorDTO);
        }else {
            try {

                Statement statement = con.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                String sqlWhere = createWhereSQL(searchDTO);
                int paramIndex;

                String sql = "SELECT count(*) FROM Topics";
                sql = sql + sqlWhere;

                PreparedStatement psCount;
                psCount = con.prepareStatement(sql);
                fillParamsToSQL(searchDTO, psCount);

                ResultSet rsCount = psCount.executeQuery();
                rsCount.next();
                numbOfRecords = rsCount.getInt(1);

                sql = "SELECT Id, Title FROM Topics";
                sql = sql + sqlWhere;
                sql = sql + " ORDER BY ViewCount DESC LIMIT ?, ?";

                PreparedStatement ps;
                ps = con.prepareStatement(sql);
                fillParamsToSQL(searchDTO, ps);
                paramIndex = searchDTO.getPsNextIndex();

                ps.setInt(paramIndex, ((pageNumber - 1) * numbOfTitles));
                paramIndex++;
                ps.setInt(paramIndex, numbOfTitles);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    TopicBean topic = new TopicBean();
                    topic.setId(rs.getInt("Id"));
                    topic.setTitle(rs.getString("Title"));
                    topics.add(topic);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBconnection.closeConnection(con);
            }

            return new TopicDTO(topics, tagId, searchInput, pageNumber, numbOfRecords);
        }


    }

    public String createWhereSQL(SearchDTO searchDTO) {
        String sql = "";

        String tagId = searchDTO.getTagId();
        String searchInput = searchDTO.getSearchInput();

        boolean isWhere = false;

        if (!tagId.equals("")) {
            sql = sql + " WHERE ";
            isWhere = true;

            sql = sql + "DocTagId = ?";
        }

        List<String> searchWords = new ArrayList<>();
        String word;
        String[] Words = searchInput.split(" ");
        for (int i = 0; i < Words.length; i++) {
            word = Words[i];
            if (!word.equals("")) {
                searchWords.add(word);
            }
        }

        searchDTO.setSearchWords(searchWords);

        for (String w : searchWords) {
            if (!isWhere) {
                sql = sql + " WHERE ";
                isWhere = true;
            } else {
                sql = sql + " AND ";
            }
            sql = sql + "Title LIKE '%'||?||'%'";
        }
        return sql;
    }

    public void fillParamsToSQL(SearchDTO searchDTO, PreparedStatement ps) throws SQLException {
        String tagId = searchDTO.getTagId();

        List<String> searchWords = searchDTO.getSearchWords();

        int index = 1;

        if (!tagId.equals("")) {
            ps.setInt(index, Integer.parseInt(tagId));
            index++;
        }

        for (String word : searchWords) {
            ps.setString(index, word);
            index++;
        }

        searchDTO.setPsNextIndex(index);
    }

}
