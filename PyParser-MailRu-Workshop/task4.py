#Парсинг веб-страницы

from pathlib import Path #Форматирование путей файловой системы
from bs4 import BeautifulSoup #Анализатор HTML/XML
import requests #HTTP-сервер

#Адрес сайта
BASE_URL = "http://forum.eve-ru.com/index.php?showtopic=111891"
#Директория для загрузки страниц
BASE_SAVE_PATH = Path('./eve')

#Подключение к сайту
r = requests.get(BASE_URL)
#Вывод информации о результате работы (200 - успех)
print("----------------------------------") 
print("Статус соединения: " + str(r.status_code)) 
print("----------------------------------") 
print() 
print() 

#Передача парсеру текста страницы с использованием втроенного в python html-парсера
soap = BeautifulSoup(r.text, "html.parser")

#Вывести title страницы
print("----------------------------------") 
print("Название страницы: ") 
print(soap.title.get_text()) 
print("----------------------------------") 
print() 
print() 

#Достаем сообщения пользователей сайта
msgs = soap.select('div.post.entry-content')

print("----------------------------------") 
print("Длина посдеднего сообщения (сырого): ") 
print(len(msgs))
print("----------------------------------") 
print() 
print() 

print("----------------------------------") 
print("Последнее сообщение (сырое): ") 
print(msgs[-1])
print("----------------------------------") 
print() 
print() 

#Парсинг сообщений
parsed_msgs = []
#В цикле foreach проходим по массиву строк и достаем текст без тэгов
for msg in msgs:
	txt = msg.get_text()
	parsed_msgs.append(txt)

print("----------------------------------") 
print("Длина распарсенного сообщения: ") 
print(len(parsed_msgs))
print("----------------------------------") 
print() 
print() 

print("----------------------------------") 
print("Распарсенное последнее сообщение: ") 
print(parsed_msgs[-1])
print("----------------------------------") 
print() 
print() 