package org.max.spring.data.config;

import org.max.spring.data.config.annotations.EnableDruidRepositories;
import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import java.lang.annotation.Annotation;

/**
 * Created by Maksym_Bondarenko on 2/5/2016.
 */
public class Registrar extends RepositoryBeanDefinitionRegistrarSupport {
    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableDruidRepositories.class;
    }

    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return new RepRepositoryConfigurationExtensionSupport();
    }
}
