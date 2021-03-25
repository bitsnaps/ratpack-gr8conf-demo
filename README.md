Ratpack project template
-----------------------------

You have just created a basic Groovy Ratpack application. It doesn't do much
at this point, but we have set you up with a standard project structure, a 
Guice back Registry, simple home page, and Spock for writing tests (because 
you'd be mad not to use it).

In this project you get:

* A Gradle build file with pre-built Gradle wrapper
* A tiny home page at src/ratpack/templates/index.html (it's a template)
* A routing file at src/ratpack/Ratpack.groovy
* Reloading enabled in build.gradle
* A standard project structure:

```
    <proj>
      |
      +- src
          |
          +- ratpack
          |     |
          |     +- Ratpack.groovy
          |     +- ratpack.properties
          |     +- public // Static assets in here
          |          |
          |          +- images
          |          +- lib
          |          +- scripts
          |          +- styles
          |
          +- main
          |   |
          |   +- groovy
                   |
                   +- // App classes in here!
          |
          +- test
              |
              +- groovy
                   |
                   +- // Spock tests in here!
```

That's it! You can start the basic app with

    ./gradlew run

but it's up to you to add the bells, whistles, and meat of the application.

### Multiple Request Handeling

You can benchmark this app using Apache Benchmark tool (ab)
    
    ab -c 3 -n 10 http://localhost:5050/products/PR-001

This will perform 10 requests.

You can use `siege` tool as following:

    siege -c 10 -r 1 http://localhost:5050/products/PR-001

