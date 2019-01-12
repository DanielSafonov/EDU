/*
 * Глава 3
 * Задача 1: Рекурсивное вычисление элемента последовательности Чисел Фибоначчи
 * [стр. -]
 * 
 * Алгоритм принимает на вход порядковый номер элемента и 
 * возвращает всю последовательность до этого элемента включительно
 */

public class ch3_t1 {
	public static void main(String args[]) {

		printFibonacciSequence(10);
		System.out.println("\n\n\n");
		printFibonacciSequence(3);
		System.out.println("\n\n\n");
		printFibonacciSequence(2);
		System.out.println("\n\n\n");
		printFibonacciSequence(1);
		System.out.println("\n\n\n");
		printFibonacciSequence(0);
		System.out.println("\n\n\n");
		printFibonacciSequence(-5);
		System.out.println("\n\n\n");
		printFibonacciSequence(27);
		
		System.out.println("\n\n\n");
		System.out.println("-----------------");
		System.out.println("\n\n\n");

		printFibonacciSequenceRec(10);
		System.out.println("\n\n\n");
		printFibonacciSequenceRec(3);
		System.out.println("\n\n\n");
		printFibonacciSequenceRec(2);
		System.out.println("\n\n\n");
		printFibonacciSequenceRec(1);
		System.out.println("\n\n\n");
		printFibonacciSequenceRec(0);
		System.out.println("\n\n\n");
		printFibonacciSequenceRec(-5);
		System.out.println("\n\n\n");
		printFibonacciSequenceRec(27);
	}

	// Функция печати последовательности чисел Фибоначчи до заданного порядкового
	// номера
	public static void printFibonacciSequence(int number) {
		int F = 0; // Текущее значение функции (Fn)
		int F_ll = 0; // Fn-2
		int F_l = 1; // Fn-1

		if (number >= 2) {
			System.out.print(F_ll + " ");
			System.out.print(F_l + " ");

			for (int i = 1; i <= number - 1; i++) {
				F = F_ll + F_l;
				F_ll = F_l;
				F_l = F;
				System.out.print(F + " ");
			}

		} else {
			switch (number) {
			case 0:
				System.out.println(F_ll);
				break;
			case 1:
				System.out.print(F_ll + " ");
				System.out.print(F_l);
				break;
			default:
				System.out.println("Порядковый номер не может быть отрицательным!");
				break;
			}
		}
	}

	// Рекурсивная функция печати последовательности чисел Фибоначчи до заданного
	// порядкового номера
	public static void printFibonacciSequenceRec(int number) {
		if (number < 0) {
			System.out.println("Порядковый номер не может быть отрицательным!");
		} else {
			System.out.println();
			for (int i = 0; i <= number; i++) {
				System.out.print(FibonacciCalcRec(i) + " ");
			}
		}
	}

	public static int FibonacciCalcRec(int number) {
		if (number == 0) {
			return 0;
		} else if (number == 1) {
			return 1;
		} else {
			return FibonacciCalcRec(number - 1) + FibonacciCalcRec(number - 2);
		}
	}
}
