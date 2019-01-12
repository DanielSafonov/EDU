#Подключение к веб-странице

import requests #HTTP-сервер

#Адрес сайта
BASE_URL = "http://forum.eve-ru.com/index.php?showtopic=111891"
#Подключение к сайту
r = requests.get(BASE_URL) 
#Вывод информации о результате работы (200 - успех)
print("Статус соединения: " + str(r.status_code)) 