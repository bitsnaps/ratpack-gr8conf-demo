package app

import java.util.concurrent.ConcurrentHashMap
import java.util.function.Supplier

class FakeRemoteProductService implements ProductService {

    static final Map<String, Supplier<Product>> products =( [
            'PR-001': productWithFakeLatency(new Product('P001', 'Learning Ratpack', 34.99), 80),
            'PR-002': productWithFakeLatency(new Product('P002', 'Netty in Action', 39.99), 110),
            'PR-003': productWithFakeLatency(new Product('P003', 'Micronaut in Action', 35.99), 600),
            'PR-004': productWithFakeLatency(new Product('P004', 'Groovy in Action 2nd edt', 39.99), 1200),
            'PR-005': productWithFakeLatency(new Product('P005', 'Code Complete', 45.99), 1500)
    ] as ConcurrentHashMap<String, Supplier<Product>>)

    private static Supplier<Product> productWithFakeLatency(Product product, int latency){
        return {
            Thread.sleep(latency)
            return product
        } as Supplier<Product>
    }

    @Override
    Product findProductById(String id) {
        return products.getOrDefault(id, {} as Supplier).get()
    }
}
