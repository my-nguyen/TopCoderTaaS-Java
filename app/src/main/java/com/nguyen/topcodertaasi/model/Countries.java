package com.nguyen.topcodertaasi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Countries {
    @SerializedName("data")
    public Data data;

    public class Data {
        @SerializedName("paginationMeta")
        public Pagination pagination;
        @SerializedName("last_update")
        public String lastUpdate;
        @SerializedName("rows")
        public List<Country> countries = null;
    }

    public class Pagination {
        @SerializedName("currentPage")
        public Integer currentPage;
        /*@SerializedName("currentPageSize")
        public Integer currentPageSize;*/
        @SerializedName("totalPages")
        public Integer totalPages;
        /*@SerializedName("totalRecords")
        public Integer totalRecords;*/
    }

    /*public class Row {
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
    }*/
}
