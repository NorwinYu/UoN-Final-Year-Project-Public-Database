package nwoolcan.model.brewery.warehouse.article;

import nwoolcan.model.utils.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for IngredientArticle.
 */
public class TestIngredientArticle {

    private final ArticleManager articleManager = new ArticleManager();
    private static final UnitOfMeasure UOM = UnitOfMeasure.GRAM;
    private static final String NAME = "DummyName";

    /**
     * Method that tests the getters and their possible errors.
     */
    @Test
    public void testGetters() {
        final IngredientType ingredientType = IngredientType.FERMENTABLE;
        final Article ingredientArticle = articleManager.createIngredientArticle(NAME, UOM, ingredientType);
        Assert.assertEquals(ArticleType.INGREDIENT, ingredientArticle.getArticleType());
        Assert.assertTrue(ingredientArticle.toIngredientArticle().isPresent());
        Assert.assertEquals(IngredientArticleImpl.class, ingredientArticle.toIngredientArticle()
                                                                          .getValue()
                                                                          .getClass());
        Assert.assertEquals(ingredientType, ingredientArticle.toIngredientArticle()
                                                             .getValue()
                                                             .getIngredientType());
    }
    /**
     * Method that tests the equals method.
     */
    @Test
    public void testEquals() {
        final IngredientArticle ingArt1
            = articleManager.createIngredientArticle(NAME, UOM, IngredientType.FERMENTABLE);
        final IngredientArticle ingArt2
            = articleManager.createIngredientArticle(NAME, UOM, IngredientType.FERMENTABLE);
        final IngredientArticle ingArt4
            = articleManager.createIngredientArticle(NAME, UOM, IngredientType.HOPS);
        Assert.assertEquals(ingArt1, ingArt2);
        Assert.assertEquals(ingArt1, ingArt1);
        Assert.assertEquals(ingArt1, ingArt1);
        Assert.assertNotEquals(ingArt1, ingArt4);
        Assert.assertNotEquals(ingArt4, ingArt1);
    }

}
