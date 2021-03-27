public class RatpackGroovyApp {

    public static void main(String[] args) {
        File file = new File("src/ratpack/Ratpack.groovy");
        def shell = new GroovyShell()
        shell.evaluate(file)
    }

}
