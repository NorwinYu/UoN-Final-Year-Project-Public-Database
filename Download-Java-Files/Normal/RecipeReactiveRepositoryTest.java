package guru.springframework.repositories.reactive;

import guru.springframework.domain.Recipe;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RecipeReactiveRepositoryTest {

    RecipeReactiveRepository recipeReactiveRepository;

    public void setUp() throws Exception{
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void testRecipeSave() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setDescription("Yummy");

        recipeReactiveRepository.save(recipe).block();

        Long count = recipeReactiveRepository.count().block();

        assertEquals(Long.valueOf(1L),count);
    }
}
