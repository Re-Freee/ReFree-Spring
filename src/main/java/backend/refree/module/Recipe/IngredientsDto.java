package backend.refree.module.Recipe;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class IngredientsDto {

    @UniqueElements
    @Size(min = 3, max = 5)
    private List<String> ingredients;
}
