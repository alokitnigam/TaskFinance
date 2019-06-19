package com.example.financerepublicassign.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;


public class StockResponse {
    @SerializedName("Meta Data")
    @Expose
    public MetaData metaData;
    @SerializedName("Time Series (Daily)")
    @Expose
    public HashMap<String, StockValue> timeSeries1min;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public HashMap<String, StockValue> getTimeSeries1min() {
        return timeSeries1min;
    }

    public void setTimeSeries1min(HashMap<String, StockValue> timeSeries1min) {
        this.timeSeries1min = timeSeries1min;
    }

    private class MetaData {
        @SerializedName("1. Information")
        @Expose
        private String information;
        @SerializedName("2. Symbol")
        @Expose
        private String symbol;
        @SerializedName("3. Last Refreshed")
        @Expose
        private String lastRefreshed;
        @SerializedName("4. Interval")
        @Expose
        private String interval;
        @SerializedName("5. Output Size")
        @Expose
        private String outputSize;
        @SerializedName("6. Time Zone")
        @Expose
        private String timeZone;
    }

    public class StockValue {
        @SerializedName("1. open")
        @Expose
        private String open;
        @SerializedName("2. high")
        @Expose
        private String high;

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        @SerializedName("3. low")
        @Expose
        private String low;
        @SerializedName("4. close")
        @Expose
        private String close;
        @SerializedName("5. volume")
        @Expose
        private String volume;
    }
}