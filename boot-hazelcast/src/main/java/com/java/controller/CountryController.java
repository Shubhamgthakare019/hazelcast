package com.java.controller;

import com.java.model.Country;
import com.java.db.CountryRepository;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.apache.logging.log4j.LogManager.getLogger;

@RestController
@RequestMapping(path = "/country")
public class CountryController {

    private static final Logger logger = getLogger(CountryController.class);

    @Autowired
    private CountryRepository countryRepository;

    @Autowired(required = true)
    private Map<String, Country> countryMap;

    @GetMapping(path = {"/get/{countryId}"})
    public Country getCountry(@PathVariable("countryId") String countryId) throws Exception {
        //first check if accountMap has the UserAccount details,if yes then return it from database
        Country country = ((countryMap.get(countryId) != null) ? (countryMap.get(countryId))
                : countryRepository.findByCountryId(countryId));
        logger.info("Country : " + country);
        return country;
    }

    @PostMapping("/addCountry")
    public String createCountry(@RequestBody Country country) throws Exception {
        //save user account in cache and db
        countryMap.put(country.getCountryId(), country);
        countryRepository.save(country);
        String str = country.getCountryId();
        logger.info("Country id: " + str);
        return "Persisted countryId : " + str;
    }

    @PostMapping("/updateCountry")
    public String updateCountry(@RequestBody Country country) throws Exception {
        //save user account in cache and db
        countryMap.put(country.getCountryId(), country);
        Country countryDb = countryRepository.findByCountryId(country.getCountryId());

        if (countryDb != null && !countryDb.getCountryId().equals("1")) {
            countryRepository.save(country);
        }
        String str = "Updated countryId : "+country.getCountryId();
        logger.info("Country id: " + str);
        return  str;
    }

    @GetMapping(path = "delete/{countryId}")
    @Transactional
    public int deleteCountry(@PathVariable("countryId") String countryId) throws Exception {
        logger.info("Country id: " + countryId);
        //remove from both cache and database
           Country countryIdMap=  countryMap.get(countryId);

        if(countryIdMap !=null && countryIdMap.getCountryId().equals(countryId)){
            countryMap.remove(countryId);
        }
        if(countryRepository.findByCountryId(countryId)!=null && countryRepository.findByCountryId(countryId).getCountryId().equals(countryId)) {

            countryRepository.findByCountryId(countryId);
        }
        return Integer.parseInt(countryId);
    }

}