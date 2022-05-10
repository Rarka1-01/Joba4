package main.java;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;

public class Main extends Application
{
    final int N1 = 5;
    final int N2 = 3;
    final int P1 = 50;
    final int P2 = 40;

    Habitat h = new Habitat(512, 640, N2, N1, P2, P1);
    ModalWindow mW = new ModalWindow(250,250);
    ModalWindow_2 mW_2 = new ModalWindow_2(250,250);
    Timer mTimer = new Timer();
    MoveCat mC = new MoveCat(h.cats, 8);
    MoveDog mD = new MoveDog(h.dogs, 10);
    int daly_time = 0;
    ClientPart cl = new ClientPart(h);
    boolean off_sim = true;

    public void start_move()
    {
        mC.start();
        mD.start();
    }

    public void start_spawn_animal()
    {
        if(daly_time == 0)
        {
            h.daly_time_sim.setText("Time: 0");
        }

        mTimer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                Platform.runLater(() ->
                {
                    h.update(daly_time);
                    daly_time++;
                    h.daly_time_sim.setText("Time: " + daly_time);
                });
            }
        }, 1000, 1000);
    }

    public void pause_spawn_animal()
    {
        mTimer.cancel();
        mTimer = new Timer();
    }

    public void stop_spawn_animal()
    {
        pause_spawn_animal();

        h.daly_time_sim.setText("Time: 0");

        h.root.getChildren().removeAll(Singleton.getAnimalsImage());

        Dog.count = 0;
        Cat.count = 0;
        h.count_cat = 0;
        h.count_dog = 0;
        Singleton.clear();
        daly_time = 0;
    }

    public void setting_button(Stage primStage)
    {
        h.start_timer.setOnAction(actionEvent ->
        {
            h.chance_cat = Integer.parseInt(h.chance_cat_combo.getValue());
            h.chance_dog = Integer.parseInt(h.chance_dog_combo.getValue());

            h.chance_cat_combo.setDisable(true);
            h.chance_dog_combo.setDisable(true);

            try
            {
                h.time_cat = Integer.parseInt(h.textF_time_cat.getText());

                if(h.time_cat <= 0)
                    throw new Error();
            }
            catch(Throwable t)
            {
                h.sayErrorCatTime();
                h.time_cat = 5;
                h.textF_time_cat.setText("5");
            }

            try
            {
                h.time_dog = Integer.parseInt(h.textF_time_dog.getText());

                if(h.time_dog <= 0)
                    throw new Error();
            }
            catch(Throwable t)
            {
                h.sayErrorDogTime();
                h.time_dog = 3;
                h.textF_time_dog.setText("3");
            }

            try
            {
                int time_cat = Integer.parseInt(h.textF_timeL_cat.getText());

                if(time_cat <= 0)
                    throw new Error();

                Cat.timeLive = time_cat;
            }
            catch(Throwable t)
            {
                h.sayErrorCatTime();
                Cat.timeLive = 9;
                h.textF_timeL_cat.setText("9");
            }

            try
            {
                int time_dog = Integer.parseInt(h.textF_timeL_dog.getText());

                if(time_dog <= 0)
                    throw new Error();

                Dog.timeLive = time_dog;
            }
            catch(Throwable t)
            {
                h.sayErrorDogTime();
                Dog.timeLive = 5;
                h.textF_timeL_dog.setText("5");
            }



            h.textF_time_cat.setEditable(false);
            h.textF_time_dog.setEditable(false);
            h.textF_timeL_cat.setEditable(false);
            h.textF_timeL_dog.setEditable(false);

            off_sim = !off_sim;
            start_spawn_animal();
            h.start_timer.setDisable(true);
            h.stop_timer.setDisable(false);
            h.start_sim_on_menu.setDisable(true);
            h.stop_sim_on_menu.setDisable(false);
            h.show_Info.setDisable(false);
        });

        h.stop_timer.setOnAction(actionEvent ->
        {
            if(h.check_modal.isSelected())
            {
                pause_spawn_animal();

                mW.info.setText("Time simulate: " + daly_time + "\nCount dogs: " +
                        Dog.count + "\nCount cats: " + Cat.count);

                Group root_mw = new Group(mW.ok, mW.cancel, mW.info);
                Scene scene_mw = new Scene(root_mw, mW.wd, mW.hg);

                Stage stage_mw = new Stage();
                stage_mw.setTitle("Info Window");
                stage_mw.setScene(scene_mw);
                stage_mw.initModality(Modality.WINDOW_MODAL);
                stage_mw.initOwner(primStage);
                stage_mw.setResizable(false);

                mW.stage_mw = stage_mw;
                stage_mw.show();
            }
            else
            {
                stop_spawn_animal();
                off_sim = !off_sim;
                h.start_timer.setDisable(false);
                h.stop_timer.setDisable(true);
                h.start_sim_on_menu.setDisable(false);
                h.stop_sim_on_menu.setDisable(true);
                h.chance_cat_combo.setDisable(false);
                h.chance_dog_combo.setDisable(false);
                h.show_Info.setDisable(false);
            }
        });

        h.on_time_daly.setOnAction(actionEvent ->
        {
            h.daly_time_sim.setVisible(true);
            h.show_time_on_menu.setSelected(true);
        });

        h.off_time_daly.setOnAction(actionEvent ->
        {
           h.daly_time_sim.setVisible(false);
           h.hide_time_on_menu.setSelected(true);
        });

        h.show_time_on_menu.setOnAction(actionEvent -> h.on_time_daly.fire());

        h.hide_time_on_menu.setOnAction(actionEvent -> h.off_time_daly.fire());

        h.start_sim_on_menu.setOnAction(actionEvent -> h.start_timer.fire());

        h.stop_sim_on_menu.setOnAction(actionEvent -> h.stop_timer.fire());

        h.check_modal.setOnAction(actionEvent -> h.show_modal_Win_on_menu.setSelected(h.check_modal.isSelected()));

        h.show_modal_Win_on_menu.setOnAction(actionEvent -> h.check_modal.setSelected(h.show_modal_Win_on_menu.isSelected()));

        h.show_Info.setOnAction(actionEvent ->
        {
            pause_spawn_animal();

            Group root_mW_2 = new Group(mW_2.info, mW_2.ok);
            Scene scene_mw_2 = new Scene(root_mW_2, mW_2.wd, mW_2.hg);

            StringBuilder s = new StringBuilder("Information:\n");

            for (var i: Singleton.getLiveObject().keySet())
            {
                s.append("Object: ").append(i.getType()).append("\nIndex: ").append(i.getIndex()).append("\n--------\n\n");
            }

            mW_2.info.setText(s.toString());

            Stage stage_mw_2 = new Stage();
            stage_mw_2.setScene(scene_mw_2);
            stage_mw_2.initModality(Modality.WINDOW_MODAL);
            stage_mw_2.initOwner(primStage);
            stage_mw_2.setResizable(false);
            stage_mw_2.setTitle("Actual info");
            mW_2.st = stage_mw_2;
            stage_mw_2.show();
        });

        h.start_cat.setOnAction(actionEvent -> mC.continueThread());

        h.stop_cat.setOnAction(actionEvent -> mC.pauseThread());

        h.start_dog.setOnAction(actionEvent -> mD.continueThread());

        h.stop_dog.setOnAction(actionEvent -> mD.pauseThread());

        h.priority_cat.setOnAction(actionEvent -> {mC.setPriority(8); mD.setPriority(2);});

        h.priority_dog.setOnAction(actionEvent -> {mC.setPriority(2); mD.setPriority(8);});

        h.i_want_be_a_cat.setOnAction(actionEvent -> {
           try
           {
              cl.reQuest();
           }
           catch (Exception e)
           {
               System.out.println(e.getMessage());
           }
        });

        mW.ok.setOnAction(actionEvent ->
        {
            stop_spawn_animal();

            h.chance_cat_combo.setDisable(false);
            h.chance_dog_combo.setDisable(false);

            h.textF_time_cat.setEditable(true);
            h.textF_time_dog.setEditable(true);
            h.textF_timeL_cat.setEditable(true);
            h.textF_timeL_dog.setEditable(true);

            off_sim = !off_sim;
            h.start_timer.setDisable(false);
            h.stop_timer.setDisable(true);
            h.start_sim_on_menu.setDisable(false);
            h.stop_sim_on_menu.setDisable(true);
            h.show_Info.setDisable(true);
            mW.stage_mw.close();
        });

        mW.cancel.setOnAction(actionEvent ->
        {
            start_spawn_animal();
            mW.stage_mw.close();
        });



        mW_2.ok.setOnAction(actionEvent ->
        {
            start_spawn_animal();
            mW_2.st.close();
        });
    }

    @Override
    public void start(final Stage primaryStage) throws Exception
    {
        Group root = new Group(h.daly_time_sim, h.start_timer,
                h.stop_timer, h.check_modal, h.on_time_daly,
                h.off_time_daly, h.menubar, h.t_s_cat,
                h.t_s_dog, h.ch_s_cat, h.ch_s_dog,
                h.chance_cat_combo, h.chance_dog_combo,
                h.textF_time_cat, h.textF_time_dog, h.time_l_cat,
                h.time_l_dog, h.textF_timeL_dog, h.textF_timeL_cat,
                h.show_Info, h.priority_cat, h.priority_dog,
                h.i_want_be_a_cat);

        setting_button(primaryStage);
        start_move();

        Scene scene = new Scene(root, h.wd, h.hg);

        h.scene = scene;
        h.root = root;
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulate");

        scene.setOnKeyTyped(keyEvent -> {
            switch (keyEvent.getCharacter().toUpperCase()) {
                case "B", "И" -> {
                    if(off_sim)
                        h.start_timer.fire();
                }
                case "E", "У" -> {
                    if(!off_sim)
                        h.stop_timer.fire();
                }
                case "T", "Е" ->
                        {
                            if(h.on_time_daly.isSelected())
                                h.off_time_daly.fire();
                            else
                                h.on_time_daly.fire();
                        }
                case "\u001B" -> System.exit(0); //esc
                default -> System.out.println("Error " + keyEvent.getCharacter());
            }
        });

        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(windowEvent -> System.exit(0));

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}