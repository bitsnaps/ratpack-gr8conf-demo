import app.FakeRemoteProductService
import app.ProductHandler
import app.ProductService
import app.RecommendationsHandler
import io.github.resilience4j.ratpack.Resilience4jModule

import static ratpack.groovy.Groovy.ratpack

ratpack {

  serverConfig {
    threads(1)
    development(false)
  }

  bindings {
    // instruct Guice for DI (like: whenever I try to inject ProductService do inject FakeRemoteProductService
    bind(ProductHandler)
    bind(RecommendationsHandler)
    bind(ProductService, FakeRemoteProductService)
    module(Resilience4jModule)
  }

  handlers {

/*/ Blocking request GET handler
   get('products/:id') { ProductService productService ->
     final Product product = productService.findProductById(pathTokens.id)
     if (product){
       render json(product)
     } else {
       response.status(404)
       render json([message: 'Not found'])
     }
    }
*/
   get 'products/:id', ProductHandler

   get 'recommendations', RecommendationsHandler

    get {
      render('Hello Ratpack!')
    }
//    files { dir "public" }
  }
}
