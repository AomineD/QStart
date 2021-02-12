package com.wineberryhalley.qstart.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Country {
    private String name;
    private String region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    private String flag;


    private Map<String, String> langObjs = new HashMap<>();

    public boolean isSelected;

    public static Country getEmpty(){
        Country ca = new Country();
        ca.setName("NONE");
        ca.setFlag("");
        return new Country();
    }

    public String getNameTranslated(){

        if(langObjs.size() < 1){
            return name;
        }else{
          String actualCountry =  Locale.getDefault().getCountry().toLowerCase();
            for (int i = 0; i < langObjs.size(); i++) {
if(langObjs.containsKey(actualCountry)){
    return langObjs.get(actualCountry);
}


            }

            return name;
        }

    }

    public void setLangObjs(Map<String, String> l){
        this.langObjs.putAll(l);
    }

}
