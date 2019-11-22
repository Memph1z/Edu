package ru.stqa.edu.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTestPass {

  @Test
  public void testPassPoint() {

    Point p1 = new Point(4.0, 10.0);
    Point p2 = new Point(9.0, 5.0);

    Assert.assertEquals(p1.distance(p2), 7.0710678118654755);
  }
}
