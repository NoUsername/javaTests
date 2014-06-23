## JavaTests

### Compare the memory usage of different application technologies

Go into the individual folders and run:

	gradle all
	# custom task that runs the tasks: build idea installApp*
	# *installApp is only run for non-springBoot projects

You get an easily runnable version, just go into
	
	./build/install/[projectName]/bin/

and run the appropriate launcher script, then check the process manager for the resource usage of the newest java process.



