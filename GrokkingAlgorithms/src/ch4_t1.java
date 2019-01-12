/*
 * Глава 4
 * Задача 1: Алгоритм Евклида - нахождение НОД двух чисел
 * [стр. 79]
 * 
 * Алгоритм принимает на вход пару положительных целых чисел 
 * и возвращает ее НОД
 * 
 * НОД - наибольший общий делитель - Nmax: K1 % Nmax = 0, K2 % Nmax = 0
 * (HCF - highest common factor)
 */


public class ch4_t1 {
	public static void main(String args[]) {
		System.out.println("Для 54 и 24 это " + findHCFbyEuclid(54, 24));
		System.out.println("Для 24 и 54 это " + findHCFbyEuclid(24, 54));
		System.out.println("Для 24 и 24 это " + findHCFbyEuclid(24, 24));
		System.out.println("Для 0 и 0 это " + findHCFbyEuclid(0, 0));
		System.out.println("Для 7 и 13 это " + findHCFbyEuclid(7, 13));
		System.out.println("Для 1000 и 22 это " + findHCFbyEuclid(1000, 22));
	}
	
	//Вычисление НОД по алгоритму Евклида
	public static int findHCFbyEuclid(int n, int m) {
		int tmp = 0; //Буферная переменная
		
		//Преобразуем пару чисел до тех пор, пока она не станет равной
		while(n != m) {
			//Записываем в одну переменную минимальный, а во вторуб разницу
			tmp = n;
			n = m - n;
			if(n < 0) {
				//m меньше n
				n = Math.abs(n);
				//m = m;
			} else {
				m = tmp;
			}
		}
		
		return n;
	}
}
