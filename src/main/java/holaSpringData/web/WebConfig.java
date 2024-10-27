package holaSpringData.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Bean
        public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
            return new HiddenHttpMethodFilter();
        }

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es"));
        return slr;
    }
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("Lang");
        return lci;
    }
    @Override
    public void addInterceptors(InterceptorRegistry intercepto){
        intercepto.addInterceptor(localeChangeInterceptor());

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:upload/");

        // Manejador para recursos estáticos (CSS, JS, imágenes, etc.)
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");


    }

    @Override
    public void addViewControllers(ViewControllerRegistry ingreso){
        ingreso.addViewController("/").setViewName("indice");
        ingreso.addViewController("/ingreso");
        ingreso.addViewController("/errores/403").setViewName("errores/403");
    }
}
