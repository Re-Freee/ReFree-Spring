package backend.refree.module.Recipe;

import backend.refree.infra.response.BasicResponse;
import backend.refree.infra.response.GeneralResponse;
import backend.refree.infra.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/recommend")
    public ResponseEntity<? extends BasicResponse> recommend(@ModelAttribute @Valid IngredientsDto ingredientsDto) {
        List<RecipeDto> recipeDtos = recipeService.recommend(ingredientsDto.getIngredients());
        return ResponseEntity.ok().body(new GeneralResponse<>(recipeDtos, "RECOMMEND_RECIPE_RESULT"));
    }
}
