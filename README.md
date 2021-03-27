Ratpack Non Blocking Demo (example from [gr8conf by Szymon Stepniak](https://github.com/wololock/ratpack-gr8conf-demo))
-----------------------------

This project demonstrates:

* Dependency Injection with Guice
* Non blocking request (using Promise)
* Fake latency service
* Circuit Breaker simple usage.

You can start the project with:

    ./gradlew run -t

### Multiple Request Handeling

You can benchmark this app using Apache Benchmark tool (ab)
    
    ab -c 3 -n 10 http://localhost:5050/products/PR-001

This will perform 10 requests.

You can use `siege` tool as following:

    siege -c 10 -r 1 http://localhost:5050/products/PR-001
