package com.weather.web;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.weather.WeatherAppProperties;
import com.weather.exception.RecordNotFoundException;
import com.weather.model.Weather;
import com.weather.service.WeatherService;



@RestController
@RequestMapping("/api/weather")
public class WeatherApiController {
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(WeatherApiController.class);
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private WeatherAppProperties configuration;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	
	@PostMapping("/{country}/{city}")
	public ResponseEntity<Object> getWeather(@PathVariable String country,
			@PathVariable String city) {
		System.out.println("asdsdfsfsdfsdf");
		
		String key=configuration.getKey();
		String lattitude="";
		
		if(country.trim().equalsIgnoreCase("CA") && city.trim().equalsIgnoreCase("Campbell"))
			lattitude=configuration.getCampbellCa();
		else if(country.trim().equalsIgnoreCase("NE") && city.trim().equalsIgnoreCase("Omaha"))
			lattitude=configuration.getOmahaNe();
		else if(country.trim().equalsIgnoreCase("TX") && city.trim().equalsIgnoreCase("Austin"))
			lattitude=configuration.getAustinTx();
		else if(country.trim().equalsIgnoreCase("Japan") && city.trim().equalsIgnoreCase("Niseko"))
			lattitude=configuration.getNisekoJapan();
		else if(country.trim().equalsIgnoreCase("Japan") && city.trim().equalsIgnoreCase("Nara"))
			lattitude=configuration.getNaraJapan();
		else if(country.trim().equalsIgnoreCase("Indonesia") && city.trim().equalsIgnoreCase("Jakarta"))
			lattitude=configuration.getJakartaIndonesia();
		else
			throw new RecordNotFoundException(country + " Not found in the list");
		
	
		String currentDate=new SimpleDateFormat("YYYY-MM-dd").format(new Date()).toUpperCase();
		Collection<Weather> weatherdetails= this.mongoTemplate.findAll
				(Weather.class);
		String cityName="";
		String countryName="";	
		for(Weather w:weatherdetails)
		{
			cityName=w.getCity();
			countryName = w.getCountry();
			String weatherDt=new SimpleDateFormat("YYYY-MM-dd").format(w.getCreatedDate()).toUpperCase();
	    	System.out.println("weatherDt"+weatherDt);
			if(cityName.equals(city) && countryName.equals(country) && currentDate.equals(weatherDt))
			{
				throw new RuntimeException("Record already exist for today date");
			}
		}

		
		Weather weather=weatherService.getWeather(key,lattitude);
		if(weather==null)
			throw new RecordNotFoundException("Country-"+ country);
		
		weather.setCity(city);
		weather.setCountry(country);
		weather.setCreatedDate(new Date());
		
		
		
		mongoTemplate.insert(weather);
		
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(weather.getId()).toUri();
			
			return ResponseEntity.created(location).build();

	
		
	}

	
	@GetMapping("/all-weather")
	public Collection<Weather> getAllWeatherDetails()
	{
		Collection<Weather> weatherdetails= this.mongoTemplate.findAll
				(Weather.class);
		
		return weatherdetails;
	}
	
	
	/*@RequestMapping("/")
    public String getAllWeather(Model model){

        model.addAttribute("products", this.mongoTemplate.findAll
				(Weather.class));

        return "index";
    }*/

	
	//@Scheduled(initialDelay = 1000, fixedRate = 10000)
	@Scheduled(cron="0 1 1 * * ?")
	public void run() {
	    System.out.println("Current time is :: " + Calendar.getInstance().getTime());
	    
	    
	    Calendar prevDay= Calendar.getInstance();
	    prevDay.setTime(new Date());
	    
	    prevDay.add(Calendar.DATE, -4);
	    
	    String prevDate=new SimpleDateFormat("YYYY-MM-dd").format(prevDay.getTime()).toUpperCase();;
	    
	    System.out.println("prevDate"+prevDate);
	    
	    Collection<Weather> weatherdetails= this.mongoTemplate.findAll
				(Weather.class);
	    for(Weather w: weatherdetails)
	    {
	    	Date weatherDate=w.getCreatedDate();
	    	
	    	String weatherDt=new SimpleDateFormat("YYYY-MM-dd").format(weatherDate).toUpperCase();
	    	System.out.println("weatherDt"+weatherDt);
	    	
	    	if(weatherDt.equals(prevDate))
	    	{
	    		System.out.println("Both dates are equal");
	    		this.mongoTemplate.remove(new Query(Criteria.where("id").is(w.getId())),Weather.class);
	    	}
	    }
	    
	  }



}
