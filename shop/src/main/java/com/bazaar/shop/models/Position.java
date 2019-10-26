
package com.bazaar.shop.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Position {

    @JsonProperty("latitude")
    public String latitude;
    @JsonProperty("longitude")
    public String longitude;
}
