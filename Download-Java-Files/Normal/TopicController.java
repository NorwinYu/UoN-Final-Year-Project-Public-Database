package controller;

import service.database.DAO.IDAO.IArticleDAO;
import service.database.DAO.implDAO.ArticleDAO;
import service.implService.DropdownService;
import service.IService.IDropdownService;
import service.IService.ITopicService;
import service.implService.TopicService;
import model.DTO.TopicDTO;
import model.beans.DropdownBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class TopicController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ITopicService topicService = new TopicService();

        String tagId = req.getParameter("tagId");

        String search_field = req.getParameter("search_field");

        String pageNumStr = req.getParameter("pageNum");


        TopicDTO topicDTO = topicService.getTopics(tagId, search_field, pageNumStr);

        req.setAttribute("topicDTO", topicDTO);

        HttpSession session = req.getSession();
        IDropdownService dropdown = new DropdownService();
        List<DropdownBean> tagList = (List<DropdownBean>) session.getAttribute("tagList");
        if (tagList == null || tagList.isEmpty()) {
            tagList = dropdown.getDropdown();
            session.setAttribute("tagList", tagList);
        }

//        IArticleDAO articleDAO = new ArticleDAO();
//        articleDAO.updateArticle(14, "IntroductionHtml", "ddd");


        RequestDispatcher rd = req.getRequestDispatcher("jsp/initialPage.jsp");
        rd.forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req,resp);

    }

}


