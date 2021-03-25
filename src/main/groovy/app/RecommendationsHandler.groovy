package app

import com.fasterxml.jackson.databind.ObjectMapper
import ratpack.exec.Promise
import ratpack.exec.util.SerialBatch
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.client.HttpClient
import ratpack.http.client.ReceivedResponse

import javax.inject.Inject

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

    @Inject
    RecommendationsHandler(HttpClient http, ObjectMapper mapper) {
        this.http = http
        this.mapper = mapper
    }

    @Override
    void handle(Context ctx) throws Exception {
        def promises = IDS.collect { id -> prepareHttpConnection(id) }
        SerialBatch.of(promises)
            .yield()
            .then({ def products ->
                ctx.render(json( products ))
            })
    }

    private Promise<Product> prepareHttpConnection(String id){
        return http.get("http://localhost:5050/products/${id}".toURI()).mapIf(
                { def response -> response.status.is2xx() },
                { def response -> mapper.readValue(response.body.inputStream, Product) },
                { _ -> null}
        )
    }

}
