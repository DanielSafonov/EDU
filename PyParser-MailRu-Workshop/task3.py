#Загрузка нескольких веб-страниц на машину

from pathlib import Path #Форматирование путей файловой системы
import requests #HTTP-сервер

#Адрес сайта - форматированная строка
BASE_URL = "http://forum.eve-ru.com/index.php?showtopic=111891&page={page_num}"
#Директория для загрузки страниц
BASE_SAVE_PATH = Path('./eve')

#Загрузить 3 страницы форума
for i in range(1, 4):
	#Подключение к сайту по адресу форматированного URL
	r = requests.get(BASE_URL.format(page_num = i))
	#Вывод информации о результате работы (200 - успех)
	print("Статус соединения: " + str(r.status_code)) 

	#Форматированная строка с именами файлов и путем к ним
	html_file_path = BASE_SAVE_PATH / 'eve_first_{page_num}.html'.format(page_num = i)

	#Открыть файловый поток в режиме ввода-вывода
	with open(str(html_file_path.absolute()), 'wb') as f:
		f.write(r.content) #Записать в файл страницу

	print("Файл успешно загружен! " + str(html_file_path))