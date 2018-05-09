package work.SecondProcess.Form;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import work.SecondProcess.Main;
import work.record.Record;

public class FormController {
    @FXML
    private Button load;
    @FXML
    private Button clean;
    @FXML
    private ListView<String> postList = new ListView<>();
    @FXML
    private ChoiceBox pictures;
    @FXML
    private ChoiceBox urls;
    @FXML
    private TextArea text;
    @FXML
    private ImageView image;

    public void onLoadButtonClick(){
        postList.getItems().clear();
        Main.fileContent.forEach((id,rec) -> postList.getItems().add(rec.getId()));
    }

    public void onCleanButtonClick(){

    };
    public void onFieldChoose(){
        Record rec = Main.fileContent.get(postList.getSelectionModel().getSelectedItem());
        text.setText(rec.getText());
    }
}
