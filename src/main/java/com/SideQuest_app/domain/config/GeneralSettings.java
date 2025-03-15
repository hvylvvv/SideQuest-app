package com.SideQuest_app.domain.config;

import com.SideQuest_app.domain.SupportedEnvironments;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application.settings")
public class GeneralSettings {
    private General general;

    private String[] admins;

    @Data
    public static class General {
        private Info info;
        private Spring spring;

        @Data
        public static class Info {
            private String applicationVersion;
            private String applicationName;
        }

        @Data
        public static class Spring {
            private String profile;

            public boolean isNonProduction() {
                return !isProduction();
            }

            public boolean isProduction() {
                return acceptsProfiles(SupportedEnvironments.PRODUCTION);
            }

            public boolean acceptsProfiles(String... profiles) {
                return ArrayUtils.contains(profiles, profile);
            }
        }
    }
}
