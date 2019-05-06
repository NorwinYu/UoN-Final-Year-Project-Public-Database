package service.database.DAO.implDAO;

import model.DTO.ArticleDTO;
import model.beans.*;
import service.database.DAO.IDAO.IArticleDAO;
import service.database.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO implements IArticleDAO {

    @Override
    public ArticleDTO getArticle(int articleId) {

        List<ArticleBean> examples = new ArrayList<>();

        String title = "";
        String introductionHtml = "";
        String remarksHtml = "";
        String parametersHtml = "";
        String syntaxHtml = "";
        String tabName = "";

        Connection con = DBconnection.getConnection();
        if (con != null) {
            try {

                Statement statement = con.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.

                String sql_title = "SELECT Title, SyntaxHtml, RemarksHtml, ParametersHtml, introductionHtml, DocTagId FROM Topics WHERE Id = ?";
                PreparedStatement ps;
                ps = con.prepareStatement(sql_title);
                ps.setInt(1, articleId);
                ResultSet rs;
                rs = ps.executeQuery();

                if (rs.next()) {
                    title = rs.getString("Title");
                    introductionHtml = rs.getString("introductionHtml");
                    remarksHtml = rs.getString("RemarksHtml");
                    parametersHtml = rs.getString("ParametersHtml");
                    syntaxHtml = rs.getString("SyntaxHtml");
                    tabName = rs.getString("DocTagId");
                }

                String sql_tagId = "SELECT Title FROM DocTags where Id = ?";
                ps = con.prepareStatement(sql_tagId);
                ps.setString(1, tabName);
                rs = ps.executeQuery();

                if (rs.next()) {
                    tabName = rs.getString("Title");
                }

                String sql_example = "SELECT Title, BodyHtml FROM Examples WHERE DocTopicId = ? ORDER BY Id";

                ps = con.prepareStatement(sql_example);
                ps.setInt(1, articleId);
                rs = ps.executeQuery();

                while (rs.next()) {
                    examples.add(new ArticleBean(rs.getString("BodyHtml"), rs.getString("Title")));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBconnection.closeConnection(con);
            }
        }
        return new ArticleDTO(examples, articleId, title, introductionHtml, remarksHtml, parametersHtml, syntaxHtml, tabName);
    }

    @Override
    public int updateArticle(String articleId, String field, String updText) {

        int result = 0;

        Connection con = DBconnection.getConnection();

        if (con != null) try {

            Statement statement = con.createStatement();
            statement.setQueryTimeout(30);


            String updateArticle = "UPDATE Topics SET " + field + "= ? WHERE Id = ?";

            PreparedStatement ps;
            ps = con.prepareStatement(updateArticle);
            ps.setString(1, updText);
            ps.setString(2, articleId);

            result = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
