package com.cn.template.xutil.weather;

import java.rmi.RemoteException;

import com.cn.template.xutil.weather.WeatherWebServiceStub.ArrayOfString;
import com.cn.template.xutil.weather.WeatherWebServiceStub.GetSupportCity;
import com.cn.template.xutil.weather.WeatherWebServiceStub.GetSupportCityResponse;
import com.cn.template.xutil.weather.WeatherWebServiceStub.GetWeatherbyCityName;
import com.cn.template.xutil.weather.WeatherWebServiceStub.GetWeatherbyCityNameResponse;

/**
 * Axis2 的bin 中执行： 
 * D:\axis2-1.6.2\bin>wsdl2java -uri http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?WSDL -p com.cn.template.xutil.weather -d adb
 * 生成两个文件 WeatherWebServiceCallbackHandler.java和WeatherWebServiceStub.java；
 * 通过这两个文件来调用http://www.webxml.com.cn/WebServices/WeatherWebService.asmx中提供的方法.
 * 
 * @author Libra
 *
 */
public class WeatherWebServices {

	/** 通过webservice获得天气支持的地区 */
	public static void main(String[] args) throws RemoteException {
		getWeatherbyCityName("南昌");
		getSupportCity();
	}

	private static void getSupportCity() throws RemoteException{
		WeatherWebServiceStub client = new WeatherWebServiceStub();
		GetSupportCity getSupportCity = new GetSupportCity();
		getSupportCity.setByProvinceName("ALL");
		GetSupportCityResponse response = client.getSupportCity(getSupportCity);
		String[] results = response.getGetSupportCityResult().getString();
		for(String city:results){
			System.out.println(city);
		}
	}

	private static void getWeatherbyCityName(String cityName) throws RemoteException{
		WeatherWebServiceStub client = new WeatherWebServiceStub();
		GetWeatherbyCityName getWeatherbyCityName = new GetWeatherbyCityName();
		getWeatherbyCityName.setTheCityName(cityName);
		GetWeatherbyCityNameResponse response = client.getWeatherbyCityName(getWeatherbyCityName);
		ArrayOfString results = response.getGetWeatherbyCityNameResult();
		String[] strs = results.getString();
		for(String str:strs){
			System.out.println(str);
		}
	}
}
