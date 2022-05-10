package main.java;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

abstract class BaseAI extends Thread
{
    protected ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    protected final ArrayList<ImageView> animals;
    protected final int speed;

    public BaseAI(ArrayList<ImageView> animal, final int speed)
    {
        this.speed = speed;
        this.animals = animal;
    }

    public void pauseThread()
    {
        this.scheduler.shutdown();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        new Thread(this).start();
    }

    public void continueThread()
    {
        synchronized (this.animals)
        {
            this.animals.notify();
        }
    }

    protected void move()
    {
        for (var i : this.animals)
        {
            if (i.getLayoutX() < 455 && i.getLayoutY() <= 90)
                i.setLayoutX(i.getLayoutX() + speed);
            else if (i.getLayoutY() < 480 && i.getLayoutX() >= 455)
                i.setLayoutY(i.getLayoutY() + speed);
            else if (i.getLayoutX() > 20 && i.getLayoutY() >= 480)
                i.setLayoutX(i.getLayoutX() - speed);
            else if (i.getLayoutY() > 90 && i.getLayoutX() <= 455)
                i.setLayoutY(i.getLayoutY() - speed);
        }
    }
}
