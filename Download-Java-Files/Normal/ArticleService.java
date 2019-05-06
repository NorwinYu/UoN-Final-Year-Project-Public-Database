package service.implService;

import model.DTO.ArticleDTO;
import model.DTO.ErrorDTO;
import service.database.DAO.IDAO.IArticleDAO;
import service.database.DAO.implDAO.ArticleDAO;
import service.IService.IArticleService;
import utils.constants.ErrorType;

public class ArticleService implements IArticleService {

    @Override
    public ArticleDTO getArticle(String id) {

        int articleId;
        if (id == null || id.isEmpty()) {
            articleId = 0;
        } else {
            articleId = (Integer.parseInt(id));
        }

        IArticleDAO articleDAO = new ArticleDAO();
        return articleDAO.getArticle(articleId);

    }

    @Override
    public ArticleDTO updateArticle(String id, String field, String updText) {

        ArticleDTO articleDTO = new ArticleDTO();
        IArticleDAO articleDAO = new ArticleDAO();
        int result = articleDAO.updateArticle(id, field, updText);


        if (result == 0){
            String errMessage = "No field " + field + "found";
            ErrorDTO errorDTO = new ErrorDTO(ErrorType.NO_FIELD_FOUND_TO_CHANGE, errMessage);
            articleDTO.setErrorDTO(errorDTO);


        } else {
            articleDTO = articleDAO.getArticle(Integer.parseInt(id));
        }
        return articleDTO;
    }

}
