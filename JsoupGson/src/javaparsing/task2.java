/*
 * Task2: получить конкретные элементы страницы
 */

package javaparsing;

import java.io.IOException;
import java.util.List; 

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class task2 {
	public static void main(String args[]) {
		try {
			Document blog = Jsoup.connect("https://paltusstas.github.io").get();
			System.out.println("Соединение установлено!\n");
			List <Element> posts_title = blog.select(".post-title > a");
			for(Element link: posts_title) {
				System.out.println(link.text());
			}
			System.out.println("\nЗавершение работы..");
		} catch (IOException e) {
			System.out.println("При выполнении программы возникла ошибка!");
			e.printStackTrace();
		} //Получение страницы по url
		
	}
}
