package service.IService;

import model.DTO.ArticleDTO;

public interface IArticleService {

    ArticleDTO getArticle(String id);

    ArticleDTO  updateArticle(String articleId, String field, String updText);


}
