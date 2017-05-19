package com.information.service;

import com.information.dto.rso.News;
import com.information.dto.rso.Newses;
import com.information.dto.rso.Rso;
import com.integration.dto.RsoAccidentDto;
import com.integration.dto.RsoDto;
import com.integration.dto.RsoRiverDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kuba on 2017-05-13.
 */
@Service
public class RsoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final static String RSO_URL = "https://komunikaty.tvp" +
            ".pl/komunikatyxml/wszystkie/wszystkie/1?_format=xml";

    public List<RsoDto> getLatestNewsForStates(List allStates, Long latestId) {
        Rso rso = new Rso(restTemplate
                .getForObject(RSO_URL,
                        Newses.class));
        return Arrays.stream(rso.getNewses().getNews())
                .filter(news -> Long.valueOf(news.getId()) > latestId)
                .filter(news -> allStates.contains(news.getProvinces().getProvince().getSlug()))
                .map(this::translateNewsToRsoDto)
                .collect(Collectors.toList());
    }

    private RsoDto translateNewsToRsoDto(News news) {
        if ("z".equals(news.getType())) {
            return new RsoRiverDto(
                    news.getLocation_name(),
                    true,
                    news.getValid_from(),
                    news.getValid_to(),
                    news.getProvinces().getProvince().getSlug(),
                    news.getRiver_name(),
                    news.getWater_level_value(),
                    news.getWater_level_warning_status_value(),
                    news.getWater_level_alarm_status_value()
            );
        } else {
            return new RsoAccidentDto(
                    news.getLocation_name(),
                    false,
                    news.getValid_from(),
                    news.getValid_to(),
                    news.getProvinces().getProvince().getSlug(),
                    news.getContent()
            );
        }
    }

}
