package ru.stqa.edu.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTestFail {

  @Test
  public void testFailPoint() {

    Point p1 = new Point(1.0, 10.0);
    Point p2 = new Point(2.0, 7.0);

    Assert.assertEquals(p1.distance(p2), 7.0710678118654755);
  }
}
