package com.zjw.websocket.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;
import java.util.ResourceBundle;

@Component
public class CDNConfig implements ApplicationListener<ContextRefreshedEvent> {

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        WebApplicationContext webApplicationContext = (WebApplicationContext) contextRefreshedEvent.getApplicationContext();
        ServletContext sc = webApplicationContext.getServletContext();
        ResourceBundle resource = ResourceBundle.getBundle("cdn");
        for (Object key : resource.keySet()) {
			assert sc != null;
			sc.setAttribute((String) key, resource.getString((String) key));
        }
    }
}
