package main.java;

abstract class Animal implements IBehaviour
{
    Animal(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return this.index;
    }

    public char getType()
    {
        return 'A';
    }

    public boolean isDeath()
    {
        return true;
    }

    protected int liveTime = 0;
    protected int index;
}
