package ru.stqa.edu.sandbox;

public class MySecondProgram {

  public static void main(String[] args) {


    Point p1 = new Point(4.0, 10.0);
    Point p2 = new Point(9.0, 5.0);

    System.out.println("Расстояние между точками " + "(" + p1.c + ", " + p1.d + ")" + " и " + "(" + p2.c + ", " + p2.d + ")" + " равно " + p1.distance(p2));
  }

}
