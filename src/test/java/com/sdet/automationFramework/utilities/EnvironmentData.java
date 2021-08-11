package com.sdet.automationFramework.utilities;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:properties/${env}.properties",
        "classpath:properties/env.properties"
})
//properties/e1.properties  classpath:${env}.properties
public interface EnvironmentData extends Config {

    String webURL();
    String browserName();
    String platform();
    String version();
    String execution();
}
