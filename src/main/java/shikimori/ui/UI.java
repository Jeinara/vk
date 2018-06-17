package shikimori.ui;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import shikimori.record.Block;

import java.io.File;
import java.net.MalformedURLException;
import java.util.concurrent.ConcurrentMap;


public class UI extends Application {
    private ImageView coverImg;
    private Label time;
    private Label ep;
    private Label name;
    private TextArea textArea;

    public void setCoverImg(String coverImg) {
        File file = new File(UIcover.ADDRESS +coverImg);
        try {
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl);
            this.coverImg.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public void setTime(String time) {
        this.time.setText(time);
    }
    public void setEp(String ep) {
        this.ep.setText(ep);
    }
    public void setName(String name) {
        this.name.setText(name);
    }
    public void setTextArea(String textArea) { this.textArea.setText(textArea);}

    static ConcurrentMap<String,Block> block;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Ongoing");

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPosition(0,0.6822);
        splitPane.setPrefSize(930.0,602.0);
        Accordion blocks = new Accordion();

        for (String blockKey: block.keySet()){
            TitledPane titledPane = new TitledPane();
            int n = block.get(blockKey).getCovers().size();
            int c;
            int h = 0;
            if(n%3 != 0) {c = n/3+1;}
            else{c = n/3;}
            GridPane gridPane = new GridPane();
            ScrollPane scrollPane = new ScrollPane();
            String[] coverKey = block.get(blockKey).getCovers().keySet()
                    .toArray(new String[block.get(blockKey).getCovers().size()]);
            for(int i=1;i<=c;i++){
                int y = 3;
                if(((n/(i*3))==0)&&((i*3-n)%3!=0)){y=y-(i*3-n);};
                for(int j=1;j<=y;j++){
                    UIcover cover = new UIcover(block.get(blockKey).getCovers().get(coverKey[h]),this);
                    gridPane.add(cover.setPane(),j-1,i-1);
                    h++;
                }
            }
            scrollPane.setContent(gridPane);
            titledPane.setContent(scrollPane);
            titledPane.setText(block.get(blockKey).getName());
            blocks.getPanes().add(titledPane);
        }
        splitPane.getItems().addAll(blocks,createPane());
        primaryStage.setScene(new Scene(splitPane));
        primaryStage.show();
    }

    private Pane createPane(){
        Pane pane = new Pane();

        ImageView img = new ImageView();
        img.setPreserveRatio(true);
        img.setLayoutX(60);
        img.setLayoutY(14);
        img.setFitWidth(168);
        img.setFitHeight(235.5);
        coverImg = img;

        Label epS = new Label();
        epS.setText("Эпизод:");
        epS.setLayoutX(14);
        epS.setLayoutY(311);

        Label ep = new Label();
        ep.setText("");
        ep.setLayoutX(14);
        ep.setLayoutY(332);
        this.ep = ep;

        Label timeS = new Label();
        timeS.setText("Время выхода:");
        timeS.setLayoutX(145);
        timeS.setLayoutY(311);

        Label time = new Label();
        time.setText("");
        time.setLayoutX(145);
        time.setLayoutY(332);
        this.time = time;

        Label desc = new Label();
        desc.setText("Описание:");
        desc.setLayoutX(14);
        desc.setLayoutY(359);

        Label name = new Label();
        name.setText("Название:");
        name.setLayoutX(25);
        name.setLayoutY(273);
        name.setMaxWidth(230);
        this.name = name;

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setPrefSize(275,204);
        textArea.setLayoutX(7);
        textArea.setLayoutY(389);
        this.textArea = textArea;

        pane.getChildren().addAll(img,ep,epS,time,timeS,desc,textArea,name);
        return pane;
    };

    public static void main(String[] args,ConcurrentMap<String,Block> block) {
        UI.block = block;
        launch(args);
    }
}
