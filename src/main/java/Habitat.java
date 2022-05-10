package main.java;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Habitat
{
    public static boolean draw_cat = true;
    public static boolean draw_dog = true;

    Habitat(int width, int height, int time_dog, int time_cat, int chance_dog, int chance_cat)
    {
        this.wd = width;
        this.hg = height;
        this.time_dog = time_dog;
        this.time_cat = time_cat;
        this.chance_dog = chance_dog;
        this.chance_cat = chance_cat;
        this.count_cat = 0;
        this.count_dog = 0;

        this.setting_Label();

        this.setting_Button();

        this.setting_CheckBox();

        this.gr_time_daly = new ToggleGroup();
        this.priority = new ToggleGroup();

        this.setting_RadioButton();

        this.setting_MenuBar();

        this.setting_ComboBox();

        this.setting_TextField();
    }

    public void update(int d_t)
    {
        int prev_cat = this.count_cat;
        int prev_dog = this.count_dog;

        this.dt = d_t;

        Singleton.checkLiveObject(this.root, this);


        if (d_t % this.time_dog == 0 && Math.random() * 100 <= this.chance_dog && this.dsp)
        {
            Singleton.addAnimal(new Dog(this.count_dog + this.count_cat + d_t), d_t);
            Dog.count++;
            this.count_dog++;
        }

        if (d_t % this.time_cat == 0 && Math.random() * 100 <= this.chance_cat && this.csp)
        {
            Singleton.addAnimal(new Cat(this.count_cat + this.count_dog + d_t), d_t);
            Cat.count++;
            this.count_cat++;
        }

        if(this.count_cat - prev_cat != 0 && this.csp)
        {
            ImageView nIamge_cat = new ImageView(Cat.icon);
            nIamge_cat.setLayoutX(Math.random() * 432 + 20);
            nIamge_cat.setLayoutY(Math.random() * 390 + 90);

            Singleton.getAnimalsImage().add(nIamge_cat);
            this.cats.add(nIamge_cat);

            if(draw_cat)
            {
                root.getChildren().add(nIamge_cat);
                scene.setRoot(root);
            }

        }

        if(this.count_dog - prev_dog != 0 && this.dsp)
        {
            ImageView nIamge_dog = new ImageView(Dog.icon);
            nIamge_dog.setLayoutX(Math.random() * 432 + 20);
            nIamge_dog.setLayoutY(Math.random() * 390 + 90);

            Singleton.getAnimalsImage().add(nIamge_dog);
            this.dogs.add(nIamge_dog);

            if(draw_dog)
            {
                root.getChildren().add(nIamge_dog);
                scene.setRoot(root);
            }
        }
    }

    public void sayErrorCatTime()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("In the field for entering time information for cats, invalid characters are replace.\nThe parameters are set by default");
        alert.showAndWait();
    }

    public void sayErrorDogTime()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("In the field for entering time information for dogs, invalid characters are replace.\nThe parameters are set by default");
        alert.showAndWait();
    }

    private void setting_MenuBar()
    {
        this.menubar = new MenuBar();
        this.menu_gr_time_daly = new ToggleGroup();
        this.menu_gr_cat = new ToggleGroup();
        this.menu_gr_dog = new ToggleGroup();

        this.play_sim = new Menu("Go");
        this.show_daly_time = new Menu("Time");
        this.show_mW = new Menu("Info");
        this.go_cat = new Menu("Cat");
        this.go_dog = new Menu("Dog");

        this.start_sim_on_menu = new MenuItem("Start simulate");
        this.stop_sim_on_menu = new MenuItem("Stop simulate");
        this.stop_sim_on_menu.setDisable(true);

        this.show_time_on_menu = new RadioMenuItem("Show current time");
        this.hide_time_on_menu = new RadioMenuItem("Hide current time");
        this.show_time_on_menu.setToggleGroup(this.menu_gr_time_daly);
        this.hide_time_on_menu.setToggleGroup(this.menu_gr_time_daly);
        this.hide_time_on_menu.setSelected(true);

        this.start_cat = new RadioMenuItem("Start move cats");
        this.stop_cat = new RadioMenuItem("Stop move cats");
        this.start_cat.setToggleGroup(this.menu_gr_cat);
        this.stop_cat.setToggleGroup(this.menu_gr_cat);
        this.stop_cat.setSelected(true);

        this.start_dog = new RadioMenuItem("Start move dogs");
        this.stop_dog = new RadioMenuItem("Stop move dogs");
        this.start_dog.setToggleGroup(this.menu_gr_dog);
        this.stop_dog.setToggleGroup(this.menu_gr_dog);
        this.stop_dog.setSelected(true);

        this.show_modal_Win_on_menu = new CheckMenuItem("Show info window");
        this.show_modal_Win_on_menu.setSelected(true);

        this.play_sim.getItems().addAll(this.start_sim_on_menu, this.stop_sim_on_menu);
        this.show_daly_time.getItems().addAll(this.show_time_on_menu, this.hide_time_on_menu);
        this.show_mW.getItems().add(this.show_modal_Win_on_menu);
        this.go_cat.getItems().addAll(this.start_cat, this.stop_cat);
        this.go_dog.getItems().addAll(this.start_dog, this.stop_dog);

        this.menubar.getMenus().addAll(this.play_sim, this.show_daly_time, this.show_mW, this.go_cat, this.go_dog);
    }

    private void setting_Label()
    {
        this.daly_time_sim = new Label("Time: 0");
        this.daly_time_sim.setLayoutX(7);
        this.daly_time_sim.setLayoutY(507);
        this.daly_time_sim.setFont(new Font("Consolas", 32));
        this.daly_time_sim.setTextFill(Color.web("#FF7777"));
        this.daly_time_sim.setVisible(false);

        this.t_s_cat = new Label("Cat spawn period:");
        this.t_s_cat.setLayoutX(10);
        this.t_s_cat.setLayoutY(36);
        this.t_s_cat.setFont(new Font("Consolas", 14));

        this.t_s_dog = new Label("Dog spawn period:");
        this.t_s_dog.setLayoutX(10);
        this.t_s_dog.setLayoutY(73);
        this.t_s_dog.setFont(new Font("Consolas",14));

        this.ch_s_cat = new Label("Cat chance spawn ↓");
        this.ch_s_cat.setLayoutX(265);
        this.ch_s_cat.setLayoutY(550);
        this.ch_s_cat.setFont(new Font("Consolas", 12));

        this.ch_s_dog = new Label("Dog chance spawn ↓");
        this.ch_s_dog.setLayoutX(265);
        this.ch_s_dog.setLayoutY(590);
        this.ch_s_dog.setFont(new Font("Consolas", 12));

        this.time_l_cat = new Label("Time of life cat: ");
        this.time_l_cat.setLayoutX(290);
        this.time_l_cat.setLayoutY(36);
        this.time_l_cat.setFont(new Font("Consolas", 14));

        this.time_l_dog = new Label("Time of life dog: ");
        this.time_l_dog.setLayoutX(290);
        this.time_l_dog.setLayoutY(73);
        this.time_l_dog.setFont(new Font("Consolas", 14));
    }

    private void setting_Button()
    {
        this.start_timer = new Button("Start");
        this.start_timer.setLayoutX(0);
        this.start_timer.setLayoutY(550);
        this.start_timer.setPrefWidth(90);
        this.start_timer.setPrefHeight(90);
        this.start_timer.setDisable(false);

        this.stop_timer = new Button("Stop");
        this.stop_timer.setLayoutX(422);
        this.stop_timer.setLayoutY(550);
        this.stop_timer.setPrefWidth(90);
        this.stop_timer.setPrefHeight(90);
        this.stop_timer.setDisable(true);

        this.show_Info = new Button("Show actual info");
        this.show_Info.setLayoutX(90);
        this.show_Info.setLayoutY(610);
        this.show_Info.setPrefSize(130, 10);
        this.show_Info.setDisable(true);

        this.i_want_be_a_cat = new Button("I\n\nc\na\nt");
        this.i_want_be_a_cat.setLayoutX(465);
        this.i_want_be_a_cat.setLayoutY(33);
        this.i_want_be_a_cat.setPrefSize(30, 100);
    }

    private void setting_CheckBox()
    {
        this.check_modal = new CheckBox("Show info window");
        this.check_modal.setLayoutX(90);
        this.check_modal.setLayoutY(550);
        this.check_modal.fire();
    }

    private void setting_RadioButton()
    {
        this.on_time_daly = new RadioButton("On current time");
        this.on_time_daly.setLayoutX(90);
        this.on_time_daly.setLayoutY(570);
        this.on_time_daly.setToggleGroup(this.gr_time_daly);

        this.off_time_daly = new RadioButton("Off current time");
        this.off_time_daly.setLayoutX(90);
        this.off_time_daly.setLayoutY(590);
        this.off_time_daly.fire();
        this.off_time_daly.setToggleGroup(this.gr_time_daly);

        this.priority_cat = new RadioButton("High\npriority");
        this.priority_cat.setLayoutX(210);
        this.priority_cat.setLayoutY(67);
        this.priority_cat.setToggleGroup(this.priority);

        this.priority_dog = new RadioButton("High\npriority");
        this.priority_dog.setLayoutX(210);
        this.priority_dog.setLayoutY(28);
        this.priority_dog.setToggleGroup(this.priority);
    }

    private void setting_ComboBox()
    {
        this.chance_cat_combo = new ComboBox<String>(FXCollections.observableArrayList("10", "20", "30", "40", "50", "60", "70", "80", "90", "100"));
        this.chance_cat_combo.setValue("40");
        this.chance_cat_combo.setLayoutX(320);
        this.chance_cat_combo.setLayoutY(565);

        this.chance_dog_combo = new ComboBox<String>(FXCollections.observableArrayList("10", "20", "30", "40", "50", "60", "70", "80", "90", "100"));
        this.chance_dog_combo.setValue("50");
        this.chance_dog_combo.setLayoutX(320);
        this.chance_dog_combo.setLayoutY(605);
    }

    private void setting_TextField()
    {
        this.textF_time_cat = new TextField("5");
        this.textF_time_cat.setLayoutX(150);
        this.textF_time_cat.setLayoutY(33);
        this.textF_time_cat.setPrefWidth(40);

        this.textF_time_dog = new TextField("3");
        this.textF_time_dog.setLayoutX(150);
        this.textF_time_dog.setLayoutY(70);
        this.textF_time_dog.setPrefWidth(40);

        this.textF_timeL_cat = new TextField("9");
        this.textF_timeL_cat.setLayoutX(420);
        this.textF_timeL_cat.setLayoutY(33);
        this.textF_timeL_cat.setPrefWidth(40);

        this.textF_timeL_dog = new TextField("5");
        this.textF_timeL_dog.setLayoutX(420);
        this.textF_timeL_dog.setLayoutY(70);
        this.textF_timeL_dog.setPrefWidth(40);
    }

    public int wd;
    public int hg;
    public int time_dog;
    public int time_cat;
    public int chance_dog;
    public int chance_cat;
    public int count_dog;
    public int count_cat;
    public int dt;
    public boolean dsp = true;
    public boolean csp = true;
    public ArrayList<ImageView> cats = new ArrayList<>();
    public ArrayList<ImageView> dogs = new ArrayList<>();

    public Scene scene;
    public Group root;
    public ToggleGroup gr_time_daly;
    public ToggleGroup priority;
    public ToggleGroup menu_gr_time_daly;
    public ToggleGroup menu_gr_cat;
    public ToggleGroup menu_gr_dog;

    public MenuBar menubar;

    public Menu play_sim;
    public Menu show_daly_time;
    public Menu show_mW;
    public Menu go_cat;
    public Menu go_dog;

    public MenuItem start_sim_on_menu;
    public MenuItem stop_sim_on_menu;
    public RadioMenuItem show_time_on_menu;
    public RadioMenuItem hide_time_on_menu;
    public RadioMenuItem start_cat;
    public RadioMenuItem stop_cat;;
    public RadioMenuItem start_dog;;
    public RadioMenuItem stop_dog;
    public CheckMenuItem show_modal_Win_on_menu;

    public RadioButton on_time_daly;
    public RadioButton off_time_daly;
    public RadioButton priority_cat;
    public RadioButton priority_dog;

    public Label daly_time_sim;
    public Label t_s_cat;
    public Label t_s_dog;
    public Label ch_s_dog;
    public Label ch_s_cat;
    public Label time_l_cat;
    public Label time_l_dog;

    public Button start_timer;
    public Button stop_timer;
    public Button show_Info;
    public Button i_want_be_a_cat;

    public CheckBox check_modal;

    public ComboBox<String> chance_dog_combo;
    public ComboBox<String> chance_cat_combo;

    public TextField textF_time_cat;
    public TextField textF_time_dog;
    public TextField textF_timeL_dog;
    public TextField textF_timeL_cat;
}