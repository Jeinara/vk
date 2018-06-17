package shikimori.ui;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import shikimori.record.Cover;

import java.io.File;
import java.net.MalformedURLException;


public class UIcover {
    public static final String ADDRESS = "C:\\Users\\Svetlana\\Documents\\GitHub\\vk\\";

    private UI ui;
    private Cover cover;
    Pane pane;
    Label ep;
    Label time;
    ImageView img;


    UIcover(Cover cover, UI ui){
        this.cover = cover;
        Pane pane = new Pane();
        pane.setPrefSize(205,306);


        ImageView img = new ImageView();
        img.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ui.setEp(cover.getEp());
                ui.setName(cover.getName());
                ui.setTime(cover.getTime());
                ui.setCoverImg(cover.getPic());
                ui.setTextArea(cover.getDesc());
            }
        });
        img.setLayoutX(19);
        img.setLayoutY(14);
        img.setFitWidth(168);
        img.setFitHeight(235.5);
        img.setPreserveRatio(true);
        this.img = img;
        File file = new File(ADDRESS +cover.getPic());
        try {
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl);
            img.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Label ep = new Label();
        ep.setText(cover.getEp());
        ep.setLayoutX(19);
        ep.setLayoutY(264);
        this.ep = ep;

        Label time = new Label();
        time.setText(cover.getTime());
        time.setLayoutX(103);
        time.setLayoutY(264);
        this.time = time;

        pane.getChildren().addAll(img,ep,time);
        this.pane = pane;
    }

    public Pane setPane(){
        return pane;
    }
}
