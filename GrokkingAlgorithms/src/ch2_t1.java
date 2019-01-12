/*
 * Глава 2
 * Задача 1: Сортировка выбором
 * [стр. 53]
 * 
 * Алгоритм принимает на вход целочисленный массив и сортирует его в порядке возрастания
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ch2_t1 {
	public static void main(String args[]) {
		//Тестовые массивы
		int[] arr1 = {22, 5, 1, 0, 7, -4, 5, 0, 0, -10};
		int[] arr2 = {1, 2, 3, 4, 5, 6, 7};
		int[] arr3 = {5, 5, 5, 5};
		int[] arr4 = {22};
		int[] arr5 = {};
		
		//Демонстраниция работы алгоритма на различных примерах
		System.out.println("Input : " + Arrays.toString(arr1));
		System.out.println("Sorted: " + Arrays.toString(selectionSort(arr1)) + "\n");
		
		System.out.println("Input : " + Arrays.toString(arr2));
		System.out.println("Sorted: " + Arrays.toString(selectionSort(arr2)) + "\n");
		
		System.out.println("Input: " + Arrays.toString(arr3));
		System.out.println("Sorted: " + Arrays.toString(selectionSort(arr3)) + "\n");
		
		System.out.println("Input : " + Arrays.toString(arr4));
		System.out.println("Sorted: " + Arrays.toString(selectionSort(arr4)) + "\n");
		
		System.out.println("Input : " + Arrays.toString(arr5));
		System.out.println("Sorted: " + Arrays.toString(selectionSort(arr5)) + "\n");
	}
	
	
	//Функция сортировки выбором
	//Найти минимальный элемент в текущей области и поставить его в конец сортированной части
	public static int[] selectionSort(int[] arr) {
		int minIndex = 0; //Индекс минимального элемента массива
		int sortedCount = 0; //Количество отсортированных элементов (граница отсортированной области)
		int tmp; //Буфер
		
		//Выполняем до тех пор, пока не отсортируем весь массив
		while(sortedCount != arr.length) {
			//Поиск минимального элемента в несортированной области массива
			minIndex = findMin(arr, sortedCount);
			
			//Записываем минимальный элемент в начало области (swap)
			tmp = arr[sortedCount];
			arr[sortedCount] = arr[minIndex];
			arr[minIndex] = tmp;
			
			//Увеличиваем размер отсортированной области
			sortedCount++;
		}
		
		return arr;
	}
	
	//Фурнкция поиска минимального элемента массива в границе от a до конца
	public static int findMin(int[] arr, int a) {
		int minIndex = a; //Индекс минимального элемента массива
		
		//Проходим по всем элементам массива последовательно
		for(int i = a; i < arr.length; i++) {
			if(arr[i] < arr[minIndex]) {
				//Нашли элемент меньше
				minIndex = i;
			}
		}

		return minIndex;
	}
}
