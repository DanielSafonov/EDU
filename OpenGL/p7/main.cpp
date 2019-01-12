/* PaltusStas 2018
 * Изучение OpenGL & GLUT на курсе "Компьютерная графика"
 * MacOS Mojave, Eclipse, g++
 *
 * Задача 7: Работа с масивами вершин и цветов
 * НЕ РАБОТАЕТ ПЕРЕМЕЩЕНИЕ ПО OZ и OY + ТОЧНО ЛИ ПРАВИЛЬНО РАБОТАЕТ RESHAPE?
 */

//Отключение сообщения об отсутствии поддержки OpenGL в MacOS Mojave (Eclipse)
#define GL_SILENCE_DEPRECATION silence

//Подключение библиотек
#include <stdio.h>
#include <stdlib.h> //exit(0);
#ifdef __APPLE__
#include <GLUT/glut.h> //GLUT
#include <OpenGL/gl.h> //OpenGL
#else
#include <GL/glut.h> //GLUT
#include <OpenGL/gl.h> //OpenGL
#endif

//Глобальные переменные
bool MojaveWorkAround = true; //Костыль для Mojave
int widthSize = 480, heightSize = 320; //Размер окна
int rotationAngle = 0; //Угол поворота треугольника
float scaleSize = 1; //Коэффициент масштабирования треугольника
float xCoordinate = 0; //Координата по оси OX для треугольника
float yCoordinate = 0; //Координата по оси OY для треугольника
float zCoordinate = 0; //Координата по оси OZ для треугольника

//Координаты вершин первого треугольника
GLfloat vertices_triangle1[] = { -0.5, -0.5, 0, 0.5, -0.5, 0, 0, 0.5, -0.25 };

//Координаты вершин и цвета второго треугольника
GLfloat vertices_triangle2[] = { -0.9, -0.1, 0, 0.1, -0.1, 0, -0.5, 0.9, 0 };

GLfloat colors_triangle2[] = { 0.7, 0, 0, 0, 0.7, 0, 0, 0, 0.7 };

//Координаты вершин и цвета квадрата (чередующиеся координаты и цвета для них)
GLfloat square[] = { -0.6, 0.6, 0.5,
						0.7, 0, 0,
					 0.9, 0.6, 0.5,
					 	 0, 0.7, 0,
				     0.9, -0.6, 0.5,
						 0, 0, 0.7,
					 -0.6, -0.6, 0.5,
					 	 0.7, 0, 0
					 };

//Прототипы функций
void initOpenGL(); //Функция инициализации GLUT и OpenGL
void displayModels(); //Функция отрисовки объектров
void display(); //Функция отрисовки окна
void reshape(int w, int h); //Функция обработки события изменения размера окна
void keyboard(unsigned char key, int w, int h); //Функция обработки событий клавиатуры
void printInfo(); //Вывод информации в консоль

//Главная функция
int main(int argc, char **argv) {
	//Вывод информации в консоль
	printInfo();

	//Инициализация окна
	glutInit(&argc, argv); //Инициализация GLUT
	initOpenGL();

	//Функция отрисовки окна
	glutDisplayFunc(display);

	//Функция обработки события изменения размера окна
	glutReshapeFunc(reshape);

	//Функция обработки событий клавиатуры
	glutKeyboardFunc(keyboard);

	//Основной цикл GLUT
	glutMainLoop();

	return 0;
}

//Инициализация окна
void initOpenGL() {
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH); //Режим отображения
	glutInitWindowSize(widthSize, heightSize); //Размер окна
	glutInitWindowPosition(100, 100); //Позиция окна на экране
	glutCreateWindow("Part 7"); //Title окна
	glClearColor(1.0, 1.0, 1.0, 1.0); //Задание цвета очистки экрана
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //Очистка экрана и буфера глубин
	glEnable(GL_DEPTH_TEST); //Включение буфера глубин
	glDepthFunc(GL_LEQUAL); //Смена режима сравнение буферов глубин
}

//Функция отрисовки сцены
void display(void) {
	//Костыль для Mojave
	if (MojaveWorkAround) {
		glutReshapeWindow(widthSize + 1, heightSize + 1);
		MojaveWorkAround = false;
	}

	//Очистка экрана и буфера глубин
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	displayModels();

	//Выгрузка кадра из буфера на экран
	glutSwapBuffers();

	//Костыль для Mojave
	glutPostRedisplay();
}

//Функция отрисовки объектров
void displayModels() {
	//Управляемый треугольник
	glLoadIdentity(); //Сброс матрицы в изначальное состояние (отмена преобразований)
	//Операции над объектром выполняются в обратном порядке (стек вызовов)
	glScalef(scaleSize, scaleSize, 1); //Масштабирование треугольника
	glTranslatef(xCoordinate, 0, 0); //Перемещение
	//glTranslatef(0, yCoordinate, zCoordinate); //Перемещение
	glRotated(rotationAngle, 0, 1, 0); //Поворот треугольника
	//Отрисовка треугольника (массив координат)
	glColor3f(1, 0.7, 0.2); //Установка цвета отрисовки
	glEnableClientState(GL_VERTEX_ARRAY); //Вкоючить режим рендера вершинного массива
	glVertexPointer(3, GL_FLOAT, 0, vertices_triangle1); //Указатель на массив типа glFloat с 3 координатами для вершины
	glDrawArrays(GL_TRIANGLES, 0, 3); //Отрисовать массив из 3 элементов с первого для постоения треугольника
	glDisableClientState( GL_VERTEX_ARRAY); //Выключить

	//Статичный треугольник (массив координат, массив цветов)
	glLoadIdentity(); //Сброс матрицы в изначальное состояние (отмена преобразований)
	glEnableClientState(GL_VERTEX_ARRAY); //Вкоючить режим рендера вершинного массива
	glEnableClientState(GL_COLOR_ARRAY); //Вкоючить режим рендера массива цветов
	glVertexPointer(3, GL_FLOAT, 0, vertices_triangle2); //Указатель на массив типа glFloat с 3 координатами для вершины
	glColorPointer(3, GL_FLOAT, 0, colors_triangle2); //Указатель на массив типа glFloat с 3 цветами для вершины
	glDrawArrays(GL_TRIANGLES, 0, 3); //Отрисовать массив из 3 элементов с первого для постоения треугольника заданного цвета
	glDisableClientState( GL_VERTEX_ARRAY); //Выключить
	glDisableClientState( GL_COLOR_ARRAY); //Выключить

	//Статичный квадрат (массив координат и цветов)
	glLoadIdentity(); //Сброс матрицы в изначальное состояние (отмена преобразований)
	glRotated(50, 1, 1, 0); //Поворот квадрата
	glEnableClientState(GL_VERTEX_ARRAY); //Вкоючить режим рендера вершинного массива
	glEnableClientState(GL_COLOR_ARRAY); //Вкоючить режим рендера массива цветов
	//Указатель на массив типа glFloat с 3 координатами и цветами для вершины
	glVertexPointer(3, GL_FLOAT, 6 * sizeof(GLfloat), square);
	glColorPointer(3, GL_FLOAT, 24, square);
	glDrawArrays(GL_QUADS, 0, 4); //Отрисовать массив из 4 элементов с первого для постоения квадрата заданного цвета
	glDisableClientState( GL_VERTEX_ARRAY); //Выключить
	glDisableClientState( GL_COLOR_ARRAY); //Выключить

}

//Функция обработки события изменения размера окна
void reshape(int w, int h) {
	glViewport(0, 0, widthSize + 1, heightSize + 1); //Установка области видимости равным изначальному размеру окна
	glMatrixMode(GL_PROJECTION); //Задание матрицы проекции (2D)
	glLoadIdentity(); //Сброс матрицы в изначальное состояние (отмена преобразований)
	gluPerspective(60, (float) widthSize / (float) heightSize, 0, 10); //Задание матрицы перспективной проекции (60 град, соотношение сторон, 2 параметра глубины пространства)
	glMatrixMode(GL_MODELVIEW); //Задание матрицы модельного вида (3D)
}

//Функция обработки клавиатуры
void keyboard(unsigned char key, int w, int h) {
	switch (key) {
	case '\033': //Заврешение программы (esc)
		exit(1);
	case 'w': //Движение вперед
		zCoordinate -= 0.1;
		break;
	case 's': //Движение назад
		zCoordinate += 0.1;
		break;
	case 'd': //Движение направо
		xCoordinate += 0.1;
		break;
	case 'a': //Движение налево
		xCoordinate -= 0.1;
		break;
	case '\032': //Движение вверх
		yCoordinate += 0.1;
		break;
	case '\016': //Движение вниз
		yCoordinate -= 0.1;
		break;
	case 'e': //Вращение по часовой
		rotationAngle += 5;
		break;
	case 'q': //Вращение против часовой
		rotationAngle -= 5;
		break;
	case 'z': //Увеличение размера
		scaleSize += 0.1;
		break;
	case 'c': //Уменьшение размера
		scaleSize -= 0.1;
		break;
	case 'x': //Возврат в исходное положение
		rotationAngle = 0;
		xCoordinate = 0;
		yCoordinate = 0;
		zCoordinate = 0;
		scaleSize = 1;
		break;
	case 'f': //Растянуть окно на полный экран
		glutFullScreen();
		break;
	}

	//Коррекция коэффициентов
	//Поворот вокруг своей оси
	if (rotationAngle >= 360) {
		rotationAngle = 0;
	} else if (rotationAngle < 0) {
		rotationAngle = 360;
	}
	//Вперед и назад
	if (zCoordinate > 10) {
		zCoordinate = 1;
	} else if (zCoordinate < 1) {
		zCoordinate = 10;
	}
	//Направо и налево
	if (xCoordinate > 1.5) {
		xCoordinate = -1.5;
	} else if (xCoordinate < -1.5) {
		xCoordinate = 1.5;
	}
	//Вверх и вниз
	if (yCoordinate > 1.5) {
		yCoordinate = -1.5;
	} else if (yCoordinate < -1.5) {
		yCoordinate = 1.5;
	}
	//Масштабирование
	if (scaleSize <= 0) {
		scaleSize = 1;
	} else if (scaleSize > 3) {
		scaleSize = 1;
	}
}

//Вывод информации в консоль
void printInfo() {
	//Вывод информации в консоль
	printf("%s\n", "====   Управление программой   ====");
	printf("%s\n", "ECS - завершение программы");
	printf("%s\n", "a/d - движение вдоль оси OX - left/right");
	printf("%s\n", "w/s - движение вдоль оси OY - forward/back");
	printf("%s\n", "shift/space - движение вдоль оси OZ - up/down");
	printf("%s\n", "q/e - вращение вокруг оси OZ - rleft/rright");
	printf("%s\n", "z/c - увеличение и уменьшение - sup/sdown");
	printf("%s\n", "x - возврат в изначальное состояние - reset");
	printf("%s\n", "f - полный экран - fscreen");
	printf("%s\n", "===================================");
}
