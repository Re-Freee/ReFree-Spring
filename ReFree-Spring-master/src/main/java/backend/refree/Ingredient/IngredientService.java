package backend.refree.Ingredient;

import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Component
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    public IngredientService(IngredientRepository ingredientRepository){
        this.ingredientRepository=ingredientRepository;
    }

    public void create(Ingredient ingredient){
        ingredientRepository.create(ingredient);
    }
    public Ingredient view(int ingredient_id){
        Optional<Ingredient> ingredient2=ingredientRepository.view(ingredient_id);
        Ingredient ingredient1=ingredient2.get();

        return ingredient1;
    }

}
