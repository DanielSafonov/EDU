/*
 * Task4: Получение выбранного с помощью фильтров списка вакансий с hh по API
 */

package javaparsing;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class task4 {
	public static void main(String args[]) {
		String request = null; //Запрос к серверу
		Connection.Response data = null; //Ответ сервера
		
		request = chooseRequest(null);//Определить запрос
		data = connectionAndGetData(request); //Подключиться и получить данные
		System.out.println(data.body()); //Вывести полученные данные
	}
	
	//Определить запрос для получения данных
	public static String chooseRequest(int args[]) {
		String baseRequest = "/vacancies?area=1"; //Вакансии Москвы
		
		if(args == null || args.length != 6) {
			System.out.println("Параметры не были заданы или заданы некорректно, используем шаблон: Java, нет опыта, стажировка.\n");
			//Java, нет опыта, стажировка
			args = new int[6];
			args[0] = 1; args[1] = 1; args[2] = 1; args[3] = 0; args[4] = 0; args[5] = 0;
		}
		
		/*
			Параметры генератора запросов
			
			Поисковой запрос: java, тестировщик              args[0]
			Опыт работы: нет опыта, от 1 года до 3-х лет     args[1]
			Тип занятости: стажировка, частичная занятость   args[2]
			График работы: гибкий график                     args[3]
			Профобласть: начало карьеры                      args[4]
			Зарплата: указана                                args[5]
		 */
		
		//Формирование строки запроса
		if(args[0] == 1) {
			//Java
			baseRequest+="&text=java";
		} else if(args[0] == 2) {
			//Тестировщик
			baseRequest+="&text=тестировщик";
		}
		
		if(args[1] == 1) {
			//Нет опыта
			baseRequest+="&experience=noExperience";
		} else if(args[1] == 2) {
			//От 1 года до 3-х лет
			baseRequest+="&experience=between1And3";
		}
		
		if(args[2] == 1) {
			//Стажировка
			baseRequest+="&employment=probation";
		} else if(args[2] == 2) {
			//Частичная занятость
			baseRequest+="&employment=part";
		}
		
		if(args[3] == 1) {
			//Гибкий график
			baseRequest+="&schedule=flexible";
		}
		
		
		if(args[4] == 1) {
			//Начало карьеры
			baseRequest+="&specialization=15";
		}
		
		
		if(args[5] == 1) {
			//Зарплата указана
			baseRequest+="&only_with_salary=true";
		}
		
		return baseRequest;
	}
	
	//Подключиться и получить данные
	public static Connection.Response connectionAndGetData(String request) {
		String baseURL = "https://api.hh.ru"; //Базовый адрес для всех запросов
		Connection.Response data = null; //Ответ сервера
		
		try {
			//Запрос к API
			data = Jsoup.connect(baseURL + request)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; rv:40.0) Gecko/20100101 Firefox/40.0")
					.ignoreContentType(true) //Игнорируем, что это не html/xml
					.referrer("http://www.google.com").followRedirects(true).timeout(10000).execute();
			
			if(data.statusCode() == 200) {
				System.out.println("Запрос успешно выполнен! Код 200.\n");
			} else {
				//TODO: Какие еще коды при успешном запросе API может выдавать и нужна ли эта обработка?
				System.out.println("При выполнении запроса возникла ошибка: " + data.statusCode() + "\n");
			}
		} catch (IOException e) {
			//Возникла ошибка
			System.out.println("При соединении возникла ошибка!\n");
			e.printStackTrace();
		}
		
		return data;
	}
}
