package backend.refree.module.Analysis;

import backend.refree.module.Recipe.RecipeRepository;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;

@SpringBootTest
@Transactional
class WordAnalysisServiceTest {

    @Autowired
    RecipeRepository recipeRepository;

    @Test
    public void wordTest() throws Exception {
        Set<String> set = new HashSet<>();
        Pattern numberPattern = Pattern.compile("[0-9]+");

        recipeRepository.findAll()
                .forEach(recipe -> {
                    KomoranResult analyze = KomoranUtils.getInstance().analyze(recipe.getIngredient());
                    List<String> nouns = analyze.getNouns();
                    for (String noun : nouns) {
                        if (numberPattern.matcher(noun).find()) {
                            continue;
                        }
                        set.add(noun);
                    }
                });

        KomoranUtils.log.info(set.toString());
    }

    private List<String> removeDuplicatesAndNumbers(List<String> words) {
        // 중복 제거를 위해 Set을 사용합니다.
        Set<String> uniqueWords = new HashSet<>(words);

        // 숫자를 삭제하기 위한 정규표현식 패턴을 작성합니다.
        Pattern numberPattern = Pattern.compile("[0-9]+");

        // 결과를 저장할 리스트를 생성합니다.
        List<String> result = new ArrayList<>();

        // 중복 제거 및 숫자 삭제 후 결과 리스트에 추가합니다.
        for (String word : uniqueWords) {
            // 숫자를 삭제합니다.
            String cleanedWord = numberPattern.matcher(word).replaceAll("");
            // 공백이 아닌 경우 결과 리스트에 추가합니다.
            if (!cleanedWord.trim().isEmpty()) {
                result.add(cleanedWord);
            }
        }

        return result;
    }
}