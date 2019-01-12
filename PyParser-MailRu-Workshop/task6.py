#Работа с API Github

from pprint import pprint #Структурированное отображение данных в консоли (JSON)
import requests #HTTP-сервер

#Адрес сайта
BASE_URL = "https://api.github.com/users/AlwxSin"

#Подключение к сайту
r = requests.get(BASE_URL)
#Вывод информации о результате работы (200 - успех)
print("----------------------------------") 
print("Статус соединения: " + str(r.status_code)) 
print("----------------------------------") 
print() 
print() 

#Вывод данных конкретного пользоватея по Github API
print("----------------------------------") 
print("Все данные пользователя AlwxSin:")
parsed_info_json = r.json()
pprint(parsed_info_json)
print("----------------------------------") 
print() 
print() 

#Получаем конкретные данные из JSON
print("----------------------------------") 
print("Выборочные данные пользователя AlwxSin:")
name = parsed_info_json['name']
print("Name: " + name)
avatar_url = parsed_info_json['avatar_url']
print("Avatar URL: " + avatar_url)
user_id = parsed_info_json['id']
print("User id: " + str(user_id))
print("----------------------------------") 
print() 
print() 

#Подключение страницы репозиториев
print("----------------------------------") 
print("Список репозиториев пользователя AlwxSin:")
repos_r = requests.get(parsed_info_json['repos_url'])
parsed_repos_json = repos_r.json()
n = len(parsed_repos_json)
print("(" + str(n) + " репозитория)")
print()
print("Имя репозитория | id | Язык")
for i in range(1, n):
	#parsed_repos_json - берем i-й набор данных, выбираем поле 'full_name', обрезаем первые 8 символов (AlwxSin/) 
	print("- " + parsed_repos_json[i]['full_name'][8:] + " " + str(parsed_repos_json[i]['id']) + " " + parsed_repos_json[i]['language'])
print("----------------------------------") 