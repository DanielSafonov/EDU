#Работа с API и JSON (https://jsonplaceholder.typicode.com)
#Сайт для тренировки рабоыт с API

from pprint import pprint #Структурированное отображение данных в консоли (JSON)
import requests #HTTP-сервер

#Адрес сайта
BASE_URL = "https://jsonplaceholder.typicode.com/posts"

#Подключение к сайту
r = requests.get(BASE_URL)
#Вывод информации о результате работы (200 - успех)
print("----------------------------------") 
print("Статус соединения: " + str(r.status_code)) 
print("----------------------------------") 
print() 
print() 

#Вывод полученного с помощью API JSON в обычном виде
print("----------------------------------") 
print("Сырые данные:")
print(r.content)
print("----------------------------------") 
print() 
print() 

print("----------------------------------") 
print("Структурированный первый элемент последовательности данных:")
parsed_json = r.json()
print(parsed_json[0])
print("----------------------------------") 
print() 
print()

print("----------------------------------") 
print("Элемент title первого набора последовательности данных:")
first_title = parsed_json[0]['title']
print(first_title)
print("----------------------------------") 
print() 
print() 