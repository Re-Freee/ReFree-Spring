package backend.refree.Ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService){
        this.ingredientService=ingredientService;
    }
    @PostMapping("/create")
    public void create(@RequestBody Ingredient ingredient){
        ingredientService.create(ingredient);
    }
    @GetMapping("/view")
    public ResponseEntity view(@RequestParam int ingredient_id){
        return ResponseEntity.ok().body(ingredientService.view(ingredient_id));
    }
}
