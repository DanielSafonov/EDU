#Работа с API headhunter

from pprint import pprint #Вывод JSON для python
import requests #HTTP-сервер

#Адрес сайта
BASE_URL = "https://api.hh.ru/"
#Добавление 'vacancies' для получение данных о вакансиях
vac_r = requests.get(BASE_URL + 'vacancies') 
#Вывод информации о результате работы (200 - успех)
print("----------------------------------") 
print("Статус соединения: " + str(vac_r.status_code)) 
print("----------------------------------") 
print() 
print() 

#Выбор и вывод первой вакансии
vacancies = vac_r.json()['items']
first_var = vacancies[0]
pprint(first_var)

