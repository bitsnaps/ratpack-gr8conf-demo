import app.FakeRemoteProductService
import app.Product
import app.ProductService
import static ratpack.jackson.Jackson.json

import static ratpack.groovy.Groovy.ratpack

ratpack {

  serverConfig {
    threads(1)
    development(false)
  }

  bindings {
    // instruct Guice for DI (like: whenever I try to inject ProductService do inject FakeRemoteProductService
    bind(ProductService, FakeRemoteProductService)
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
   get('products/:id') { ProductService productService ->
     productService.findProductById(pathTokens.id).onNull({
       response.status(404)
       render([message:'Not found'])
     }).then({ Product product ->
       render(json(product))
     })
    }

//    files { dir "public" }
  }
}
