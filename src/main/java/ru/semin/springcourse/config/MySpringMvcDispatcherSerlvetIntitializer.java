package ru.semin.springcourse.config;


import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import org.springframework.web.filter.CharacterEncodingFilter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;


public class MySpringMvcDispatcherSerlvetIntitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected String  [] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup( ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        registerCharacterEncodingFilter(servletContext);
        registerHiddenFieldFilter(servletContext);
    }

    private void registerCharacterEncodingFilter(ServletContext servletContext) {
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");

    }
}