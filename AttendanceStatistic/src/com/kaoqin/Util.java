package com.kaoqin;

public class Util {
	//2015/03/19  08:50:44
	public  static String getRealTime(String time){
		int returnVal=-1;
		time =time.split("  ")[1];
		String [] tmps=time.split(":");
		int hour = Integer.parseInt(tmps[0]);
		int minite=Integer.parseInt(tmps[1]);
		return ("hour:"+hour+" mimite:"+minite+" tot:"+(hour*60+minite));
	}
	public  static  int getRealTimeMinite(String time){
		time =time.split("  ")[1];
		String [] tmps=time.split(":");
		int hour = Integer.parseInt(tmps[0]);
		int minite=Integer.parseInt(tmps[1]);
		return  (hour*60+minite);
	}
	
}
