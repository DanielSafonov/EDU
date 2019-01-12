/*
 * Глава 1
 * Задача 1: Бинарный поиск
 * [стр. 20]
 * 
 * Алгоритм принимает на вход отсортированный по возрастанию массив целых чисел и искомое число, 
 * возвращает позицию найденного элемента или -1
 */

import java.util.Arrays;

public class ch1_t1 {
	public static void main(String args[]) {
		//Тестовые массивы
		int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		int[] arr2 = {1, 2, 3, 4, 5};
		int[] arr3 = {5, 4, 3, 2, 1};
		int[] arr4 = {-5, -4, -3, -2, -1};
		int[] arr5 = {1};
		int[] arr6 = {};
		
		//Демонстраниция работы алгоритма на различных примерах
		System.out.println(Arrays.toString(arr1) + "   (" + 3 + ")");
		System.out.println(binarySearch(arr1, 3) + "\n");
		
		System.out.println(Arrays.toString(arr2) + "   (" + 5 + ")");
		System.out.println(binarySearch(arr2, 5) + "\n");
		
		System.out.println(Arrays.toString(arr3) + "   (" + 2 + ")");
		System.out.println(binarySearch(arr3, 2) + "\n");
		
		System.out.println(Arrays.toString(arr4) + "   (" + -1 + ")");
		System.out.println(binarySearch(arr4, -1) + "\n");
		
		System.out.println(Arrays.toString(arr5) + "   (" + 1 + ")");
		System.out.println(binarySearch(arr5, 1) + "\n");
		
		System.out.println(Arrays.toString(arr6) + "   (" + 3 + ")");
		System.out.println(binarySearch(arr6, 3) + "\n");
	}
	
	//Функция бинарного поиска
	//Вход: отсортированный по возрастанию одномерный целочисленный массив и искомый элемент
	//Выход: позиция искомого элемента в массиве или -1
	public static int binarySearch(int[] arr, int item) {
		//Границы и середина рассматриваемой области
		int left = 0;
		int middle;
		int right = arr.length - 1;
		
		//Пока рассматриваемая область не сократилась до одного элемента продолжаем поиск
		while(left <= right) {
			middle = (left + right) / 2;
			
			if(item == arr[middle]) {
				//Искомый элемент найден
				return middle;
			} else if(item < arr[middle]) {
				//Искомый элемент может быть в левой части
				right = middle - 1;
			} else {
				//Искомый элемент может быть в правой части
				left = middle + 1;
			}
		}
		return -1;
	}
}
