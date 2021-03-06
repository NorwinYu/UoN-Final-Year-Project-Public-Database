package nwoolcan.model.brewery.warehouse;

import nwoolcan.model.brewery.batch.Batch;
import nwoolcan.model.brewery.warehouse.article.Article;
import nwoolcan.model.brewery.warehouse.article.BeerArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientArticle;
import nwoolcan.model.brewery.warehouse.article.IngredientType;
import nwoolcan.model.brewery.warehouse.article.QueryArticle;
import nwoolcan.model.brewery.warehouse.stock.BeerStock;
import nwoolcan.model.brewery.warehouse.stock.QueryStock;
import nwoolcan.model.brewery.warehouse.stock.Stock;
import nwoolcan.model.utils.UnitOfMeasure;
import nwoolcan.utils.Result;

import java.util.Date;
import java.util.List;

/**
 * Warehouse.
 */
public interface Warehouse {

    /**
     * Getter of the {@link Stock} in the warehouse, filtered with a {@link QueryStock}.
     * @param queryStock describes the nature of the query.
     * @return a {@link List} of {@link Stock} which respects the given {@link QueryStock}.
     */
    List<Stock> getStocks(QueryStock queryStock);
    /**
     * Getter of the {@link Article} in the warehouse, filtered with a {@link QueryArticle}.
     * @param queryArticle describes the nature of the query.
     * @return a {@link List} of {@link Article} which respects the given {@link QueryArticle}.
     */
    List<Article> getArticles(QueryArticle queryArticle);
    /**
     * If it does not exist, creates a misc {@link Article}, otherwise it will be returned.
     * @param name of the {@link Article}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link Article}.
     * @return a new {@link Article} if it does not exist, otherwise the existing one.
     */
    Article createMiscArticle(String name, UnitOfMeasure unitOfMeasure);
    /**
     * If it does not exist, creates a {@link BeerArticle}, otherwise it will be returned.
     * @param name of the {@link BeerArticle}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link BeerArticle}.
     * @return a new {@link BeerArticle} if it does not exist, otherwise the existing one.
     */
    BeerArticle createBeerArticle(String name, UnitOfMeasure unitOfMeasure);
    /**
     * If it does not exist, creates a {@link IngredientArticle}, otherwise it will be returned.
     * @param name of the {@link IngredientArticle}.
     * @param unitOfMeasure the {@link UnitOfMeasure} of the {@link IngredientArticle}.
     * @param ingredientType the {@link IngredientType} of the {@link IngredientArticle}.
     * @return a new {@link IngredientArticle} if it does not exist, otherwise the existing one.
     */
    IngredientArticle createIngredientArticle(String name,
                                              UnitOfMeasure unitOfMeasure,
                                              IngredientType ingredientType);
    /**
     * Constructor of the {@link Stock}.
     * @param article linked to the {@link Stock}.
     * @param expirationDate linked to the {@link Stock}.
     * @return a {@link Result} indicating errors.
     */
    Result<Stock> createStock(Article article, Date expirationDate);
    /**
     * Constructor of the {@link Stock}.
     * @param article linked to the {@link Stock}.
     * @return a {@link Result} indicating errors.
     */
    Result<Stock> createStock(Article article);
    /**
     * Constructor of the {@link BeerStock}.
     * @param beerArticle linked to this {@link BeerStock}.
     * @param expirationDate linked to this {@link BeerStock}.
     * @param batch linked to this {@link BeerStock}.
     * @return a {@link Result} indicating errors.
     */
    Result<BeerStock> createBeerStock(BeerArticle beerArticle,
                                      Date expirationDate,
                                      Batch batch);
    /**
     * Constructor of the {@link BeerStock}.
     * @param beerArticle linked to this {@link BeerStock}.
     * @param batch linked to this {@link BeerStock}.
     * @return a {@link Result} indicating errors.
     */
    Result<BeerStock> createBeerStock(BeerArticle beerArticle,
                                      Batch batch);
    /**
     * Setter for the name of the {@link Article}.
     * @param article the {@link Article} to which we want to change the name.
     * @param newName the new name to be assigned to the {@link Article}.
     * @return a {@link Result} of {@link Article} for fluency.
     */
    Result<Article> setName(Article article, String newName);
    /**
     * Getter of a single {@link Article} based on its id.
     * @param id to search the {@link Article}.
     * @return the {@link Article} requested or an error if it does not exist.
     */
    Result<Article> getArticleById(int id);
    /**
     * Getter of a single {@link Stock} based on its id.
     * @param id to search the {@link Stock}.
     * @return the {@link Stock} requested or an error if it does not exist.
     */
    Result<Stock> getStockById(int id);
    /**
     * Getter of a single {@link BeerStock} based on its id.
     * @param id to search the {@link BeerStock}.
     * @return the {@link BeerStock} requested or an error if it does not exist.
     */
    Result<BeerStock> getBeerStockById(int id);

}
