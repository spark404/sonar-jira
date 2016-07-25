package org.sonar.plugins.jira.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedHashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class JiraIssue {
    Map<String, Object> fields;

    public JiraIssue() {
        fields = new LinkedHashMap<>();
    }

    public Map<String, Object> getFields() {
        return fields;
    }
}
