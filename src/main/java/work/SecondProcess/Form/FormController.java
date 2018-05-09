package work.SecondProcess.Form;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import work.SecondProcess.DataBase;
import work.SecondProcess.Main;
import work.record.Record;

import java.io.File;
import java.net.MalformedURLException;

public class FormController {
    @FXML
    private Button load;
    @FXML
    private Button clean;
    @FXML
    private ListView<String> postList = new ListView<>();
    @FXML
    private ComboBox<String> pictures;
    @FXML
    private ComboBox<String>  urls;
    @FXML
    private TextArea text;
    @FXML
    private ImageView image;
    @FXML
    private Label labPictures;
    @FXML
    private Label labUrls;
    private String address = "C:\\Users\\Svetlana\\Documents\\GitHub\\vk\\";

    public void onLoadButtonClick(){
        postList.getItems().clear();
        Main.fileContent.forEach((id,rec) -> postList.getItems().add(rec.getId()));
    }

    public void onCleanButtonClick(){
        try{
        File file = new File(address+"гадство_здесь");
        File[] listOfImages = file.listFiles();
        for ( int i = 0; i< listOfImages.length; i++){
            listOfImages[i].delete();
        }
        file = new File(address+"гадим_сюда.json");
        boolean b = file.delete();
        if(b){
        postList.getItems().clear();
        Main.fileContent.clear();
        Main.idInDB.clear();
        urls.getItems().clear();
        pictures.getItems().clear();
        labUrls.setText(labUrls.getText().substring(0,labUrls.getText().length()-1));
        labPictures.setText(labPictures.getText().substring(0,labPictures.getText().length()-1));
        image.setImage(null);
        text.setText("");}
        } catch (NullPointerException e){ System.out.println("Уже очищено");}
    }
    public void onImgChoose(){

        File file = new File(address +pictures.getSelectionModel().getSelectedItem());
        try {
            String localUrl = file.toURI().toURL().toString();
            Image img = new Image(localUrl);
            if(pictures.getSelectionModel().getSelectedItem() != null){image.setImage(img);}
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public void onFieldChoose(){
        Record rec = Main.fileContent.get(postList.getSelectionModel().getSelectedItem());
        text.setText(rec.getText());
        pictures.setItems(FXCollections.observableArrayList(rec.getImg()));
        labPictures.setText("Images in post:"+rec.getImg().size());
        urls.setItems(FXCollections.observableArrayList(rec.getHref()));
        labUrls.setText("Urls in post:"+rec.getHref().size());
        image.setImage(null);
    }
}
