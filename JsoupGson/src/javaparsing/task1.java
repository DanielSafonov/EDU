/*
 * Task1: получить title страницы по url
 */

package javaparsing;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class task1 {
	public static void main(String args[]) throws IOException {
		Document doc = Jsoup.connect("https://paltusstas.github.io").get(); //Получение страницы по url
		String title = doc.title(); //Получение содержимого title
		System.out.println("Hello, " + title + "!");
	}
}
