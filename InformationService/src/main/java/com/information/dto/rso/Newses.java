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
public class Newses
{
    private News[] news;

    private Pagination_info pagination_info;
}
