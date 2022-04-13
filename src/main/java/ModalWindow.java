package main.java;

import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModalWindow
{
    ModalWindow(int width, int height)
    {
        this.wd = width;
        this.hg = height;

        this.ok = new Button("Ok\n(Stop\nsimulate)");
        this.ok.setLayoutX(20);
        this.ok.setLayoutY(150);
        this.ok.setPrefSize(95,95);

        this.cancel = new Button("Cancel\n(Continue\nsimulate)");
        this.cancel.setLayoutX(135);
        this.cancel.setLayoutY(150);
        this.cancel.setPrefSize(95,95);

        this.info = new TextArea("");
        this.info.setLayoutX(20);
        this.info.setLayoutY(30);
        this.info.setPrefSize(210, 110);
        this.info.setEditable(false);
    }

    public int wd;
    public int hg;

    public Button ok;
    public Button cancel;

    public TextArea info;

    public Stage stage_mw;
}