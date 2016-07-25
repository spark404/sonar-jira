package org.sonar.plugins.jira.rest;

public class Identifier {
    private String id;

    public Identifier() {
    }

    public Identifier(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
