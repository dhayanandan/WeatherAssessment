package com.weather.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weather.model.Weather;

/**
 * Created by jt on 1/20/16.
 */
@Controller
public class IndexController {
	
	
	
	private MongoTemplate mongoTemplate;

   	@Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}



	@RequestMapping("/")
    public String getIndex(Model model){

        model.addAttribute("products", mongoTemplate.findAll(Weather.class));

        return "index";
    }



}
