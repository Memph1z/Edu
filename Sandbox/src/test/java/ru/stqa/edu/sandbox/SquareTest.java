package ru.stqa.edu.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SquareTest {

  @Test
  public void testArea() {

    Square s = new Square(5);
    Assert.assertEquals(s.arear(), 25.0);

  }

}
