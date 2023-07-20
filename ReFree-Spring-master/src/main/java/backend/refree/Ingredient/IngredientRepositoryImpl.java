package backend.refree.Ingredient;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component
public class IngredientRepositoryImpl implements  IngredientRepository{
    private final EntityManager em;
    public IngredientRepositoryImpl(EntityManager em){
        this.em=em;
    }
    @Override
    public void create(Ingredient ingredient){
        em.persist(ingredient);
    }
    @Override
    public Optional<Ingredient> view(int ingredient_id){
        Ingredient ingredient=em.find(Ingredient.class,ingredient_id);
        return Optional.ofNullable(ingredient);
    }

}
