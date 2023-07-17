package backend.refree.module.Recipe;

import backend.refree.module.Recipe.Recipe;

import java.util.List;

public interface RecipeRepositoryCustom {

    List<Recipe> findByIn(List<Long> ids);
}
