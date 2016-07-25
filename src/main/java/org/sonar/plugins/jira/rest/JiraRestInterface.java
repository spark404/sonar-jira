package org.sonar.plugins.jira.rest;


import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.cxf.jaxrs.client.WebClient;
import org.sonar.api.config.Settings;

import org.sonar.plugins.jira.JiraConstants;

import java.util.ArrayList;
import java.util.List;

public class JiraRestInterface {

    private final WebClient client;

    public JiraRestInterface(String baseUrl, String username, String password) {
        List<Object> providers = new ArrayList<Object>();
        providers.add( new JacksonJaxbJsonProvider() );

        client = WebClient.create("https://jira.schubergphilis.com/rest/api/2", providers, username, password, null);
        client.accept("application/json");
        client.type("application/json");
    }

    public MySelf getMySelf() {

        return client.path("/myself").get(MySelf.class);
    }

    public String createIssue(Settings settings, String summary, String description, String priority) {
        JiraIssue jiraIssue = new JiraIssue();
        jiraIssue.getFields().put("project", new Identifier(settings.getString(JiraConstants.JIRA_PROJECT_KEY_PROPERTY)));
        jiraIssue.getFields().put("issuetype", new Identifier(settings.getString(JiraConstants.JIRA_ISSUE_TYPE_ID)));
        jiraIssue.getFields().put("summary", summary);
        jiraIssue.getFields().put("priority", new Identifier(priority));
        jiraIssue.getFields().put("description", description);
        //jiraIssue.getFields().put("customfield_10700", "external issue id");

        CreateJiraIssueResponse response = client.path("/issue").post(jiraIssue, CreateJiraIssueResponse.class);
        return response.getKey();
    }

    public JiraIssue retrieveIssue(Settings settings, String issueId) {
        return client.path("/issue/" + issueId).get(JiraIssue.class);
    }
}
