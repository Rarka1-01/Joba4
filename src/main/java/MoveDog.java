package main.java;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MoveDog extends BaseAI
{
    private final ArrayList<ImageView> dogs;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final int speed = 10;

    public MoveDog(ArrayList<ImageView> dogs)
    {
        this.dogs = dogs;
    }

    public void pauseThread()
    {
        this.scheduler.shutdown();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        new Thread(this).start();
    }

    public void continueThread()
    {
        synchronized (dogs)
        {
            dogs.notifyAll();
        }
    }

    @Override
    public void run()
    {
        synchronized (this.dogs)
        {
            try {
                dogs.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            scheduler.scheduleAtFixedRate(() -> {
                try
                {
                    for (var i : this.dogs)
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
