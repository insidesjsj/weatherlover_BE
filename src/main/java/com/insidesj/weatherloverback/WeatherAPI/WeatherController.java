package com.insidesj.weatherloverback.WeatherAPI;

import com.insidesj.weatherloverback.Location.GridService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@CrossOrigin(origins = "http://localhost:3000")  // 클라이언트 주소로 변경
@RestController
public class WeatherController {
    @Autowired
    private GridService gridService;

    // 기상청 api 서비스키 가져오기
    @Value("${weather.api.service-key}")
    private String weatherApiServiceKey;

    // 어제 날짜 구하기
    LocalDate yesterdayDate = LocalDate.now().minusDays(1);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");  // 포맷 지정
    String yesterdayDateString = yesterdayDate.format(formatter);

    @GetMapping("/weather")
    public ResponseEntity<String> getWeather() {
        String geoX = gridService.getNx();
        String geoY = gridService.getNy();

        String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
        String serviceKey = weatherApiServiceKey;
        String pageNo = "1";
        String numOfRows = "1000";
        String dataType = "JSON";
        String baseDate = yesterdayDateString;
        String baseTime = "2300";
        String nx = geoX;
        String ny = geoY;

        String url = String.format("%s?serviceKey=%s&pageNo=%s&numOfRows=%s&dataType=%s&base_date=%s&base_time=%s&nx=%s&ny=%s",
                apiUrl, serviceKey, pageNo, numOfRows, dataType, baseDate, baseTime, nx, ny);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        System.out.println("Response code: " + response.getStatusCodeValue());
        System.out.println(response.getBody());

        // JSON 파싱
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());

            // 예시로 하늘 상태 파싱
            JSONObject responseObj = (JSONObject) jsonObject.get("response");
            JSONObject bodyObj = (JSONObject) responseObj.get("body");
            JSONObject itemsObj = (JSONObject) bodyObj.get("items");
            JSONArray itemArr = (JSONArray) itemsObj.get("item");


            for (Object item : itemArr) {
                JSONObject itemObj = (JSONObject) item;
                String category = (String) itemObj.get("category");

                if ("SKY".equals(category)) {
                    String skyStatus = (String) itemObj.get("fcstValue");
                    System.out.println("Sky Status: " + skyStatus);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 처리
        }

        return ResponseEntity.ok("{\"message\": \"Weather received successfully\"}");
    }
}
