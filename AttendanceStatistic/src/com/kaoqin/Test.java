package com.kaoqin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;


public class Test {

	static String tab=",";
	public static void main(String[] args) throws IOException {
//		printSingle();
		Scanner reader = new Scanner(System.in);
		System.out.println("input month or month/day (2015/03 or 2015/03/19)");
		String dayStr=reader.nextLine();
		String basePath=getCleanPath()+File.separator+"kaoqin"+File.separator;
		File file = new File(basePath);
		if(!file.exists()){
			file.mkdirs();
		}
		printCurrentDay(dayStr,basePath);
	}
	private static  void printSingle() throws IOException {
		String yearMonth="2015/03";
		String[] nums = {"257"};
		StringBuilder sb = new StringBuilder();
		sb.append(getTitle());
		for (int i = 0; i < nums.length; i++) {
			sb.append(generate(nums[i],yearMonth));
		}
		String path = "/Users/luhai/Downloads/kaoqin/"+yearMonth.replace("/", "-")+nums.toString()+".csv";
		writeToFile(path, sb.toString());
	}
	private static  void printCurrentDay(String dayStr,String basePath) throws IOException {
		String yearMonth=dayStr;
		String[] nums = getNums();
		StringBuilder sbnotComStr=new StringBuilder("未打开人员：");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nums.length; i++) {
			String generate = generate(nums[i],yearMonth);
			if(generate==null||"".equals(generate)){
				sbnotComStr.append(nums[i]+" ");
			}
			sb.append(generate);
		}
		String path = basePath+yearMonth.replace("/", "-")+".csv";
		writeToFile(path, sb.toString());
		System.out.println("-------------------------------------");
		System.out.println(sbnotComStr.toString());
		System.out.println("-------------------------------------");
		
	}
	private static String generate(String num,String yearMonth) throws IOException{
		while(num.length()<9){
			num="0"+num;
		}
		String sourcePath=getCleanPath()+"/"+"001_GL.TXT";
//		 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/luhai/Downloads/test.txt"),"gbk"));
		 BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourcePath),"gbk"));
		 List<UserBean> list = new ArrayList<UserBean>();
		    try {
		        String line = br.readLine();
		        while (line != null) {
		            String[] split = line.split("\\t");
		            String realnum = split[2];
		            String time =split[6];
		            if(realnum.equals(num)){
		            	String day=time.split("  ")[0];
		            	if(getUserBeanByday(list,day)==null){
		            		if(!(day.indexOf(yearMonth)>-1)){
		            			line = br.readLine();
		            			continue;
		            		}
		            		UserBean userBean= new UserBean();
		            		userBean.setDay(day);
		            		userBean.setNum(num);
		            		userBean.setUserName(split[3]);
		            		userBean.setRealStartTime(Util.getRealTimeMinite(time));
		            		userBean.setDaytimeStart(time);
		            		list.add(userBean);
		            	}else{
		            		UserBean userBeanByday = getUserBeanByday(list,day);
		            		userBeanByday.setRealEndTime(Util.getRealTimeMinite(time));
		            		userBeanByday.setDaytimeEnd(time);
		            	}
		            }
		            line = br.readLine();
		        }
		    } finally {
		        br.close();
		    }
		    String printUserBeanList = printUserBeanList(list);
		    System.out.println(printUserBeanList);
//		    String fileName=list.get(0).getUserName().trim().replace(" ","")+"-"+list.get(0).getNum();
//		    PrintWriter writer = new PrintWriter("/Users/luhai/Downloads/kaoqin/"+fileName+".csv", "GBK");
//		    writer.println(printUserBeanList);
//		    writer.close();
		    return printUserBeanList;

	}
	private static void writeToFile(String filePath,String content) throws FileNotFoundException, UnsupportedEncodingException{
	    PrintWriter writer = new PrintWriter(filePath, "GBK");
	    writer.println(content);
	    writer.close();
	}
	public static UserBean getUserBeanByday(List<UserBean> list,String day){
		for (UserBean userBean : list) {
			if(userBean.getDay().equals(day)){
				return userBean;
			}
		}
		return null;
	}
	public static String printUserBean(UserBean userBean){
		StringBuilder strbf = new StringBuilder();
    	strbf.append(userBean.getNum());
    	strbf.append(tab);
    	strbf.append(userBean.getUserName());
    	strbf.append(tab);
    	strbf.append("'"+userBean.getDaytime().replace(tab, " "));
    	strbf.append(tab);
    	strbf.append("'"+userBean.getDaytimeEnd().replace(tab, " "));
    	strbf.append(tab);
    	strbf.append(userBean.getRealStartTime());
    	strbf.append(tab);
    	strbf.append(userBean.isBefore830(userBean.getRealStartTime())?"是":"否");
    	strbf.append(tab);
    	strbf.append(userBean.getFixStartTime());
    	strbf.append(tab);
    	strbf.append(userBean.getRealWorkHour());
//    	System.out.println(strbf.toString());
    	return strbf.toString();
	}
	public static String printUserBeanList(List<UserBean> list){
//        StringBuilder sb = new StringBuilder();
        StringBuilder strbf=new StringBuilder();
//    	strbf.append("工号");
//    	strbf.append(tab);
//    	strbf.append("姓名");
//    	strbf.append(tab);
//    	strbf.append("打卡时间");
//    	strbf.append(tab);
//    	strbf.append("下班时间");
//    	strbf.append(tab);
//    	strbf.append("当天分钟数");
//    	strbf.append(tab);
//    	strbf.append("是否8：30前打卡");
//    	strbf.append(tab);
//    	strbf.append("修正时间");
//    	strbf.append(tab);
//    	strbf.append("工作时间");
//    	strbf.append(System.lineSeparator());
//    	System.out.println(strbf.toString());
    	for (UserBean userBean : list) {
    		strbf.append(printUserBean(userBean));
    		strbf.append(System.lineSeparator());
		}
    	return strbf.toString();
	}
	
	private static String getTitle(){
        StringBuilder strbf=new StringBuilder();
    	strbf.append("工号");
    	strbf.append(tab);
    	strbf.append("姓名");
    	strbf.append(tab);
    	strbf.append("打卡时间");
    	strbf.append(tab);
    	strbf.append("下班时间");
    	strbf.append(tab);
    	strbf.append("当天分钟数");
    	strbf.append(tab);
    	strbf.append("是否8：30前打卡");
    	strbf.append(tab);
    	strbf.append("修正时间");
    	strbf.append(tab);
    	strbf.append("工作时间");
    	strbf.append(System.lineSeparator());
//    	System.out.println(strbf.toString());
    	return strbf.toString();
	}
	
	public static String getCleanPath() {
	    ClassLoader classLoader = Test.class.getClassLoader();
	    File classpathRoot = new File(classLoader.getResource("").getPath());

	    return classpathRoot.getPath();
	}
	
	private static String[] getNums() throws IOException{
		Properties prop = new Properties();
//		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
//		InputStream stream = loader.getResourceAsStream("config.properties");
		InputStreamReader ir = new InputStreamReader(new FileInputStream(getCleanPath()+"/"+"config.properties"));
		prop.load(ir);
		String usersStr = prop.getProperty("users");
		String[] split = usersStr.split(",");
		String[] users = new String[split.length];
		for(int i=0;i<split.length;i++){
			users[i]=split[i];
		}
		return users;
	}
	
}
