package main.java;

import javafx.scene.Group;
import javafx.scene.image.ImageView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public class Singleton
{
    private static ArrayList<Animal> animals = new ArrayList<>(1);
    private static ArrayList<ImageView> animals_Image = new ArrayList<>(1);
    private static TreeSet<Integer> index_object = new TreeSet<>();
    private static HashMap<Animal, Integer> liveObject = new HashMap<>();


    public static void checkLiveObject(Group root, Habitat h)
    {
        ArrayList<Animal> tmp = new ArrayList<>(1);
        for(Animal i: Singleton.liveObject.keySet())
            if(i.isDeath())
            {
                Singleton.index_object.remove(i.getIndex());
                int j = Singleton.animals.indexOf(i);
                root.getChildren().remove(Singleton.animals_Image.get(j));
                Singleton.animals.remove(j);
                var tt = Singleton.animals_Image.get(j);
                Singleton.animals_Image.remove(j);
                tmp.add(i);
                switch (i.getType()) {
                    case 'C' -> { Cat.count--; h.cats.remove(tt); }
                    case 'D' -> {Dog.count--; h.dogs.remove(tt); }
                }
            }

        for(Animal i: tmp)
            Singleton.liveObject.remove(i);


        tmp.clear();
    }

    public static ArrayList<ImageView> getAnimalsImage(){ return Singleton.animals_Image; }

    public static ArrayList<Animal> getAnimals()
    {
        return Singleton.animals;
    }

    public static HashMap<Animal, Integer> getLiveObject()
    {
        return Singleton.liveObject;
    }

    public static void addAnimal(Animal a, int birthTime)
    {
        Singleton.animals.add(a);
        Singleton.index_object.add(a.getIndex());
        Singleton.liveObject.put(a, birthTime);
    }

    public static void clear()
    {
        Singleton.animals.clear();
        Singleton.animals_Image.clear();
        Singleton.index_object.clear();
        Singleton.liveObject.clear();
    }
}