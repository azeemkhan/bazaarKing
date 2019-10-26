package com.bazaar.shop.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.DateTime;

@Setter
@Getter
@ToString
public class Timing {
    private DateTime startTime;
    private DateTime endTime;
}
