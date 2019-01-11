package com.teamsankya.quickviewreportingapp.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="quickviewtable")
public class ProductBean implements Serializable {

	@Id
	@Column(name="ProductId")
	private String ProductId;
	@Column(name="ProductName")
	private String ProductName;
	@Column(name="ProductPrice")
	private double ProductPrice;
	
	public ProductBean() {
	}
	
	public ProductBean(String productId, String productName, double productPrice) {
		super();
		ProductId = productId;
		ProductName = productName;
		ProductPrice = productPrice;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public double getProductPrice() {
		return ProductPrice;
	}
	public void setProductPrice(double productPrice) {
		ProductPrice = productPrice;
	}
	
	
	
	
	
	
}
