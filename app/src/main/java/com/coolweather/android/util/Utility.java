package com.coolweather.android.util;

import android.text.TextUtils;
import android.util.Log;

import com.coolweather.android.db.City;
import com.coolweather.android.db.County;
import com.coolweather.android.db.Province;
import com.coolweather.android.gson.Weather5;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 工具类，处理和解析GSON格式的数据
 * Created by pzbz025 on 2017/8/11.
 */

public class Utility {

    private static final String TAG = "Utility";

    public static boolean handleProvinceResponse(String response) {

        /**
         * 解析和处理服务器返回的省级数据
         */
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provincesObject = allProvinces.getJSONObject(i);
                    Log.d(TAG, "handleProvinceResponse: " + provincesObject.toString());
                    Province province = new Province();
                    province.setProvinceCode(provincesObject.getInt("id"));
                    province.setProvinceName(provincesObject.getString("name"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级的数据
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级的数据
     */
    public static boolean handlerCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCountyCode(countyObject.getInt("id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 用GSON解析JSON天气数据
     */
//    public static Weather5 handleWeatherResponse(String response) {
//        try {
//            if (!TextUtils.isEmpty(response)) {
//                JSONObject jsonObject = new JSONObject(response);
//                JSONArray jsonArray = jsonObject.getJSONArray("HeWeather5");
//                String weatherContent = jsonArray.getJSONObject(0).toString();
//                Log.d(TAG, "handleWeatherResponse: " + weatherContent);
//                return new Gson().fromJson(response, Weather5.class);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
