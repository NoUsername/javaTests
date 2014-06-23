package at.paukl.javatest;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mqtt.MQTTComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig extends SingleRouteCamelConfiguration {

    @Autowired DummyService dummyService;

    /**
     * Returns the CamelContext which support OSGi
     */
    @Override
    protected CamelContext createCamelContext() throws Exception {
        DefaultCamelContext context = new DefaultCamelContext();
        return context;
    }

    @Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {
        MQTTComponent mqtt = new MQTTComponent();
        camelContext.addComponent("mqtt", mqtt);

        // register other listeners....
    }


    public static class SomeBean {

        public void someMethod(String body) {
            System.out.println("Received: " + body);
        }

    }

    @Bean
    @Override
    public RouteBuilder route() {
        return new RouteBuilder() {
            public void configure() {

                // listen for files in directory and publish to mosquitto
                from("file:src/data?noop=true").to("mqtt:test?publishTopicName=lolwat");

                // mosquitto listener to file output
                from("mqtt:test?subscribeTopicName=foo").
                        to("file://target/test?noop=true");

                // mosquitto listener to bean method
                from("mqtt:test2?subscribeTopicName=bar").
                        bean(dummyService, "handleMessage");
            }
        };
    }

}