package com.fluig.companysearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDetails {

    public List<String> companyIndexes;


}
