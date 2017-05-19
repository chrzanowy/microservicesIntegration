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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Pagination_info")
public class Pagination_info {
    @XmlAttribute
    private String totalItems;
    @XmlAttribute
    private String itemsPerPage;
}
