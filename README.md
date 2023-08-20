**Проект:** Приложение для отслеживания почтовых отправлений </br> 
**Автор:** Богданова Диана ([dbogda15](https://github.com/dbogda15))<br>
**Язык:** Java 17 </br>
**Framework:** Spring </br>
**Сборщик проекта:** Maven

### Описание работы приложения

 Данное веб-приложение имеет следующие сущности:
* почтовое отделение с полями:
    + идентификатор,
    + индекс, 
    + название, 
    + адрес получателя,
    + коллекция почтовых отправлений.
* почтовое отправление с полями:
    + идентификатор,
    + тип (письмо, посылка, бандероль, открытка),
    + индекс получателя, 
    + адрес получателя, 
    + имя получателя
    + список отчётов/история перемещений.
* отчёт с полями:
    + идентификатор,
    + сообщение с локациями,
    + ID почтового отпраления.

Операции, которые могут быть выполнены:
+ регистрации почтового отправления,
+ его прибытие в промежуточное почтовое отделение,
+ его убытие из почтового отделения,
+ его получение адресатом,
+ просмотр статуса и полной истории движения почтового отправления.

Расшифровка кодов ответа:
+ 200 - Запрос выполнен успешно,
+ 400 - Ошибка в параметрах запроса,
+ 404 - Несуществующий URL,
+ 500 - Ошибка со стороны сервера.

Все операции, связанные с почтовым отделением, можно осуществить через контроллер *PostOfficeController*, а  операции, связанные с почтовыми отправлениями - *PostalItemController*.</br>

Чтобы осуществить регистрацию нового почтового отпраления, перемещение его между отделениями или доставку в пункт выдачи, необходимо в контроллере *PostalItemController* ввести ID отправления и ID отеделения, куда будет осуществлено перемещение.</br> 

Результатом данного запроса будет сообщение о перемещении (*Report*), который содержит информацию о текущих локациях и ID почтового отправления.</br>

Для запуска данного приложения необходимо добавить в файл *application.properties* следующее:

```
spring.datasource.url=${db.url}
spring.datasource.username=${db.username}
spring.datasource.password=${db.password}
spring.jpa.hibernate.ddl-auto=update
```
Где:
db.url = ссылка для подключения к вашей базе данных (БД)
db.username = ваш username от БД
db.password = ваш пароль от БД

