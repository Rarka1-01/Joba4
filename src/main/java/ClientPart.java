package main.java;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.Socket;

public class ClientPart extends Thread
{
    private Socket client;
    private Habitat h;
    private BufferedReader in;
    private BufferedWriter out;
    private int name;

    ClientPart(Habitat h)
    {
        name = (int) (Math.random() * 10000 + 15);
        this.h = h;
        try {
            client = new Socket("localhost", 4004);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            out.write(name + "\n");
            out.flush();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(name);
        start();
    }

    public void reQuest()
    {
        try {
            out.write("Cat\n");
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void getInfo()
    {
        try {
            out.write(Cat.count + "/" + Dog.count + "\n");
            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void newGenerate(String word)
    {
        int nC = Integer.parseInt(word.substring(0, word.indexOf('/')));
        int nD = Integer.parseInt(word.substring(word.indexOf('/') + 1));
        Cat.count = nC;
        Dog.count = nD;

        if(nC == 0)
        {
            h.dsp = true;
            h.csp = false;
        }
        else
        {
            h.dsp = false;
            h.csp = true;
        }

        Platform.runLater(() ->
        {

            h.root.getChildren().removeAll(h.cats);
            h.root.getChildren().removeAll(h.dogs);
            h.scene.setRoot(h.root);
            h.cats.clear();
            h.dogs.clear();
            Singleton.clear();

        for(int i = 0; i < nC; i++)
        {
            Singleton.addAnimal(new Cat(h.count_cat + h.dt), h.dt);
            ImageView nIamge_cat = new ImageView(Cat.icon);
            nIamge_cat.setLayoutX(Math.random() * 432 + 20);
            nIamge_cat.setLayoutY(Math.random() * 390 + 90);

            Singleton.getAnimalsImage().add(nIamge_cat);
            h.cats.add(nIamge_cat);
            h.root.getChildren().add(nIamge_cat);
            h.scene.setRoot(h.root);
        }

        for(int i = 0; i < nD; i++)
        {
            Singleton.addAnimal(new Dog(h.count_dog + h.dt), h.dt);
            ImageView nIamge_dog = new ImageView(Dog.icon);
            nIamge_dog.setLayoutX(Math.random() * 432 + 20);
            nIamge_dog.setLayoutY(Math.random() * 390 + 90);

            Singleton.getAnimalsImage().add(nIamge_dog);
            h.cats.add(nIamge_dog);
            h.root.getChildren().add(nIamge_dog);
            h.scene.setRoot(h.root);
        }});
    }

    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                try {
                    String word = in.readLine();

                    if(word.equals("Get"))
                        this.getInfo();

                    if(word.indexOf('/') != -1) {
                        System.out.println(word);
                        this.newGenerate(word);
                    }

                }catch (Exception e)
                {
                    System.out.println(e.getMessage());
                    if(e.getMessage().equals("Connection reset"))
                        break;
                }
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
