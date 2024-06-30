package org.shayvin.tourplanner.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.TourPlannerApp;

import java.io.IOException;
import java.util.Properties;

public class ConfigService {

    private static final Logger logger = LogManager.getLogger(OpenRouteService.class);

    public ConfigService() {

    }

    public static String loadKeyValuePair() {
        // Load key-value pair from file
        Properties properties = new Properties();
        try {
            logger.info("Loading API key...");
            properties.load(TourPlannerApp.class.getClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            logger.error("No API key found!");
        }
        logger.info("Success loading API key.");
        return properties.getProperty("apiKey");
    }
}
