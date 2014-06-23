package at.paukl.javatest;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MqStarter {

    //Logger log = LoggerFactory.getLogger(MqStarter.class);

    @Autowired
    CamelContext camelContext;

    @PostConstruct
    public void postConstruct() {
        if (camelContext != null && camelContext instanceof DefaultCamelContext) {
            try {
                loginfo("starting camelContext");
                camelContext.start();
                loginfo("camelContext started");
            } catch (Exception e) {
                logerror("could not start camelContext", e);
            }
        } else {
            loginfo("not starting camelContext");
        }
    }

    private void loginfo(String msg) {
        System.out.println(msg);
    }
    private void logerror(String msg, Throwable e) {
        System.out.println(msg);
        e.printStackTrace();
    }

}
