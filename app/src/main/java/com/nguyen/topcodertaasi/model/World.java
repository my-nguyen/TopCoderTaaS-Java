package com.nguyen.topcodertaasi.model;

import com.google.gson.annotations.SerializedName;

public class World {
    @SerializedName("data")
    public Data data;

    public class Data {
        @SerializedName("total_cases")
        public String totalCases;
        @SerializedName("recovery_cases")
        public String recoveryCases;
        @SerializedName("death_cases")
        public String deathCases;
        @SerializedName("last_update")
        public String lastUpdate;
        @SerializedName("currently_infected")
        public String currentlyInfected;
    }
}

