package backend.refree.Ingredient;

import java.util.Optional;

public interface IngredientRepository {
    void create(Ingredient ingredient);
    Optional<Ingredient> view(int ingredient_id);
}
