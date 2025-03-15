package com.SideQuest_app.domain;

public interface SupportedEnvironments {
    String DEVELOPMENT = "development";
    String PRODUCTION = "production";
    String NON_PRODUCTION = "!production";
    String NON_PRODUCTION_STAGING = "!production & !staging";
    String TEST = "test";
    String STAGING = "staging";
    String CUSTOM = "custom";
}
