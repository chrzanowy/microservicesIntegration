package com.information.dto.rso;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

/**
 * Created by Kuba on 2017-05-13.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Province {
    @XmlAttribute
    private String id;
    private String province;
    @XmlAttribute
    private String slug;
    @XmlAttribute
    private String city;
}
