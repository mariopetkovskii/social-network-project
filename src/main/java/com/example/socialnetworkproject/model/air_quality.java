package com.example.socialnetworkproject.model;

import lombok.Data;

@Data
public class air_quality {
    private Double co;
    private Double no2;
    private Double o3;
    private Double so2;
    private Double pm2_5;
    private Double pm10;

    public String getAirQuality(){
        return String.format("%.0f", (co + no2 + o3 + so2 + pm2_5 + pm10) / 8);
    }

    public String getNo2() {
        return String.format("%.0f", no2);
    }

    public String getCo() {
        return String.format("%.0f", co);
    }

    public String getO3() {
        return String.format("%.0f", o3);
    }

    public String getSo2() {
        return String.format("%.0f", so2);
    }

    public String  getPm2_5() {
        return String.format("%.0f", pm2_5);
    }

    public String  getPm10() {
        return String.format("%.0f", pm10);
    }

    public Double airQuality(){
        return (co + no2 + o3 + so2 + pm2_5 + pm10) / 8;
    }

    public String AirQualityInfo(){
        if(airQuality() < 40)
            return "Air is not polluted";
        else if(airQuality() < 100)
            return "Air is polluted, we recommend you to stay home";
        else
            return "Air is too hazardous, stay home, and use this application!";
    }
}
