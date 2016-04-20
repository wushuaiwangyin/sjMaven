package com.krm.dbaudit.web.all360;


public class WsCeshi {
	
	/**
	 * 处理流程类型枚举
	*/
	public enum ActionType {  
		proc_getdata,proc_groupdata,proc_mergedata,proc_sortdata,proc_distinctdata,proc_overlaydata
	}
	public enum Mobile {
		   Samsung(400), Nokia(250),Motorola(325);
		  
		   int price;
		   Mobile(int p) {
		      price = p;
		   }
		   int showPrice() {
		      return price;
		   } 
		}
	
	public static void main(String[] args) {
		ActionType actionType = ActionType.valueOf("proc_getdata");
		for(Mobile m : Mobile.values()) { 
	        System.out.println(m + " costs " + m.showPrice() + " dollars");
	     }
		switch(actionType){
		case 	proc_getdata:
			System.out.println(actionType);
		
		}
		
		System.out.println(actionType);

	}

}
