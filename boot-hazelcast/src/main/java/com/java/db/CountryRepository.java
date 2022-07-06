package com.java.db;

import com.java.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {

    Country findByCountryId(String countryId) throws Exception;

    Country deleteByCountryId(String countryId) throws Exception ;
}