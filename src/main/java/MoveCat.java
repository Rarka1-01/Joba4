package main.java;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MoveCat extends BaseAI
{
    public MoveCat(ArrayList<ImageView> cats, final int speed)
    {
        super(cats, speed);
    }

    @Override
    public void run()
    {
        synchronized (this.animals)
        {
            try {
                this.animals.wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            scheduler.scheduleAtFixedRate(() -> {
                try
                {
                    this.move();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }, 0, 50, TimeUnit.MILLISECONDS);
        }

    }
}
