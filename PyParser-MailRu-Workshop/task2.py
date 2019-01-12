#Загрузка веб-страницы на машину
#Предварительно небходимо создать папку 'eve' в рабочей директории

from pathlib import Path #Форматирование путей файловой системы
import requests #HTTP-сервер

#Адрес сайта
BASE_URL = "http://forum.eve-ru.com/index.php?showtopic=111891"
#Директория для загрузки страниц
BASE_SAVE_PATH = Path('./eve')

#Подключение к сайту
r = requests.get(BASE_URL) 
#Вывод информации о результате работы (200 - успех)
print("Статус соединения: " + str(r.status_code)) 

#Имя создаваемого файла и путь к нему
html_file_path = BASE_SAVE_PATH / 'eve_first.html'

#Открыть файловый поток в режиме ввода-вывода
#Преобразуем объект Path к строке и получаем абсолютный, а не относительный путь
with open(str(html_file_path.absolute()), 'wb') as f:
	f.write(r.content) #Записать в файл страницу

print("Файл успешно загружен! " + str(html_file_path))