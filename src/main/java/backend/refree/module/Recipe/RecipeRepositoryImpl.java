package backend.refree.module.Recipe;


import backend.refree.module.Recipe.Recipe;
import backend.refree.module.Recipe.RecipeRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static backend.refree.module.Recipe.QRecipe.*;

@RequiredArgsConstructor
public class RecipeRepositoryImpl implements RecipeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Recipe> findByIn(List<Long> ids) {
        return jpaQueryFactory
                .selectFrom(recipe)
                .where(recipe.id.in(ids))
                .fetch();
    }
}
