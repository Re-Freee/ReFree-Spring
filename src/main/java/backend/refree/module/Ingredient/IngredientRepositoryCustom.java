package backend.refree.module.Ingredient;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

public interface IngredientRepositoryCustom {
//    public List<Ingredient> findAllIngredient(int mem_id);
//    public List<Ingredient> search(String searchKey,int mem_id);
//    public void delete(int ingredient_id,int cnt);
//    Optional<Ingredient> findByIngred(int ingredient);
    void delete(int ingredient_id,int cnt,String memo);
    List<Ingredient> findAllIngredient(int mem_id);
    List<Ingredient> search(String searchKey,int mem_id);
}
