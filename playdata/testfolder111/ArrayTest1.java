package step01.syntax;

import org.junit.Test;

import model.domain.Person;

public class ArrayTest1 {

//	@Test
	public void m1() {
		int[] i = { 1, 2, 3 };
		System.out.println(i[0] + " " + i.length);
		System.out.println("----");

		for (int index = 0; index < 3; index++) {
			System.out.print(i[index] + "\t");
		}
		System.out.println("");
		for (int v : i) {
			System.out.println(v + "\t");
		}
	}

	public void m2() {
		System.out.println(2);
	}

	@Test
	public void m3() {
		System.out.println("m3시작");
		Person[] p = { new Person("연아", 20), new Person("연아2", 50) };
		
		System.out.println(p[0].getName());
	}

	public static void main(String[] args) {

	}
}
