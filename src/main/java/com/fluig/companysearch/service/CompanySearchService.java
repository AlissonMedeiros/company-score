package com.fluig.companysearch.service;

import com.fluig.companysearch.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanySearchService {

    public static final String SEARCH_URL = "https://iosearch.reclameaqui.com.br/raichu-io-site-search-0.0.1-SNAPSHOT/companies/search/";
    public static final String DETAILS_URL = "https://iosite.reclameaqui.com.br/raichu-io-site-0.0.1-SNAPSHOT/company/{id}/public";
    private RestTemplate restTemplate = new RestTemplate();

    public List<Company> search(String name) {
        ResponseEntity<Companies> response = restTemplate.getForEntity(SEARCH_URL + name, Companies.class);
        return response.getBody().companies;
    }

    public List<CompanyIndex> get(String id) {
        ResponseEntity<CompanyDetails> response = restTemplate.getForEntity(DETAILS_URL, CompanyDetails.class, id);
        CompanyDetails indexes = response.getBody();
        return indexes.companyIndexes.stream()
                .map(json -> getIndex(json))
                .collect(Collectors.toList());
    }

    private CompanyIndex getIndex(String object) {
        List<String> values = Arrays.asList(object.replace("{", "")
                .replace("}", "")
                .split(","));
        CompanyIndex companyIndex = new CompanyIndex();
        companyIndex.id = getString("id", values);
        parseDoubleValues(values, companyIndex);
        parseLongValues(values, companyIndex);
        companyIndex.status = getStatus(values);
        companyIndex.type = getType(values);
        return companyIndex;

    }

    private void parseLongValues(List<String> values, CompanyIndex companyIndex) {
        companyIndex.totalAnswered = getLong("totalAnswered", values);
        companyIndex.totalComplains = getLong("totalComplains", values);
        companyIndex.totalComplains30 = getLong("totalComplains30", values);
        companyIndex.totalEvaluated = getLong("totalEvaluated", values);
        companyIndex.totalNotAnswered = getLong("totalNotAnswered", values);
    }

    private void parseDoubleValues(List<String> values, CompanyIndex companyIndex) {
        companyIndex.answeredPercentual = getDouble("answeredPercentual", values);
        companyIndex.averageAnswerTime = getDouble("averageAnswerTime", values);
        companyIndex.averageAnswerTime3M = getDouble("averageAnswerTime3M", values);
        companyIndex.consumerScore = getDouble("consumerScore", values);
        companyIndex.dealAgainPercentual = getDouble("dealAgainPercentual", values);
        companyIndex.finalScore = getDouble("finalScore", values);
        companyIndex.solvedPercentual = getDouble("solvedPercentual", values);
    }


    private IndexType getType(List<String> values) {
        String status = getString("type", values);
        if(status != null) {
            return IndexType.valueOf(status.trim());
        }
        return null;
    }

    private CompanyStatus getStatus(List<String> values) {
        String status = getString("status", values);
        if(status != null) {
            return CompanyStatus.valueOf(status.trim());
        }
        return null;
    }

    private Long getLong(String key, List<String> values) {
        return Long.valueOf(getString(key, values).trim());
    }

    private Double getDouble(String key, List<String> values) {
        return Double.valueOf(getString(key, values));
    }

    private String getString(String key, List<String> values) {
        return values.stream().filter(v -> v.trim().startsWith(key))
                .map(v -> getValue(key, v))
                .findFirst()
                .orElse(null);
    }

    private String getValue(String key, String v) {
        return v.replace(key + "=", "");
    }
}
