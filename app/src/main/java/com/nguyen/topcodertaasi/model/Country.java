package com.nguyen.topcodertaasi.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Country implements Serializable {
    @SerializedName("country")
    public String country;
    @SerializedName("country_abbreviation")
    public String countryAbbreviation;
    @SerializedName("total_cases")
    public String totalCases;
    @SerializedName("total_deaths")
    public String totalDeaths;
    @SerializedName("total_recovered")
    public String totalRecovered;
    @SerializedName("active_cases")
    public String activeCases;
    @SerializedName("flag")
    public String flag;
}
