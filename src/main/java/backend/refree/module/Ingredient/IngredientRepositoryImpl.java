package backend.refree.module.Ingredient;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static backend.refree.module.Ingredient.QIngredient.ingredient;

@RequiredArgsConstructor
public class IngredientRepositoryImpl implements  IngredientRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Ingredient> findAllIngredient(int mem_id) {
        return jpaQueryFactory
                .selectFrom(ingredient)
                .where(ingredient.ingredient_id.eq(mem_id))
                .fetch();
    }
    @Override
    public List<Ingredient> search(String searchKey,int mem_id){
        return jpaQueryFactory
                .selectFrom(ingredient)
                .where(ingredient.name.contains(searchKey),ingredient.ingredient_id.eq(mem_id))
                .fetch();
    }
//        List<Ingredient> check=em.createQuery("select v from Ingredient v where u.member_id:=id",Ingredient.class)
//                .setParameter("id",mem_id)
//                .getResultList();
//        List<Ingredient> confirm=new ArrayList<>();
//        for(int i=0;i<check.size();i++){
//            if(check.get(i).getName().contains(searchKey)){
//                confirm.add(check.get(i));
//            }
//        }
//        return confirm;
    @Override
    public void delete(int ingredient_id,int cnt){
        jpaQueryFactory
                .update(ingredient)
                .set(ingredient.quantity,cnt)
                .where(ingredient.ingredient_id.eq(ingredient_id))
                .execute();
//        em.createQuery("update Ingredient u set u.quantity=:quantity")
//                .setParameter("quantity",cnt)
//                .executeUpdate();
    }

//    private final EntityManager em;
//    public IngredientRepositoryImpl(JPAQueryFactory jpaQueryFactory, EntityManager em){
//        this.jpaQueryFactory = jpaQueryFactory;
//        this.em=em;
//    }
//    @Override
//    public void create(Ingredient ingredient){
//        em.persist(ingredient);
//    }
//    @Override
//    public Optional<Ingredient> view(int ingredient_id){
//        Ingredient ingredient=em.find(Ingredient.class,ingredient_id);
//        return Optional.ofNullable(ingredient);
//    }
//    @Override
//    public List<Ingredient> findAllIngredient(int mem_id){
//        return em.createQuery("select u from Ingredient u where u.member_id=:id", Ingredient.class)
//                .setParameter("id",mem_id)
//                .getResultList();
//    }

}
