
package com.bazaar.shop.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {

    @JsonProperty("name")
    public String name;
    @JsonProperty("description")
    public String description;
    @JsonProperty("category")
    public List<String> category = null;
    @JsonProperty("ownerName")
    public String ownerName;
    @JsonProperty("userName")
    public String userName;
    @JsonProperty("password")
    public String password;
    @JsonProperty("shopLogo")
    public String shopLogo;
    @JsonProperty("photoUrls")
    public List<String> photoUrls = null;
    @JsonProperty("address")
    public List<Address> address = null;
    @JsonProperty("openTime")
    public String openTime;
    @JsonProperty("closeTime")
    public String closeTime;
}
