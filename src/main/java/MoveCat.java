package main.java;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MoveCat extends BaseAI
{
    private final ArrayList<ImageView> cats;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final int speed = 8;

    public MoveCat(ArrayList<ImageView> cats)
    {
        this.cats = cats;
    }

    public void pauseThread()
    {
       this.scheduler.shutdown();
       this.scheduler = Executors.newSingleThreadScheduledExecutor();
       new Thread(this).start();
    }

    public void continueThread()
    {
        synchronized (cats)
        {
            cats.notifyAll();
        }
    }

    @Override
    public void run()
    {
        synchronized (this.cats)
        {
            try {
                cats.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            scheduler.scheduleAtFixedRate(() -> {
                try
                {
                    for (var i : this.cats)
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

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }, 0, 50, TimeUnit.MILLISECONDS);
        }

    }
}
