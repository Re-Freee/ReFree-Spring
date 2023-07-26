package backend.refree.module.Ingredient;

import java.util.List;

public interface IngredientRepositoryCustom {
//    public List<Ingredient> findAllIngredient(int mem_id);
//    public List<Ingredient> search(String searchKey,int mem_id);
//    public void delete(int ingredient_id,int cnt);

    void delete(int ingredient_id,int cnt);
    List<Ingredient> findAllIngredient(int mem_id);
    List<Ingredient> search(String searchKey,int mem_id);
}
