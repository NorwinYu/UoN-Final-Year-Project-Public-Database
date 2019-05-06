package model.DTO;

import model.beans.ArticleBean;

import java.util.List;

public class ArticleDTO {
    private List<ArticleBean> articleList;
    private int id;
    private String title;
    private String tabName;

    private String introductionHtml;
    private String remarksHtml;
    private String parametersHtml;
    private String syntaxHtml;

    private ErrorDTO errorDTO;

    public ArticleDTO() {
    }


    public ArticleDTO(List<ArticleBean> articleList, int id, String title, String introductionHtml, String remarksHtml, String parametersHtml, String syntaxHtml, String tabName) {
        this.articleList = articleList;
        this.id = id;
        this.title = title;
        this.introductionHtml = introductionHtml;
        this.remarksHtml = remarksHtml;
        this.parametersHtml = parametersHtml;
        this.syntaxHtml = syntaxHtml;
        this.tabName = tabName;
    }


    public List<ArticleBean> getArticleList() {
        return articleList;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIntroductionHtml() {
        return introductionHtml;
    }

    public String getRemarksHtml() {
        return remarksHtml;
    }

    public String getParametersHtml() {
        return parametersHtml;
    }

    public String getSyntaxHtml() {
        return syntaxHtml;
    }

    public String getTabName() { return tabName; }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
