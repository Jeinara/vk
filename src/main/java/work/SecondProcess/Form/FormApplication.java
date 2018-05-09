package work.SecondProcess.Form;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class FormApplication extends Application {

    @FXML
    private ComboBox<String> pictures;

    @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("Post.fxml"));
            primaryStage.setTitle("Post");
            primaryStage.setScene(new Scene(root));
//            pictures.valueProperty().addListener(new ChangeListener<String>() {
//                @Override public void changed(ObservableValue ov, String t, String t1) {
//                    System.out.println(ov);
//                    System.out.println(t);
//                    System.out.println(t1);
//                }
//            });
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }

}
