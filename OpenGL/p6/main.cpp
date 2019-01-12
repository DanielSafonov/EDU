/* PaltusStas 2018
 * Изучение OpenGL & GLUT на курсе "Компьютерная графика"
 * MacOS Mojave, Eclipse, g++
 *
 * Задача 6: перспектива, модельный вид, функция reshape
 * Глубина (z) умножается на (-1)
 * Вращение объекта происходит иначе (вокруг новой системы координат (координата по z) - реализовать через glTranslate())
 * Но в функции reshape() нет возможности настроить viewport() по размеру текущего окна
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
	glutCreateWindow("Part 6"); //Title окна
	glClearColor(1.0, 1.0, 1.0, 1.0); //Задание цвета очистки экрана
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);; //Очистка экрана и буфера глубин
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
void displayModels(){
	//Управляемый треугольник
	glLoadIdentity(); //Сброс матрицы в изначальное состояние (отмена преобразований)
	//Операции над объектром выполняются в обратном порядке (стек вызовов)
	glScalef(scaleSize, scaleSize, 1); //Масштабирование треугольника
	glTranslatef(xCoordinate, 0, 0); //Перемещение вдоль оси OX
	glRotated(rotationAngle, 0, 1, 0); //Поворот треугольника
	//Отрисовка треугольника
	glBegin(GL_TRIANGLES);
	glColor3f(0.7, 0, 0); //Установка цвета отрисовки
	glVertex3f(-0.5, -0.5, -2); //Левая вершина
	glColor3f(0, 0.7, 0); //Установка цвета отрисовки
	glVertex3f(0.5, -0.5, -2); //Правая вершина
	glColor3f(0, 0, 0.7); //Установка цвета отрисовки
	glVertex3f(0, 0.5, -2.3); //Верхняя вершина
	glEnd();

	//Статичный треугольник
	glLoadIdentity(); //Сброс матрицы в изначальное состояние (отмена преобразований)
	glBegin(GL_TRIANGLES);
	glColor3f(0, 0.7, 0.2); //Установка цвета отрисовки
	glVertex3f(-0.9, -0.1, -2); //Левая вершина
	glVertex3f(0.1, -0.1, -2); //Правая вершина
	glVertex3f(-0.5, 0.9, -2); //Верхняя вершина
	glEnd();
}

//Функция обработки события изменения размера окна
void reshape(int w, int h) {
	glViewport(0, 0, widthSize + 1, heightSize + 1); //Установка области видимости равным изначальному размеру окна
	glMatrixMode(GL_PROJECTION); //Задание матрицы проекции (2D)
	glLoadIdentity(); //Сброс матрицы в изначальное состояние (отмена преобразований)
	gluPerspective(60, (float) widthSize / (float) heightSize, 1, 10); //Задание матрицы перспективной проекции (60 град, соотношение сторон, 2 параметра глубины пространства)
	glMatrixMode(GL_MODELVIEW); //Задание матрицы модельного вида (3D)
}

//Функция обработки клавиатуры
void keyboard(unsigned char key, int w, int h) {
	switch (key) {
	case 'q': //Заврешение программы
		exit(1);
	case '\033': //Заврешение программы (esc)
		exit(1);
	case 'a': //Поворот на 5 градусов вокруг оси z (a)
		rotationAngle += 5;
		break;
	case 'd': //Поворот на 5 градусов вокруг оси z (d)
		rotationAngle -= 5;
		break;
	case 'x': //Возврат в исходное положение (x)
		rotationAngle = 0;
		xCoordinate = 0;
		scaleSize = 1;
		break;
	case 'w': //Перемещение по оси OX (w)
		xCoordinate += 0.05;
		break;
	case 's': //Перемещение по оси OX (s)
		xCoordinate -= 0.05;
		break;
	case 'z': //Увеличение фигуры (q)
		scaleSize += 0.1;
		break;
	case 'c': //Уменьшение фигуры (q)
		scaleSize -= 0.1;
		break;
	case 'f': //Растянуть окно на полный экран
		glutFullScreen();
		break;
	}

	//Коррекция коэффициентов
	if (rotationAngle >= 360) {
		rotationAngle = 0;
	} else if (rotationAngle < 0) {
		rotationAngle = 360;
	}
	if (xCoordinate > 1.5) {
		xCoordinate = -1.5;
	} else if (xCoordinate < -1.5) {
		xCoordinate = 1.5;
	}
}

//Вывод информации в консоль
void printInfo() {
	//Вывод информации в консоль
	printf("%s\n", "====   Управление программой   ====");
	printf("%s\n", "q/ECS - завершение программы");
	printf("%s\n", "w/s - движение вдоль оси OX");
	printf("%s\n", "a/d - вращение вокруг оси OZ");
	printf("%s\n", "z/c - увеличение и уменьшение");
	printf("%s\n", "x - возврат в изначальное состояние");
	printf("%s\n", "f - полный экран");
	printf("%s\n", "===================================");
}
