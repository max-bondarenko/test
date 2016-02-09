package org.max.data.config;

import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;

import java.lang.Class;import java.lang.Override;import java.lang.String;import java.lang.annotation.Annotation;

/**
 * Created by Maksym_Bondarenko on 2/5/2016.
 */
public class Registrar extends RepositoryBeanDefinitionRegistrarSupport {
    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return Sim.class;
    }

    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return new RepositoryConfigurationExtensionSupport(){

            @Override
            public String getRepositoryFactoryClassName() {
                return null;
            }

            @Override
            protected String getModulePrefix() {
                return null;
            }
        };
    }
}
