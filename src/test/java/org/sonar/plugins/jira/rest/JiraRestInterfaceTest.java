package org.sonar.plugins.jira.rest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.sonar.api.config.Settings;
import org.sonar.plugins.jira.JiraConstants;

import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.*;

@Ignore("Only useful with a real jira at the moment")
public class JiraRestInterfaceTest {

    private JiraRestInterface restInterface;
    private Settings testSettings;
    private Properties testProperties;

    @Before
    public void setUp() throws Exception {


        testProperties = new Properties();
        InputStream propertyStream = this.getClass().getResourceAsStream("/test.properties");
        assertNotNull(propertyStream);
        testProperties.load(propertyStream);

        // Provide a local override
        propertyStream = this.getClass().getResourceAsStream("/test-local.properties");
        if (propertyStream != null) {
            testProperties.load(propertyStream);
        }

        testSettings = new Settings();
        testSettings.addProperties(testProperties);

        final String jiraUrl = testSettings.getString(JiraConstants.SERVER_URL_PROPERTY) + testSettings.getString(JiraConstants.SOAP_BASE_URL_PROPERTY);
        restInterface = new JiraRestInterface(jiraUrl, testSettings.getString(JiraConstants.USERNAME_PROPERTY), testSettings.getString(JiraConstants.PASSWORD_PROPERTY));
    }

    @Test
    public void selfTest() {
        MySelf response = restInterface.getMySelf();
        assertEquals(testProperties.getProperty(JiraConstants.USERNAME_PROPERTY), response.getKey());
        System.out.println(response.getDisplayName());
    }

    @Test
    public void retrieveIssueTest() {
        JiraIssue response = restInterface.retrieveIssue(testSettings, testProperties.getProperty("sonar.jira.test.issuekey"));
        System.out.println(response.getFields());
    }

    @Test
    @Ignore("Will create an issue, use sparingly")
    public void createIssueTest() {
        String issueId = restInterface.createIssue(testSettings, "summary", "description", "1");
        System.out.println(issueId);
    }

}