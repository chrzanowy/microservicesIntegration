package com.information.dto.rso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Kuba on 2017-05-13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class News {
    private String water_level_warning_status_value;

    private String location_name;

    private String rso_alarm;

    private String rso_icon;

    private String valid_from;

    private String water_level_alarm_status_value;

    private String type;

    private Provinces provinces;

    private String water_level_trend;

    private String shortcut;

    private String id;

    private String content;

    private String water_level_value;

    private String title;

    private String updated_at;

    private String created_at;

    private String longitude;

    private String latitude;

    private String repetition;

    private String valid_to;

    private String river_name;
}