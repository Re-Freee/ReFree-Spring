package backend.refree.module.Recipe;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @PostConstruct
    public void initRecipeData() throws IOException, ParseException {
        ClassPathResource classPathResource = new ClassPathResource("test.json");

        List<Recipe> recipeList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser
                .parse(new InputStreamReader(classPathResource.getInputStream(), "UTF-8"));

        JSONObject cookObject = (JSONObject) jsonObject.get("COOKRCP01");
        JSONArray recipeArray = (JSONArray) cookObject.get("row");
        for (int i = 0; i < recipeArray.size(); i++) {
            JSONObject recipeObject = (JSONObject) recipeArray.get(i);
            Recipe recipe = Recipe.builder()
                    .name((String) recipeObject.get("RCP_NM"))
                    .type((String) recipeObject.get("RCP_PAT2"))
                    .calorie(Double.parseDouble((String) recipeObject.get("INFO_ENG")))
                    .imageUrl((String) recipeObject.get("ATT_FILE_NO_MAIN"))
                    .ingredient((String) recipeObject.get("RCP_PARTS_DTLS"))
                    .manuel1((String) recipeObject.get("MANUAL01"))
                    .manuelUrl1((String) recipeObject.get("MANUAL_IMG01"))
                    .manuel2((String) recipeObject.get("MANUAL02"))
                    .manuelUrl2((String) recipeObject.get("MANUAL_IMG02"))
                    .manuel3((String) recipeObject.get("MANUAL03"))
                    .manuelUrl3((String) recipeObject.get("MANUAL_IMG03"))
                    .manuel4((String) recipeObject.get("MANUAL04"))
                    .manuelUrl4((String) recipeObject.get("MANUAL_IMG04"))
                    .manuel5((String) recipeObject.get("MANUAL05"))
                    .manuelUrl5((String) recipeObject.get("MANUAL_IMG05"))
                    .manuel6((String) recipeObject.get("MANUAL06"))
                    .manuelUrl6((String) recipeObject.get("MANUAL_IMG06"))
                    .manuel7((String) recipeObject.get("MANUAL07"))
                    .manuelUrl7((String) recipeObject.get("MANUAL_IMG07"))
                    .manuel8((String) recipeObject.get("MANUAL08"))
                    .manuelUrl8((String) recipeObject.get("MANUAL_IMG08"))
                    .manuel9((String) recipeObject.get("MANUAL09"))
                    .manuelUrl9((String) recipeObject.get("MANUAL_IMG09"))
                    .manuel10((String) recipeObject.get("MANUAL10"))
                    .manuelUrl10((String) recipeObject.get("MANUAL_IMG10"))
                    .manuel11((String) recipeObject.get("MANUAL11"))
                    .manuelUrl11((String) recipeObject.get("MANUAL_IMG11"))
                    .manuel12((String) recipeObject.get("MANUAL12"))
                    .manuelUrl12((String) recipeObject.get("MANUAL_IMG12"))
                    .manuel13((String) recipeObject.get("MANUAL13"))
                    .manuelUrl13((String) recipeObject.get("MANUAL_IMG13"))
                    .manuel14((String) recipeObject.get("MANUAL14"))
                    .manuelUrl14((String) recipeObject.get("MANUAL_IMG14"))
                    .manuel15((String) recipeObject.get("MANUAL15"))
                    .manuelUrl15((String) recipeObject.get("MANUAL_IMG15"))
                    .manuel16((String) recipeObject.get("MANUAL16"))
                    .manuelUrl16((String) recipeObject.get("MANUAL_IMG16"))
                    .manuel17((String) recipeObject.get("MANUAL17"))
                    .manuelUrl17((String) recipeObject.get("MANUAL_IMG17"))
                    .manuel18((String) recipeObject.get("MANUAL18"))
                    .manuelUrl18((String) recipeObject.get("MANUAL_IMG18"))
                    .manuel19((String) recipeObject.get("MANUAL19"))
                    .manuelUrl19((String) recipeObject.get("MANUAL_IMG19"))
                    .manuel20((String) recipeObject.get("MANUAL20"))
                    .manuelUrl20((String) recipeObject.get("MANUAL_IMG20"))
                    .build();
            recipeList.add(recipe);
        }
        recipeRepository.saveAll(recipeList);
    }
}
