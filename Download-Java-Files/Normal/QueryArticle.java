package nwoolcan.model.brewery.warehouse.article;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * QueryArticle.
 */
public final class QueryArticle {

    /**
     * Enum which denotes the parameter on which sort the query.
     */
    public enum SortParameter {
        /**
         * No order.
         */
        NONE,
        /**
         * Sort accordingly with the id.
         */
        ID,
        /**
         * Sort accordingly with the name.
         */
        NAME
    }
    @Nullable private final String minName;
    @Nullable private final String maxName;
    @Nullable private final ArticleType includeArticleType;
    @Nullable private final ArticleType excludeArticleType;
    private final SortParameter sortParameter;
    private final Boolean sortDescending;

    // Package private
    QueryArticle(@Nullable final String minName,
                 @Nullable final String maxName,
                 @Nullable final ArticleType includeArticleType,
                 @Nullable final ArticleType excludeArticleType,
                 final SortParameter sortParameter,
                 final Boolean sortDescending) {
        this.minName = minName;
        this.maxName = maxName;
        this.includeArticleType = includeArticleType;
        this.excludeArticleType = excludeArticleType;
        this.sortParameter = sortParameter;
        this.sortDescending = sortDescending;
    }
    /**
     * Getter of minName.
     * @return minName.
     */
    public Optional<String> getMinName() {
        return Optional.ofNullable(minName);
    }
    /**
     * Getter of maxName.
     * @return maxName.
     */
    public Optional<String> getMaxName() {
        return Optional.ofNullable(maxName);
    }
    /**
     * Getter of the only {@link ArticleType} to be included in the elements of the query.
     * @return the only {@link ArticleType} to be included in the elements of the query.
     */
    public Optional<ArticleType> getIncludeArticleType() {
        return Optional.ofNullable(includeArticleType);
    }
    /**
     * Getter of the only {@link ArticleType} to be excluded in the elements of the query.
     * @return the only {@link ArticleType} to be excluded in the elements of the query.
     */
    public Optional<ArticleType> getExcludeArticleType() {
        return Optional.ofNullable(excludeArticleType);
    }
    /**
     * Getter of the parameter on which the elements of the query will be sorted.
     * @return the parameter on which the elements of the query will be sorted.
     */
    public SortParameter getSortBy() {
        return sortParameter;
    }
    /**
     * Returns a boolean denoting whether the sort will be descending.
     * @return a boolean denoting whether the sort will be descending.
     */
    public boolean getSortDescending() {
        return sortDescending;
    }

}
