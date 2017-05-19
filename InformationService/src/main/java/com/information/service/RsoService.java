package com.information.service;

import com.information.dto.rso.News;
import com.information.dto.rso.Newses;
import com.information.dto.rso.Rso;
import com.integration.dto.RsoDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by Kuba on 2017-05-13.
 */
@Service
public class RsoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final static String RSO_URL = "https://komunikaty.tvp" +
            ".pl/komunikatyxml/wszystkie/wszystkie/1?_format=xml";

    public Map<String, List<RsoDto>> getLatestNewsForStates(List allStates, Long latestId) {
        Map<String, List<RsoDto>> newsMap = new HashMap<>();
        Rso rso = new Rso(restTemplate
                .getForObject(RSO_URL,
                        Newses.class));
        Arrays.stream(rso.getNewses().getNews())
                .filter(news -> Long.valueOf(news.getId()) > latestId)
                .filter(news -> allStates.contains(news.getProvinces().getProvince().getSlug()))
                .forEach(news -> addToMap(news, newsMap));
        return newsMap;
    }

    private void addToMap(News news, Map<String, List<RsoDto>> newsMap) {
        String slug = news.getProvinces().getProvince().getSlug();
        if (newsMap.containsKey(slug)) {
            newsMap.get(slug).add(translateNewsToRsoDto(news));
        } else {
            List<RsoDto> rsoDtoList = new LinkedList<>();
            rsoDtoList.add(translateNewsToRsoDto(news));
            newsMap.put(slug, rsoDtoList);
        }
    }

    private RsoDto translateNewsToRsoDto(News news) {
        if ("z".equals(news.getType())) {
            return new RsoDto(
                    news.getLocation_name(),
                    true,
                    news.getValid_from(),
                    news.getValid_to(),
                    news.getProvinces().getProvince().getSlug(),
                    news.getRiver_name(),
                    news.getWater_level_value(),
                    news.getWater_level_warning_status_value(),
                    news.getWater_level_alarm_status_value(),
                    null
            );
        } else {
            return new RsoDto(
                    news.getLocation_name(),
                    false,
                    news.getValid_from(),
                    news.getValid_to(),
                    news.getProvinces().getProvince().getSlug(),
                    null,
                    null,
                    null,
                    null,
                    news.getContent()
            );
        }
    }

}
