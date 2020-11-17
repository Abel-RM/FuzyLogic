package ProyectoFuzzy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static ProyectoFuzzy.Controller.iniciarFuzzyLogic;
public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("vista.fxml"));
        primaryStage.setTitle("Proyecto Logica Difusa");
        primaryStage.setScene(new Scene(root, 600, 453));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        iniciarFuzzyLogic();
        launch(args);
    }
}
