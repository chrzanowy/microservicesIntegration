package com.information.service;

import com.information.dto.rso.News;
import com.information.dto.rso.Newses;
import com.information.dto.rso.Rso;
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

    public List<News> getLatestNewsForStates(List allStates, Long latestId) {
        //TODO support pagination

        Rso rso = new Rso(restTemplate
                .getForObject(RSO_URL,
                        Newses.class));
        return Arrays.stream(rso.getNewses().getNews())
                .filter(news -> Long.valueOf(news.getId()) > latestId)
                .filter(news -> allStates.contains(news.getProvinces().getProvince().getSlug()))
                .collect(Collectors.toList());
    }


}
