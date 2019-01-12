/*
 * Task5: Получение списка вакансий с hh по определенному фильтру в удобочитаемом виде
 * Данные сохраняются в .txt файл, а так же встраиваются в html страницу со стилями
 * для удобочитаемого вида
 */

package javaparsing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class task5 {
	public static void main(String args[]) {
		String request = null; // Запрос к серверу
		Connection.Response data = null; // Ответ сервера

		request = chooseRequest(null); // Определить запрос
		data = connectionAndGetData(request); // Подключиться и получить данные

		generateHtmlPage(data.body()); // Сгенерировать HTML страницу с полученными данными
	}

	// TODO: переделать метод
	// Определить запрос для получения данных
	public static String chooseRequest(int args[]) {
		String baseRequest = "/vacancies?area=1"; // Вакансии Москвы

		if (args == null || args.length != 6) {
			System.out.println(
					"Параметры не были заданы или заданы некорректно, используем шаблон: Java, нет опыта, стажировка.\n");
			// Java, нет опыта, стажировка
			args = new int[6];
			args[0] = 1;
			args[1] = 1;
			args[2] = 1;
			args[3] = 0;
			args[4] = 0;
			args[5] = 0;
		}

		/*
		 * Параметры генератора запросов
		 * 
		 * Поисковой запрос: java, тестировщик args[0] Опыт работы: нет опыта, от 1 года
		 * до 3-х лет args[1] Тип занятости: стажировка, частичная занятость args[2]
		 * График работы: гибкий график args[3] Профобласть: начало карьеры args[4]
		 * Зарплата: указана args[5]
		 */

		// Формирование строки запроса
		if (args[0] == 1) {
			// Java
			baseRequest += "&text=java";
		} else if (args[0] == 2) {
			// Тестировщик
			baseRequest += "&text=тестировщик";
		}

		if (args[1] == 1) {
			// Нет опыта
			baseRequest += "&experience=noExperience";
		} else if (args[1] == 2) {
			// От 1 года до 3-х лет
			baseRequest += "&experience=between1And3";
		}

		if (args[2] == 1) {
			// Стажировка
			baseRequest += "&employment=probation";
		} else if (args[2] == 2) {
			// Частичная занятость
			baseRequest += "&employment=part";
		}

		if (args[3] == 1) {
			// Гибкий график
			baseRequest += "&schedule=flexible";
		}

		if (args[4] == 1) {
			// Начало карьеры
			baseRequest += "&specialization=15";
		}

		if (args[5] == 1) {
			// Зарплата указана
			baseRequest += "&only_with_salary=true";
		}

		return baseRequest;
	}

	// Подключиться и получить данные
	public static Connection.Response connectionAndGetData(String request) {
		String baseURL = "https://api.hh.ru"; // Базовый адрес для всех запросов
		Connection.Response data = null; // Ответ сервера

		try {
			// Запрос к API
			data = Jsoup.connect(baseURL + request)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; rv:40.0) Gecko/20100101 Firefox/40.0")
					.ignoreContentType(true) // Игнорируем, что это не html/xml
					.referrer("http://www.google.com").followRedirects(true).timeout(10000).execute();

			if (data.statusCode() == 200) {
				System.out.println("Запрос успешно выполнен! Код 200.\n");
			} else {
				// TODO: Какие еще коды при успешном запросе API может выдавать и нужна ли эта
				// обработка? Как вообще корректно обрабатывать и выводить коды при неулачном
				// соединении?
				System.out.println("При выполнении запроса возникла ошибка: " + data.statusCode() + "\n");
			}
		} catch (IOException e) {
			// Возникла ошибка
			System.out.println("При соединении возникла ошибка!\n");
			e.printStackTrace();
		}

		return data;
	}

	// Спарсить данные с полученного объекта json
	public static Vacancy[] parseData(String data) {
		JsonObject jsonObjectData = new Gson().fromJson(data, JsonObject.class); // Объект JSON с сырыми данными
		JsonArray jsonArrayData = jsonObjectData.getAsJsonArray("items"); // Массив элементов с сырыми данными

		Vacancy[] vacancies = new Vacancy[jsonArrayData.size()]; // Массив объектов Vacancy с данными по вакансиям

		JsonObject jsonSalary = null; // Объект JSON для хранения зарплаты
		JsonObject jsonEmployer = null; // Объект JSON для хранения работодателя
		JsonObject jsonDescription = null; // Объект JSON для хранения краткого описания вакансии
		String salary = null; // Строка с зарплатой
		String description = null; // Строка с кратким описанием вакансии

		for (int i = 0; i < jsonArrayData.size(); i++) {
			jsonObjectData = (JsonObject) jsonArrayData.get(i); // Записываем элемент массива в json объект

			// Зарплата
			if (!jsonObjectData.get("salary").isJsonNull()) {
				jsonSalary = jsonObjectData.get("salary").getAsJsonObject(); // Объект с описанием зарплаты
				// Формирование строки зарплаты
				if (!jsonSalary.get("from").isJsonNull()) {
					salary = "от " + jsonSalary.get("from").getAsString();
				}
				if (!jsonSalary.get("to").isJsonNull()) {
					salary = salary + " до " + jsonSalary.get("to").getAsString();
				}
				if (!jsonSalary.get("currency").isJsonNull()) {
					salary = salary + " " + jsonSalary.get("currency").getAsString();
				}
				if (!jsonSalary.get("gross").isJsonNull()) {
					if (jsonSalary.get("gross").getAsString() == "true") {
						salary = salary + " до вычета НДФЛ";
					} else {
						salary = salary + " на руки";
					}
				}
			} else {
				salary = "Не указана";
			}

			// Работодатель
			if (!jsonObjectData.get("employer").isJsonNull()) {
				jsonEmployer = jsonObjectData.get("employer").getAsJsonObject(); // Объект с описанием работодателя
			}

			// Описание
			if (!jsonObjectData.get("snippet").isJsonNull()) {
				jsonDescription = jsonObjectData.get("snippet").getAsJsonObject(); // Объект с кратким описанием
																					// вакансии
				if (!jsonDescription.get("requirement").isJsonNull()) {
					description = jsonDescription.get("requirement").getAsString();
				}
				if (!jsonDescription.get("responsibility").isJsonNull()) {
					description = description + "<br/><br/>" + jsonDescription.get("responsibility").getAsString();
				}
			}

			// Запись полученных данных в элемент массива
			vacancies[i] = new Vacancy(jsonObjectData.get("id").getAsInt(), jsonObjectData.get("name").getAsString(),
					salary, jsonEmployer.get("name").getAsString(), description);

		}

		return vacancies;
	}

	// Сгенерировать select-идентификаторы для вставки данных в HTML-шаблон
	public static VacancyGeneratedSelectors[] generateSelectors(Vacancy[] vacancies, int vacancyCount) {
		// Массив с идентификаторами для select
		VacancyGeneratedSelectors[] vacancyGeneratedSelectors = new VacancyGeneratedSelectors[vacancyCount + 1];

		// Генерируем идентификаторы для выбора блоков во время вставки
		for (int i = 1; i <= vacancyCount; i++) {
			vacancyGeneratedSelectors[i] = new VacancyGeneratedSelectors(i, "#" + i + " .header", "#" + i + " .salary",
					"#" + i + " .employer", "#" + i + " .description-text", "#" + i + " .vacancyLink");
		}

		return vacancyGeneratedSelectors;
	}

	// Загрузить шаблон HTML-страницы
	public static Document loadExportPageTemplate() {
		Document page = null;
		
		File pageFile = new File("task5/vacancies_template.html"); // Пробуем открыть файл
		
		if (pageFile.isFile()) {
			// Файл существует
			if (pageFile.length() > 0) {
				// Файл не пуст
				try {
					page = Jsoup.parse(pageFile, "utf-8");
					System.out.println("Файл шаблона успешно открыт!\n");
					return page;
				} catch (IOException e) {
					System.out.println("При чтении файла шаблона возникла ошибка!");
					e.printStackTrace();
					System.out.println("Завершение программы..");
					System.exit(0); //Завершение программы
				}
			} else {
				// Файл пуст
				System.out.println("Файл шаблона пуст!");
				System.out.println("Завершение программы..");
				System.exit(0); //Завершение программы
			}
		} else {
			// Такого файла не существует
			System.out.println("Такого файла не существует!");
			System.out.println("Завершение программы..");
			System.exit(0); //Завершение программы
		}	
		return null;
	}

	// Произвести вставку данных в поля HTML-шаблона
	public static void insertDataToPage(Document page, Vacancy[] vacancies,
		VacancyGeneratedSelectors[] vacancyGeneratedSelectors) {
		Element templateElement; // Элемент шаблона, в который производится вставка
		Element blocksContainerElement; //Элемент шаблона - контейнер блоков вакансий
		int vacanciesCount = vacancies.length; //Количество найденных вакансий

		// Добавить данные в блок секции
		templateElement = page.select(".section .request").first();
		templateElement.html("ТЕКСТ ЗАПРОСА"); // TODO: передать строку запроса

		templateElement = page.select(".section .vacancies_count").first();
		templateElement.html(Integer.toString(vacanciesCount) + " вакансий найдено");

		//Создание блоков для вакансий
		if(vacanciesCount != 0) {
			//Количество вакансий больше 0
			
			//Контейнер с блоками
			blocksContainerElement = page.select(".blocks").first();
			//Контейнер с шаблонным блоком
			templateElement = page.select(".block#1").first(); 
			
			//Создаем необходимое количество блоков (id начинается с 1)
			for(int i = 2; i <= vacanciesCount; i++) {
				//Изменить id блока и добавить копию блока с другим id
				blocksContainerElement.append(templateElement.attr("id", Integer.toString(i)).toString());
			}
			templateElement.attr("id", Integer.toString(1)); //TODO: это нужно или нет?
			
			// Проходим по всему массиву селекторов и заполняем поля данными
			for (int i = 1; i <= vacancyGeneratedSelectors.length - 1; i++) {
				// Заголовок
				templateElement = page.select(vacancyGeneratedSelectors[i].getHeader()).first();
				templateElement.html(vacancies[i - 1].getName());

				// Зарплата
				templateElement = page.select(vacancyGeneratedSelectors[i].getSalary()).first();
				templateElement.html(vacancies[i - 1].getSalary());

				// Работодатель
				templateElement = page.select(vacancyGeneratedSelectors[i].getEmployer()).first();
				templateElement.html("<b>" + vacancies[i - 1].getEmployer() + "</b>");

				// Описание
				templateElement = page.select(vacancyGeneratedSelectors[i].getDescription()).first();
				templateElement.html(vacancies[i - 1].getDescription());

				// Ссылка на вакансию
				templateElement = page.select(vacancyGeneratedSelectors[i].getLink()).first();
				templateElement.attr("href", "https://hh.ru/vacancy/" + vacancies[i - 1].id);
			}
			
			//Сохранение сгенерированной страницы в файл
			savePageToFile(page); 
		}
	}

	// Сохранить страницу в файл
	public static void savePageToFile(Document page) {
		FileWriter fileWriter = null; // Поток вывода
		try {
			File pageFile = new File("task5/vacanciesExport.html");
			// Существует ли файл
			if (!pageFile.exists()) {
				pageFile.createNewFile();// Создаем новый файл
			}
			
			fileWriter = new FileWriter(pageFile); // Поток вывода
			
			// Запись кода страницы в файл
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

	// Сгенерировать HTML страницу с полученными данными
	public static void generateHtmlPage(String data) {
		// Спарсить данные с полученного объекта json
		Vacancy[] vacancies = parseData(data);
		// Сгенерировать select-идентификаторы для вставки данных в HTML-шаблон
		VacancyGeneratedSelectors[] vacancyGeneratedSelectors = generateSelectors(vacancies, vacancies.length);
		// Загрузить шаблон HTML-страницы
		Document page = loadExportPageTemplate();
		// Произвести вставку данных в поля HTML-шаблона
		insertDataToPage(page, vacancies, vacancyGeneratedSelectors);
	}
}

//Класс для описания вакансии
class Vacancy {
	int id; // Идентификатор
	String name; // Название
	String salary; // Зарплата
	String employer; // Работодатель
	String description; // Описание

	// Конструктор класса
	public Vacancy(int id, String name, String salary, String employer, String description) {
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.employer = employer;
		this.description = description;
	}

	// Методы возврата полей объекта
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSalary() {
		return salary;
	}

	public String getEmployer() {
		return employer;
	}

	public String getDescription() {
		return description;
	}

}

//Класс для описания блока вакансии в генерируемом HTML файле
class VacancyGeneratedSelectors {
	int idSelector; // id блока
	String headerSelector; // selector поля названия вакансии
	String salarySelector; // selector поля зарплаты
	String employerSelector; // selector поля работодателя
	String descriptionSelector; // selector поля описания
	String vacancyLinkSelector; // selector поля ссылки на вакансию

	// Конструктор класса
	VacancyGeneratedSelectors(int idSelector, String headerSelector, String salarySelector, String employerSelector,
			String descriptionSelector, String vacancyLinkSelector) {
		this.idSelector = idSelector;
		this.headerSelector = headerSelector;
		this.salarySelector = salarySelector;
		this.employerSelector = employerSelector;
		this.descriptionSelector = descriptionSelector;
		this.vacancyLinkSelector = vacancyLinkSelector;
	}

	// Методы возврата полей объекта
	public int getID() {
		return idSelector;
	}

	public String getHeader() {
		return headerSelector;
	}

	public String getSalary() {
		return salarySelector;
	}

	public String getEmployer() {
		return employerSelector;
	}

	public String getDescription() {
		return descriptionSelector;
	}

	public String getLink() {
		return vacancyLinkSelector;
	}
}

//TODO: НЕТ ССЫЛКИ
//TODO: УБИРАТЬ ЛИ тег хайлайт?
//TODO: массив vacancyGeneratedSelectors можно заменить одним объектом, каждый раз конкатенирую #id и один из полей объекта
//TODO: Показывать адрес компании на карте
//TODO: Указывать станцию метро
//TODO: Указывать дату публикации
//TODO: Добавить на страницу блок со ссылками на Яндекс, MailRU и прочие стажировки
//TODO: Запускать и выводить сразу несколько поисковых запросов
//TODO: Вывести в консоль ссылку на файл, по которой можно перейти