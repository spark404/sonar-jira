package org.sonar.plugins.jira.rest;

public class CreateJiraIssueResponse {
    private String id;
    private String key;
    private String self;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getSelf() {
        return self;
    }
}
