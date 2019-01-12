/*
 * Глава 3
 * Задача 2: Работа со стеком
 * [стр. 65]
 * 
 * Добавление, извлечение, чтение, получение по индексу элементов стека
 */

import java.util.Stack;

public class ch3_t2 {
	public static void main(String args[]) {
		Stack<String> orders = new Stack(); // Стек заказов в ресторане

		// Печать элементов cтека
		printOrders(orders);

		// Добавление элементов в стек
		orders.push("Чизбургер");
		orders.push("Картофель фри");
		orders.push("Большая колла");
		orders.push("Сырный соус");

		// Печать элементов cтека
		System.out.println("Список заказов: ");
		printOrders(orders);

		// Извлекаем верхний элемент стека
		System.out.println("Заказ " + orders.pop() + " начал готовиться!");
		System.out.println("Заказ " + orders.pop() + " начал готовиться!");
		System.out.println("Заказ " + orders.pop() + " начал готовиться!");
		System.out.println("\n");

		// Печать элементов cтека
		System.out.println("Оставшиеся заказы: ");
		printOrders(orders);
	}

	// Рекурсивная печать стека без извлечения элементов
	public static void printOrders(Stack stack) {
		if (stack.empty()) {
			System.out.println("Стек заказов пуст!\n");
		} else {
			for (int i = 0; i < stack.size(); i++) {
				System.out.print(stack.get(i) + " ");
			}
			System.out.println("\n");
		}
	}
}
