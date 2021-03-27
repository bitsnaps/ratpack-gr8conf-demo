import ratpack.groovy.Groovy
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest;
import ratpack.test.http.TestHttpClient;
import ratpack.test.ServerBackedApplicationUnderTest;
import org.junit.Test;
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import ratpack.test.MainClassApplicationUnderTest

class RatpackGroovySpec {

    ServerBackedApplicationUnderTest ratpackGroovyApp = new MainClassApplicationUnderTest(RatpackGroovyApp.class)
    @Delegate TestHttpClient client = TestHttpClient.testHttpClient(ratpackGroovyApp)

    @Test
    void "test if app is started"() {
        when:
        get('')

        then:
        assert response.statusCode == 200
        assert response.body.text == 'Hello Ratpack!'
    }

}
