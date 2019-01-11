package com.teamsankya.quickviewreportingapp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.teamsankya.quickviewreportingapp.dto.ProductBean;


public class Productdetailsdao {
	String qry = "from ProductBean";

	public  List<ProductBean>  getproductdetails(){
		
		Configuration configuration = new Configuration().configure();
		Session session = configuration.buildSessionFactory().openSession();
		Query query = session.createQuery(qry);
		List<ProductBean> list = query.list();
		System.out.println(list);
		return list;	
	}

}
