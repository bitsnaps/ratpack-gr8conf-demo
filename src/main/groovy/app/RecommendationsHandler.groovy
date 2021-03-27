package app

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import io.github.resilience4j.ratpack.circuitbreaker.CircuitBreakerTransformer
import ratpack.exec.Promise
import ratpack.exec.util.ParallelBatch
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.client.HttpClient
import javax.inject.Inject
import java.time.Duration

import static ratpack.jackson.Jackson.json

class RecommendationsHandler implements Handler {

    private static final List<String> IDS = [
            'PR-003',
            'PR-004',
            'PR-001',
            'PR-002'
    ]

    private final HttpClient http
    private final ObjectMapper mapper
    private final CircuitBreakerRegistry registry

    @Inject
    RecommendationsHandler(HttpClient http, ObjectMapper mapper, CircuitBreakerRegistry registry) {
        this.http = http
        this.mapper = mapper
        this.registry = registry
    }

    @Override
    void handle(Context ctx) throws Exception {
        def promises = IDS.collect { id -> prepareHttpConnection(id) }
//      We use ParallelBatch which is much faster than SerialBatch for parallel request handling
        ParallelBatch.of(promises)
//            .yield()
            .publisher().toList() // filter all NULLs
            .then({ def products ->
                ctx.render(json( products ))
            })
    }

    private Promise<Product> prepareHttpConnection(String id){
        return http.get("http://localhost:5050/products/${id}".toURI(), { def request ->
            request.readTimeout(Duration.ofMillis(500))
        }).mapIf(
                { def response -> response.status.is2xx() },
                { def response -> mapper.readValue(response.body.inputStream, Product) },
                { _ -> null}

        ).transform(
            // Transform existing promise to some another promise
            CircuitBreakerTransformer.of(registry.circuitBreaker(id))
        )
                .onError { def error ->
            println(error.message)
        }
    }

}
