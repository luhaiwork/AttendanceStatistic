package com.kaoqin;

public class UserBean {
	private String userName="";
	private String num="";
	private String day="";
	private int realStartTime=0;
	private int realEndTime=0;
	private String daytimeStart="";
	private String daytimeEnd="";
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getRealStartTime() {
		return realStartTime;
	}
	public void setRealStartTime(int realStartTime) {
		this.realStartTime = realStartTime;
	}
	public int getRealEndTime() {
		return realEndTime;
	}
	public void setRealEndTime(int realEndTime) {
		this.realEndTime = realEndTime;
	}
	public String getDaytime() {
		return daytimeStart;
	}
	public void setDaytimeStart(String daytimeStart) {
		this.daytimeStart = daytimeStart;
	}
	public String getDaytimeEnd() {
		return daytimeEnd;
	}
	public void setDaytimeEnd(String daytimeEnd) {
		this.daytimeEnd = daytimeEnd;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getFixEndTime(){
		if(getRealEndTime()>12*60&&getRealEndTime()<13*60){
			//对于早晨晚来的人，如8:37打开，那么12:07走，要算他7分钟的上班时间。
			if(getFixStartTime()>8.5*60){
				return (12*60+(getFixStartTime()-(int)(8.5*60)));
			}
			return 12*60;
		}else{
			return getRealEndTime();
		}
	}

	public double getRealWorkHour(){
		if("".equals(daytimeStart)||"".equals(daytimeEnd)){
			return -1;
		}
		int sleepTime=0;
		if(getFixEndTime()>=13*60&&getFixStartTime()<=12*60){
			sleepTime=1;
		}
		int minu=getFixEndTime()-getFixStartTime();
		double workMust=minu/60-sleepTime+(minu%60/30*0.5);
		return workMust;
	}
	
	public static  boolean isBefore830(int minute){
		if(minute <(60*8+30)){
			return true;
		}else{
			return false;
		}
	}
	public  boolean isNoonCom(int minute){
		if(minute <(60*13)&&minute>(60*12)){
			return true;
		}else{
			return false;
		}
	}
	private   int minute830=60*8+30;
	public   int getFixStartTime(){
		if(isBefore830(realStartTime)){
			return minute830;
		}else if(isNoonCom(realStartTime)){
			return 13*60;
		}else{
			return realStartTime;
		}
	}
	
}
