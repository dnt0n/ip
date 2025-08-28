package justachillguy;

public class JustAChillGuyException extends Exception {
    public JustAChillGuyException() {
        super("Oops! There are some errors.");
    }

    public JustAChillGuyException(String msg) {
        super(msg);
    }
}
