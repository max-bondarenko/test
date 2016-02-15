package org.max.spring.data.config;

import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;

/**
 * Created by Maksym_Bondarenko on 2/11/2016.
 */
public class RepRepositoryConfigurationExtensionSupport extends RepositoryConfigurationExtensionSupport {
    @Override
    protected String getModulePrefix() {
        return "druid";
    }

    @Override
    public String getRepositoryFactoryClassName() {
        return "Cant be defined in Annotation only";
    }
}
