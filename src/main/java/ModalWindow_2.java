package main.java;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ModalWindow_2
{
    public ModalWindow_2(int wd, int hg)
    {
        this.wd = wd;
        this.hg = hg;

        this.ok = new Button("Ok");
        this.ok.setLayoutX(10);
        this.ok.setLayoutY(165);
        this.ok.setPrefSize(230, 65);

        this.info = new TextArea("");
        this.info.setLayoutX(10);
        this.info.setLayoutY(10);
        this.info.setPrefSize(230, 150);
        this.info.setEditable(false);
    }

    public int wd;
    public int hg;

    public Button ok;
    public TextArea info;
    public Stage st;
}
