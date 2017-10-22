public class Main {
    public static void main(String[] args) {

        Parser parser = new Parser();

        parser.parseFile(Main.class.getResource("example"));
    }
}
