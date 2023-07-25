package backend.refree.module.Recipe;

import backend.refree.infra.response.BasicResponse;
import backend.refree.infra.response.GeneralResponse;
import backend.refree.infra.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/recommend")
    public ResponseEntity<? extends BasicResponse> recommend(@RequestParam List<String> ingredients) {
        List<RecipeDto> recipeDtos = recipeService.recommend(ingredients);
        return ResponseEntity.ok().body(new GeneralResponse<>(recipeDtos, "RECOMMEND_RECIPE_RESULT"));
    }
}
