package com.information.service;

import com.information.dto.rso.News;
import com.information.dto.rso.Newses;
import com.information.dto.rso.Rso;
import com.integration.dto.RsoDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Kuba on 2017-05-13.
 */
@Service
public class RsoService {

    private static Map<String, Long> LATEST_ID_MAP = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();
    private final static String RSO_URL = "https://komunikaty.tvp" +
            ".pl/komunikatyxml/{state}/wszystkie/0?_format=xml";

    public Map<String, List<RsoDto>> getLatestNewsForStates(List<String> allStates) {
        Map<String, List<RsoDto>> newsMap = new HashMap<>();
        for (String state : allStates) {
            Rso rso = new Rso(restTemplate
                    .getForObject(RSO_URL, Newses.class, state));
            long latestId = LATEST_ID_MAP.containsKey(state) ? LATEST_ID_MAP.get(state) : 0L;
            List<News> filteredNews = Arrays.stream(rso.getNewses().getNews())
                    .filter(news -> Long.valueOf(news.getId()) > latestId)
                    .collect(Collectors.toList());
            if (!filteredNews.isEmpty()) {
                newsMap.put(state, translateNestList(filteredNews));
                filteredNews.stream()
                        .max((o1, o2) -> Long.valueOf(o1.getId()).compareTo(Long.valueOf(o2.getId())))
                        .ifPresent(news -> LATEST_ID_MAP.put(state, Long.valueOf(news.getId())));
            }
        }
        return newsMap;
    }

    private List<RsoDto> translateNestList(List<News> newsList) {
        return newsList.stream().map(this::translateNewsToRsoDto).collect(Collectors.toList());
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
                    news.getWater_level_alarm_status_value(),
                    news.getWater_level_warning_status_value(),
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
