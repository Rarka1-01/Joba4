package main.java;

import javafx.scene.image.Image;

public class Dog extends Animal
{
    public static int count = 0;
    public static int timeLive = 5;
    public static Image icon = new Image("/main/resources/dog.png");

    Dog(int index)
    {
        super(index);
    }

    @Override
    public boolean isDeath()
    {
        this.liveTime++;
        return this.liveTime == Dog.timeLive;
    }

    @Override
    public char getType()
    {
        return 'D';
    }
}
