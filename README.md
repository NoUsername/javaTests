## JavaTests

### Compare the memory usage of different application technologies

Go into the individual folders and run:

	gradle all
	# custom task that runs the tasks: build idea installApp*
	# *installApp is only run for non-springBoot projects
	
	gradle run

You also get an easily runnable version, just go into
	
	./build/install/[projectName]/bin/

and run the appropriate launcher script, then check the process manager for the resource usage of the newest java process.



### Results:

Done on Windows 7, 64 bit JDK 1.7.0_60

* springBoot (no web) ~ 70MB
* springWithCamelMq ~ 90MB
* springWithSimpleMqtt ~ 50MB
* springWithJettySpringMvcFull ~ 95MB