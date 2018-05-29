package com.fluig.companysearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum CompanyStatus {
    NOT_RECOMMENDED,
    RECOMMENDED;

}
