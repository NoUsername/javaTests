## JavaTests

Some projects to compare different Java technologies (mainly in terms of memory consumption and how code could look like that uses them).

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
Memory size given is the value of the "Working Set (Memory)" column of TaskManager.

Grouped by things that do (very roughly speaking) similar things:

* plainJava ~ 17MB
* guice ~ 32MB
* spring ~ 45MB 
* springBoot (no web) ~ 70MB

----

* springWithSimpleMqtt ~ 50MB
* springWithCamelMq ~ 90MB

----

* springWithJetty ~ 65MB
* springWithJettySpringMvc ~78MB

----

* springWithJettySpringMvcFull ~ 95MB

