Вашему вниманию представлена курсовая работа на тему "Разработка базы данных для автоматизированной системы управления контроля результатов обучения по дисциплине 'Управление и автоматизация баз данных' на платформе Университета 'Синергия'", от 19.03.2024 года.
----
Presented to your attention is a term paper on the topic "Development of a database for an automated system of managing educational outcomes assessment in the discipline 'Database Management and Automation' on the platform of 'Synergy University'", dated March 19, 2024.
---------------

![Curs](https://github.com/Kemerev/Course-work/assets/114690702/09ceeb46-a688-4ac1-9144-d6e22708bb7a)

---------------
Чтобы проект заработал нужно до установить slf4j-api-2.0.12 и sqlite-jdbc-3.45.2.0
---------------
Создание базы данных:
	Были определены и созданы таблицы для хранения информации о студентах, преподавателях, оценках, расписании занятий и других аспектах учебного процесса.
	Таблица "Студенты":
	   - ФИО (Текстовое поле)
	   - Группа (Текстовое поле)
	   - Контактные данные (Текстовое поле)
	   - Дата рождения (Дата/время)
	   - Фотография (Вложенный файл)
	2. Таблица "Предметы":
	   - Название предмета (Текстовое поле)
	   - Преподаватель (Текстовое поле)
	   - Количество часов (Числовое поле)
	   - Описание (Длинное текстовое поле)
	3. Таблица "Оценки":
	   - ID оценки (Автоинкремент)
	   - ID студента (Ссылка на таблицу "Студенты")
	   - ID предмета (Ссылка на таблицу "Предметы")
	   - Оценка (Числовое поле)
	   - Дата оценки (Дата/время)
	4. Таблица "Экзамены":
	   - ID экзамена (Автоинкремент)
	   - Предмет (Текстовое поле)
	   - Дата экзамена (Дата/время)
	   - Преподаватель (Текстовое поле)
	   - Описание (Длинное текстовое поле)
	5. Таблица "Преподаватели" (новая таблица):
	   - ФИО преподавателя (Текстовое поле)
	   - Кафедра (Текстовое поле)
	   - Контактные данные (Текстовое поле)
	   - Ученая степень (Текстовое поле)
	6. Таблица "Учебные материалы" (новая таблица):
	   - Название материала (Текстовое поле)
	   - Автор (Текстовое поле)
	   - Категория (Текстовое поле)
	   - Ссылка на файл (Гиперссылка)
	7. Таблица "Группы" (новая таблица):
	   - Номер группы (Текстовое поле)
	   - Курсы (Текстовое поле)
	   - Статус (Текстовое поле)
	8. Таблица "Расписание занятий" (новая таблица):
	   - День недели (Текстовое поле)
	   - Время начала (Дата/время)
	   - Время окончания (Дата/время)
	   - Место проведения (Текстовое поле)
	   - Преподаватель (Текстовое поле)
	9. Таблица "Аудитории" (новая таблица):
	   - Номер аудитории (Текстовое поле)
	   - Вместимость (Числовое поле)
	   - Оборудование (Текстовое поле)
	10. Таблица "Электронные ресурсы" (новая таблица):
	   - Название ресурса (Текстовое поле)
	   - Тип ресурса (Текстовое поле)
	   - Ссылка (Гиперссылка)
Разработка пользовательского интерфейса:
	На языке Java с использованием библиотеки Swing был создан пользовательский интерфейс.
	Реализованы различные формы для ввода данных об оценках, просмотра информации о студентах и их успеваемости, генерации отчетов по успеваемости и просмотра общих данных.
Добавление аутентификации:
	Реализовано окно аутентификации с проверкой логина и пароля.
	Для входа в приложение требуется использование предварительно заданных логинов и паролей
Обработка данных и отображение отчетов:
	Реализована обработка данных из базы данных для генерации отчетов по успеваемости студентов.
	Отчеты выводятся в виде текста в соответствующих окнах пользовательского интерфейса.
Локализация:
	Добавлена локализация пользовательского интерфейса на русский язык.
Тестирование:
	Проведено тестирование приложения на различных этапах разработки для обеспечения корректной работы и соответствия требованиям.
------------
Database Creation:
Tables were defined and created to store information about students, teachers, grades, class schedules, and other aspects of the educational process.

Table "Students":
- Full Name (Text field)
- Group (Text field)
- Contact Information (Text field)
- Date of Birth (Date/time)
- Photo (Binary Large Object)

Table "Subjects":
- Subject Name (Text field)
- Teacher (Text field)
- Hours (Numeric field)
- Description (Long text field)

Table "Grades":
- Grade ID (Autoincrement)
- Student ID (Reference to the "Students" table)
- Subject ID (Reference to the "Subjects" table)
- Grade (Numeric field)
- Date of Grade (Date/time)

Table "Exams":
- Exam ID (Autoincrement)
- Subject (Text field)
- Exam Date (Date/time)
- Teacher (Text field)
- Description (Long text field)

Table "Teachers" (new table):
- Teacher's Full Name (Text field)
- Department (Text field)
- Contact Information (Text field)
- Academic Degree (Text field)

Table "Study Materials" (new table):
- Material Name (Text field)
- Author (Text field)
- Category (Text field)
- File Link (Hyperlink)

Table "Groups" (new table):
- Group Number (Text field)
- Courses (Text field)
- Status (Text field)

Table "Class Schedule" (new table):
- Day of the Week (Text field)
- Start Time (Date/time)
- End Time (Date/time)
- Location (Text field)
- Teacher (Text field)

Table "Classrooms" (new table):
- Room Number (Text field)
- Capacity (Numeric field)
- Equipment (Text field)

Table "Electronic Resources" (new table):
- Resource Name (Text field)
- Resource Type (Text field)
- Link (Hyperlink)

Development of User Interface:
A user interface was created using Java with the Swing library.
Various forms were implemented for entering grade data, viewing information about students and their performance, generating performance reports, and viewing general data.

Authentication Addition:
An authentication window was implemented with login and password verification.
Entering the application requires the use of predefined logins and passwords.

Data Processing and Report Display:
Data processing from the database was implemented to generate reports on student performance.
Reports are displayed as text in corresponding windows of the user interface.

Localization:
Localization of the user interface into Russian was added.

Testing:
Testing of the application was conducted at various stages of development to ensure proper operation and compliance with requirements.
