package ru.stqa.edu.sandbox;

public class MyFirstProgram {

	public static void main(String[] args) {

		hello("world");
		hello("user");
		hello("Memph1z");

		double l = 5;
		System.out.println("Площадь квадата со стороной " + l + " равна " + arear(l));

		double a = 6.0;
		double b = 4.0;
		System.out.println("Площадь прямоугольника со сторонами " + a + " и " + b + " равна " + area(a, b));
}
	public static void hello(String somebody){

		System.out.println("Hello, " + somebody + "!");
	}

	public static double arear(double len){

		return len * len;
	}

	public static double area(double a, double b){
		return a * b;
	}

}