package controller;

import model.DTO.ArticleDTO;
import model.DTO.TopicDTO;
import model.beans.DropdownBean;
import service.IService.IArticleService;
import service.IService.IDropdownService;
import service.implService.ArticleService;
import service.implService.DropdownService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ArticleController extends HttpServlet {

    IArticleService articleService = new ArticleService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {

        String id = req.getParameter("id");

        ArticleDTO articleDTO = articleService.getArticle(id);

        loadArticle(req, resp, articleDTO);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {

        String articleId = req.getParameter("id");
        String field = req.getParameter("field");
        String updText = req.getParameter("updText");

        ArticleDTO articleDTO = articleService.updateArticle(articleId, field, updText);

        loadArticle(req, resp, articleDTO);

    }

    private void loadArticle (HttpServletRequest req, HttpServletResponse resp, ArticleDTO articleDTO){

        HttpSession session = req.getSession();
        IDropdownService dropdown = new DropdownService();
        List<DropdownBean> tagList = (List<DropdownBean>) session.getAttribute("tagList");
        if (tagList == null || tagList.isEmpty()) {
            tagList = dropdown.getDropdown();
            session.setAttribute("tagList", tagList);
        }

        req.setAttribute("articleDTO", articleDTO);
        TopicDTO topicDTO = new TopicDTO("","");
        req.setAttribute("topicDTO", topicDTO);

        try {
            req.getRequestDispatcher("jsp/article.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
