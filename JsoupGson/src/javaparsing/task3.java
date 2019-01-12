/*
 * Task3: Программа для сбора данных с произвольной страницы
 */

package javaparsing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class task3 {
	static Scanner in = new Scanner(System.in); // Поток ввода

	public static void main(String args[]) {
		int read_mode; // Режим чтения страницы
		int action; // Действие над страницей
		Document page = null;

		start(); // Сообщение при старте

		read_mode = mainMenu(); // Выбор режима работы программы

		if (read_mode == 0) {
			// Чтение страницы по URL
			String url = readURL(); // Чтение адреса станицы из консоли

			// Получение страницы по url
			try {
				page = Jsoup.connect(url)
						.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
						.referrer("http://www.google.com").followRedirects(true).timeout(60000).get();

				System.out.println("Соединение установлено!\n");
			} catch (IOException e) {
				System.out.println("При соединении возникла ошибка!");
				e.printStackTrace();
			}

		} else {
			// Чтение страницы из файла
			String filename = readFile(); //Пробуем открыть файл
			if (filename != null) {
				// Файл существует и не пуст
				File pageFile = new File(filename);
				try {
					page = Jsoup.parse(pageFile, "utf-8");
					System.out.println("Файл успешно открыт!\n");
				} catch (IOException e) {
					System.out.println("При чтении файла возникла ошибка!");
					e.printStackTrace();
				}
			} else {
				System.out.println("При чтении файла возникла ошибка!");
				exit(); //Завершение программы
			}
		}

		while (true) {
			action = actionsMenu(read_mode); // Выбор действия над страницей

			switch (action) {
			case 0:
				// Справка о программе
				printHelp();
				break;
			case 1:
				// Информация о старнице
				printPageInfo(page);
				break;

			case 2:
				// Парсинг данных по строке
				parsePageData(page);
				break;

			case 3:
				// Сохранение страницы в файл
				savePageToFile(page);
				break;
			}
		}
	}

	// Сообщение при старте
	public static void start() {
		System.out.println("********************************");
		System.out.println("*         JSOUP - TASK3        *");
		System.out.println("*   Парсинг страницы по строке *");
		System.out.println("*         Daniel Safonov       *");
		System.out.println("********************************");
	}

	// Главное меню программы
	public static int mainMenu() {
		int read_mode = -1; // Режим чтения
		System.out.println("\n");
		System.out.println("-------------------------------------------------");
		System.out.println("Выберите режим работы программы:");
		System.out.println("0 - чтение страницы по URL");
		System.out.println("1 - чтение страницы из файла");
		System.out.println("2 - справка");
		System.out.println("3 - выход");
		System.out.println("-------------------------------------------------");
		System.out.println("> ");

		// Чтение значения
		while (true) {
			read_mode = in.nextInt();
			if (read_mode >= 0 && read_mode <= 1) {
				// Введено верное значение
				break;
			} else if (read_mode == 2) {
				// Вызов справки
				printHelp();
				System.out.println("\n");
				System.out.println("-------------------------------------------------");
				System.out.println("Выберите режим работы программы:");
				System.out.println("0 - чтение страницы по URL");
				System.out.println("1 - чтение страницы из файла");
				System.out.println("2 - справка");
				System.out.println("3 - выход");
				System.out.println("-------------------------------------------------");
				System.out.println("> ");
			} else if (read_mode == 3) {
				// Завершение программы
				exit();
			} else {
				System.out.println("Вы ввели неверное значение!");
				System.out.println("> ");
			}
		}
		// Возврат значения
		return read_mode;
	}

	// Меню действий над страницей
	public static int actionsMenu(int read_mode) {
		int action; // Действие
		System.out.println("-------------------------------------------------");
		System.out.println("0. Справка");
		System.out.println("1. Вывести информацию о странице");
		System.out.println("2. Спарсить данные по сроке");
		if (read_mode == 0) {
			System.out.println("3. Сохранить страницу в файл");
			System.out.println("4. Выход");
		} else {
			System.out.println("3. Выход");
		}
		System.out.println("-------------------------------------------------");
		System.out.println("> ");

		// Чтение значения
		while (true) {
			action = in.nextInt();
			// Чтение по url
			if (read_mode == 0) {
				// Завершение программы
				if (action == 4) {
					exit();
				}
				if (action >= 0 && action < 4) {
					break; // Введено верное значение
				} else {
					System.out.println("Вы ввели неверное значение!");
					System.out.println("> ");
				}
			} else {
				// Чтение из файла
				// Завершение программы
				if (action == 3) {
					exit();
				}
				if (action >= 0 && action < 3) {
					break; // Введено верное значение
				} else {
					System.out.println("Вы ввели неверное значение!");
					System.out.println("> ");
				}
			}
		}
		return action;
	}

	// Чтение URL с консоли
	public static String readURL() {
		String url;
		System.out.print("\nВведите адрес страницы > ");
		in.nextLine();
		url = in.nextLine(); // Считать адрес
		url = url.trim(); // Удалить пробелы в начени и конце
		return url;
	}

	// Чтение старницы из файла
	public static String readFile() {
		String fileName;
		System.out.print("\nВведите путь к файлу > ");
		in.nextLine();
		fileName = in.nextLine(); // Считать адрес
		fileName = fileName.trim(); // Удалить пробелы в начени и конце
		
		File pageFile = new File(fileName); // Пробуем открыть файл
		
		if (pageFile.isFile()) {
			// Файл существует
			if (pageFile.length() > 0) {
				// Файл не пуст
				return pageFile.getName(); // Вернуть имя файла
			} else {
				// Файл пуст
				System.out.println("Файл пуст!");
				return null;
			}
		} else {
			// Такого файла не существует
			System.out.println("Такого файла не существует!");
			return null;
		}
	}

	// Вывод справки
	public static void printHelp() {
		System.out.println("\n");
		System.out.println("-------------------------------------------------");
		System.out.println("Данная программа была выполнена в качестве");
		System.out.println("практики при изучении библиотеки JSOUP.");
		System.out.println("\n");
		System.out.println("В функционал программы входит работа с локальными");
		System.out.println("и удаленными html-страницами, а именно:");
		System.out.println("- Получение общей информации о странице");
		System.out.println("- Парсинг данных по заданной строке");
		System.out.println("- Загрузка страницы");
		System.out.println("-------------------------------------------------");
		System.out.println("\n");
	}

	// Вывод информации о странице
	public static void printPageInfo(Document page) {
		System.out.println("\n");
		System.out.println("-------------------------------------------------");
		System.out.println("Информация о странице:");
		System.out.println("URL: " + page.location());
		System.out.println("Title: " + page.title());

		String description = page.select("meta[name=description]").first().attr("content");
		System.out.println("Description: " + description);

		//TODO: Keywords
		//TODO: Encoding
		//TODO: Lang

		System.out.println("-------------------------------------------------");
		System.out.println("\n");
	}

	// Спарсить данные по строке
	public static void parsePageData(Document page) {
		String request; //Строка с запросом
		List <Element> data = null; //Список выбранных при парсинге элементов
		
		System.out.println("\n");
		System.out.println("-------------------------------------------------");
		System.out.println("Введите строку-запрос для парсинга" + page.title());
		System.out.println("-------------------------------------------------");
		System.out.print("\nВведите запрос для select > ");
		
		in.nextLine();
		request = in.nextLine(); // Считать запрос
		request = request.trim(); // Удалить пробелы в начале и конце
		try {
			data = page.select(request);
		} catch(Exception e) {
			System.out.println("При парсинге возникла ошибка!");
			e.printStackTrace();
		}
		
		if(!data.isEmpty()) {
			//В списке имеются элементы
			System.out.println(data.toString());
		} else {
			System.out.println("Данных не найдено! Запрос не корректен или возникла ошибка.");
		}

	}

	// Сохранить страницу в файл
	public static void savePageToFile(Document page) {
		FileWriter fileWriter = null; //Поток вывода
		try {
			File pageFile = new File("savedPage.html");
			// Существует ли файл
			if (!pageFile.exists()) {
				pageFile.createNewFile();// Создаем новый файл
			}
			//Смена кодировки файла
			Element encoding = page.select("meta[http-equiv=content-type], meta[charset]").first();
			if(encoding != null) {
				//Кодировка установлена
				//TODO https://stackoverflow.com/questions/35099509/jsoup-and-character-encoding
			} else {
				//Кодировка не установлена
				//TODO установить кодировку
			}
			
			fileWriter = new FileWriter(pageFile); // Поток вывода
			//Запись кода страницы в файл
			fileWriter.write(page.toString()); 
			fileWriter.flush();
			System.out.println("Страница успешно сохранена!\n");
		} catch (IOException e) {
			System.out.println("При сохранении страницы возникла ошибка!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("При сохранении страницы возникла ошибка!");
				e.printStackTrace();
			}
		}
	}

	// Завершение программы
	public static void exit() {
		System.out.println("-------------------------------------------------");
		System.out.println("Завершение программы..");
		System.out.println("-------------------------------------------------");
		System.exit(0);
	}
}

//TODO: pageInfo + кодировка
//TODO: При открытии файла спрашивать, не SavedPage ли это
//TODO: Перенос строк в выводе