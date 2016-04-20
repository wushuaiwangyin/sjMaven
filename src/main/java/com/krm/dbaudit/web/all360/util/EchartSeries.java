package com.krm.dbaudit.web.all360.util;

import java.math.BigDecimal;


public class EchartSeries {
private  int  id;
private String name;
private  String type="line";
private double[] data;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public double[] getData() {
	return data;
}
public void setData(double[] data) {
	this.data = data;
}
public static void main(String[] args) {
BigDecimal	big=new BigDecimal("192300589").setScale(4,BigDecimal.ROUND_HALF_UP);
System.out.println(big.doubleValue());
}
}
