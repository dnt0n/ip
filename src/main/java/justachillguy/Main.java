package justachillguy;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import justachillguy.JustAChillGuy;
import justachillguy.MainWindow;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private JustAChillGuy justAChillGuy = new JustAChillGuy();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDuke(justAChillGuy);  // inject the Duke instance
            stage.setTitle("✨ Just A Chill Guy ✨");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
