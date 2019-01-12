/* PaltusStas 2018
 * Изучение OpenGL & GLUT на курсе "Компьютерная графика"
 * MacOS Mojave, Eclipse, g++
 *
 * Задача 1: создать окно размером 500x500px
 */

//Отключение сообщения об отсутствии поддержки OpenGL в MacOS Mojave (Eclipse)
#define GL_SILENCE_DEPRECATION silence

//Подключение библиотек
#ifdef __APPLE__
#include <GLUT/glut.h> //GLUT
#else
#include <GL/glut.h> //GLUT
#endif

//Глобальные переменные
bool MojaveWorkAround = true; //Костыль для Mojave
int size = 500; //Размер окна

//Функция отрисовки
void display(void) {
	//Костыль для Mojave
	if (MojaveWorkAround) {
		glutReshapeWindow(size + 1, size + 1);
		MojaveWorkAround = false;
	}

	//Очистка экрана
	glClear(GL_COLOR_BUFFER_BIT);

	//Выгрузка кадра из буфера на экран
	glutSwapBuffers();

	//Костыль для Mojave
	glutPostRedisplay();
}

//Главная функция
int main(int argc, char **argv) {
	//Инициализация окна
	glutInit(&argc, argv); //Инициализация GLUT
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGBA | GLUT_DEPTH); //Режим отображения
	glutInitWindowSize(size, size); //Размер окна
	glutInitWindowPosition(100, 100); //Позиция окна на экране
	glutCreateWindow("Part 1"); //Title окна
	glClearColor(1.0, 1.0, 1.0, 1.0); //Задание цвета очистки экрана
	glClear(GL_COLOR_BUFFER_BIT); //Очистка экрана

	//Функция отрисовки окна
	glutDisplayFunc(display);

	//Основной цикл GLUT
	glutMainLoop();

	return 0;
}
