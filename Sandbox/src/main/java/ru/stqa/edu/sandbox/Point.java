package ru.stqa.edu.sandbox;

public class Point {

  public double c;
  public double d;

  public Point(double c, double d) {

    this.c = c;
    this.d = d;

  }

  public double distance(Point p2) {
    return Math.sqrt(((p2.c - c) * (p2.c - c)) + ((p2.d - d) * (p2.d - d)));
  }

}
