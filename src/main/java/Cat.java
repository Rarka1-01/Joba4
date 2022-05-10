package main.java;

import javafx.scene.image.Image;

public class Cat extends Animal
{
    public static int count = 0;
    public static int timeLive = 9;
    public static Image icon = new Image("/main/resources/cat.png");

    Cat(int index)
    {
        super(index);
    }

    @Override
    public boolean isDeath()
    {
        this.liveTime++;
        return this.liveTime == Cat.timeLive;
    }

    @Override
    public char getType()
    {
        return 'C';
    }
}
