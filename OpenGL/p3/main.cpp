/* PaltusStas 2018
 * Изучение OpenGL & GLUT на курсе "Компьютерная графика"
 * MacOS Mojave, Eclipse, g++
 *
 * Задача 3: обработка клавиатуры: перемещение, вращение и увеличение/уменьшение треугольника
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

//Прототипы функций
void initOpenGL(); //Функция инициализации GLUT и OpenGL
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
	glutCreateWindow("Part 3"); //Title окна
	glClearColor(1.0, 1.0, 1.0, 1.0); //Задание цвета очистки экрана
	glClear(GL_COLOR_BUFFER_BIT); //Очистка экрана
}

//Функция отрисовки
void display(void) {
	//Костыль для Mojave
	if (MojaveWorkAround) {
		glutReshapeWindow(widthSize + 1, heightSize + 1);
		MojaveWorkAround = false;
	}

	//Очистка экрана
	glClear(GL_COLOR_BUFFER_BIT);

	//Отрисовка треугольника
	glBegin(GL_TRIANGLES);
	glColor3f(0.7, 0, 0); //Установка цвета отрисовки
	glVertex3f(-0.5, -0.5, 0); //Левая вершина
	glColor3f(0, 0.7, 0); //Установка цвета отрисовки
	glVertex3f(0.5, -0.5, 0); //Правая вершина
	glColor3f(0, 0, 0.7); //Установка цвета отрисовки
	glVertex3f(0, 0.5, 0); //Верхняя вершина
	glEnd();

	//Выгрузка кадра из буфера на экран
	glutSwapBuffers();

	//Костыль для Mojave
	glutPostRedisplay();
}

//Функция обработки события изменения размера окна
void reshape(int w, int h) {
	glViewport(0, 0, widthSize + 1, heightSize + 1); //Установка области видимости равным изначальному размеру окна
}

//Функция обработки клавиатуры
void keyboard(unsigned char key, int w, int h) {
	switch (key) {
	case 'q': //Заврешение программы
		exit(1);
	case '\033': //Заврешение программы (esc)
		exit(1);
	case 'a': //Поворот на 5 градусов вокруг оси z (a)
		glRotated(5, 0, 1, 0); //Поворот
		break;
	case 'd': //Поворот на 5 градусов вокруг оси z (d)
		glRotated(-5, 0, 1, 0); //Поворот
		break;
	case 'x': //Возврат в исходное положение (x)
		glLoadIdentity(); //Возврат фигуры в изначальное положение
		break;
	case 'w': //Перемещение по оси OX (w)
		glTranslatef(0.1, 0, 0);
		break;
	case 's': //Перемещение по оси OX (s)
		glTranslatef(-0.1, 0, 0);
		break;
	case 'z': //Увеличение фигуры (q)
		glScalef(1.1, 1.1, 0);
		break;
	case 'c': //Уменьшение фигуры (q)
		glScalef(0.9, 0.9, 0);
		break;
	case 'f': //Растянуть окно на полный экран
		glutFullScreen();
		break;
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
