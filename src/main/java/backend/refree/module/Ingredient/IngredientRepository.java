package backend.refree.module.Ingredient;

import backend.refree.module.Member.memberEntity;
import backend.refree.module.Recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer>,IngredientRepositoryCustom {

}
