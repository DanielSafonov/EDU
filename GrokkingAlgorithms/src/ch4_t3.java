/*
 * Глава 4
 * Задача 3: Сортировка пузырьком
 * [стр. -]
 * 
 * Алгоритм принимет на вход целочисленный одномерный массив 
 * и возвращает отсортированный по убыванию массив
 */

import java.util.Arrays;

public class ch4_t3 {
	public static void main(String args[]) {
		// Тестовые массивы
		int[] arr1 = { 2, 10, -7, 5, 35, 42, -10, 0, 11 };
		int[] arr2 = { -1, -2, -3, -5 };
		int[] arr3 = { 0, 0, 0, 0 };
		int[] arr4 = { 1 };
		int[] arr5 = {};

		// Демонстраниция работы алгоритма на различных примерах
		System.out.println(Arrays.toString(arr1));
		System.out.println(Arrays.toString(bubbleSort(arr1)) + "\n");

		System.out.println(Arrays.toString(arr2));
		System.out.println(Arrays.toString(bubbleSort(arr2)) + "\n");

		System.out.println(Arrays.toString(arr3));
		System.out.println(Arrays.toString(bubbleSort(arr3)) + "\n");

		System.out.println(Arrays.toString(arr4));
		System.out.println(Arrays.toString(bubbleSort(arr4)) + "\n");

		System.out.println(Arrays.toString(arr5));
		System.out.println(Arrays.toString(bubbleSort(arr5)) + "\n");
	}
	
	
	//Функция сортировки пузырьком
	public static int[] bubbleSort(int[] arr) {
		int tmp = 0; //Буфер
		int sortedCounter = 0; //Счетчик отсортированных элементов
		
		if(arr.length == 0) {
			System.out.println("Массив пуст!");
		} else {
			//Выполняем до тех пор, пока весь массив не будет отсортирован
			while(sortedCounter != arr.length) {
				//Обходим только не сортированную область (n - sortedCounter)
				for(int j = 0; j < arr.length - 1 - sortedCounter; j++) {
					if(arr[j] < arr[j+1]) {
						//Меняем элементы местами
						tmp = arr[j];
						arr[j] = arr[j+1];
						arr[j+1] = tmp;
					}
				}
				sortedCounter++;
			}
		}
		return arr;
	}
}
