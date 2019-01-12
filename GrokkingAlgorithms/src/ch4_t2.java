/*
 * Глава 4
 * Задача 2: Быстрая сортировка
 * [стр. 85]
 * 
 * Алгоритм принимет на вход целочисленный одномерный массив 
 * и возвращает отсортированный по убыванию массив
 */

import java.util.Arrays;

public class ch4_t2 {
	public static void main(String args[]) {
		// Тестовые массивы
		int[] arr1 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		int[] arr2 = { 1, 2, 3, 4, 5 };
		int[] arr3 = { 5, 4, 3, 2, 1 };
		int[] arr4 = { -5, -4, -3, -2, -1 };
		int[] arr5 = { 1 };
		int[] arr6 = {};

		// Демонстраниция работы алгоритма на различных примерах
		System.out.println(Arrays.toString(arr1));
		System.out.println(quickSort(arr1) + "\n");

		System.out.println(Arrays.toString(arr2));
		System.out.println(quickSort(arr2) + "\n");

		System.out.println(Arrays.toString(arr3));
		System.out.println(quickSort(arr3) + "\n");

		System.out.println(Arrays.toString(arr4));
		System.out.println(quickSort(arr4) + "\n");

		System.out.println(Arrays.toString(arr5));
		System.out.println(quickSort(arr5) + "\n");

		System.out.println(Arrays.toString(arr6));
		System.out.println(quickSort(arr6) + "\n");
	}
	
	//Функция быстрой сортировки
	
	public static int[] quickSort(int[] arr) {
		
		return arr;
	}
}
