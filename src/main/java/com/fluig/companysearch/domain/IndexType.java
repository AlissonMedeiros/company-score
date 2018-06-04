package com.fluig.companysearch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum IndexType {
    SIX_MONTHS,
    LAST_YEAR,
    TWELVE_MONTHS,
    PAST_LAST_YEAR,
    LAST_THREE_YEARS;
}
