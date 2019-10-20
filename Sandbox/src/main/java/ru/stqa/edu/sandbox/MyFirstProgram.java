package ru.stqa.edu.sandbox;

public class MyFirstProgram {

	public static void main(String[] args) {

		hello("world");
		hello("user");
		hello("Memph1z");

		Square s = new Square(8);
		System.out.println("Площадь квадата со стороной " + s.l + " равна " + s.arear());

		Rectangle r = new Rectangle(4, 6);
		System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " равна " + r.area());
}
	public static void hello(String somebody) {

		System.out.println("Hello, " + somebody + "!");
	}
}