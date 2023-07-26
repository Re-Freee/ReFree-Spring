package backend.refree.module.Ingredient;


import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Transactional
@Component
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    public IngredientService(IngredientRepository ingredientRepository){
        this.ingredientRepository=ingredientRepository;
    }
    public void create(Ingredient ingredient) throws IOException {
        final String inputs=ingredient.getName();//땅콩밥, 방울토마토, 흑토마토, 롤케이크
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1390802/AgriFood/FdFood/getKoreanFoodFdFoodList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=JFijwchWJy3Nc6ZJO%2BLrky1imhAw43gItboKn2EuJbkrJJhPd13DziheNccSaKwmHiPc%2FDz4Jx8Z5mvH5JG5bg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("service_Type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml 과 json 형식 지원*/
        urlBuilder.append("&" + URLEncoder.encode("Page_No","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("Page_Size","UTF-8") + "=" + URLEncoder.encode("20", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("food_Name","UTF-8") + "=" + URLEncoder.encode(inputs, "UTF-8")); /*음식 명 (검색어 입력값 포함 검색)*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();


        String jsonString=sb.toString();
        int endIndex=jsonString.length()-2;
        String modified=jsonString.substring(0,endIndex);
        String[] input=modified.split("\"fd_Eng_Nm\":\"");
        String[] input2;
        if(input.length>2){
            input2=input[2].split(",");
        }
        else{
            input2=input[1].split(",");
        }


        //input2[0] : 이 재료의 최종 카테고리로 추가
        ingredientRepository.save(ingredient);
    }
    public Ingredient view(int ingredient_id){
        Optional<Ingredient> ingredient2= ingredientRepository.findById(ingredient_id);
        Ingredient ingredient1=ingredient2.get();
        return ingredient1;
    }
    public List<Ingredient> findAllIngredient(int mem_id){
        return ingredientRepository.findAllIngredient(mem_id);
    }
    public List<Ingredient> closure(int mem_id){ //유통기한이 3일 남은 경우
        List<Ingredient> check=findAllIngredient(mem_id);
        List<Ingredient> confirm = null;
        for(int i=0;i<check.size();i++){
            Date expire=check.get(i).getPeriod();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formatted= dateFormat.format(expire);
            String[] format=formatted.split("-");

            // 현재 시간을 milliseconds로 얻어옵니다.
            long currentTimeMillis = System.currentTimeMillis();
            Date currentDate = new Date(currentTimeMillis);
            String formatted2=dateFormat.format(currentDate);
            String[] format2=formatted2.split("-");

            if(format2[0].equals(format2[0])&&format2[1].equals(format2[1])){
                if(Integer.parseInt(format[2])-Integer.parseInt(format2[2])<=3){
                    confirm.add(check.get(i));
                    continue;
                }
            }


            confirm.add(check.get(i));
        }
        return confirm;
    }
    public List<Ingredient> end(int mem_id){
        List<Ingredient> check=findAllIngredient(mem_id);
        List<Ingredient> confirm = null;
        for(int i=0;i<check.size();i++){
            Date expire=check.get(i).getPeriod();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formatted= dateFormat.format(expire);
            String[] format=formatted.split("-");

            // 현재 시간을 milliseconds로 얻어옵니다.
            long currentTimeMillis = System.currentTimeMillis();
            Date currentDate = new Date(currentTimeMillis);
            String formatted2=dateFormat.format(currentDate);
            String[] format2=formatted2.split("-");

            if(Integer.parseInt(format2[0])-Integer.parseInt(format[0])>0){
                confirm.add(check.get(i));
                continue;
            }
            else if(Integer.parseInt(format2[1])-Integer.parseInt(format[1])>0){
                confirm.add(check.get(i));
                continue;
            }
            else if(Integer.parseInt(format2[2])-Integer.parseInt(format[2])>0){
                confirm.add(check.get(i));
                continue;
            }

            confirm.add(check.get(i));
        }
        return confirm;
    }
    public Ingredient delete(int ingred_id, int cnt){
        ingredientRepository.delete(ingred_id,cnt);
        Ingredient check=view(ingred_id);
        check.setQuantity(cnt);
        return check;
    }
    public List<Ingredient> search(String searchKey,int mem_id){
        return ingredientRepository.search(searchKey,mem_id);
    }

}
