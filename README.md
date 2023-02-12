# [Курсовой проект "Сетевой чат"](https://github.com/Evgeniy-Kirichenko/chat.git)

## Описание проекта

Разработал два приложения для обмена текстовыми сообщениями по сети с помощью консоли (терминала) между двумя и более пользователями.

**Первое приложение - сервер чата**, ожидает подключения пользователей. 

**Второе приложение - клиент чата**, подключается к серверу чата и осуществляет доставку и получение новых сообщений.

Все сообщения записываются в log.txt, который дополняется при каждом запуске, а также при отправленном или полученном сообщении, как на сервере, так и на клиентах. Выход из чата осуществляется по команде "exit".

## Описание реализации
- Сервер умеет одновременно ожидать новых пользователей и обрабатывать поступающие сообщения от пользователей;
- Сервер отправляет сообщения от одного пользователя остальным пользователям;
- Реализовано логирование, запись логов в файл log.txt;
- В качестве файла настроек используется settings.txt;
- Использован сборщик пакетов MAVEN;
- Код размещен на github;

## [Описание сервера](src/main/java/server)

- Порт для подключения клиентов устанавливается через файл настроек settings.txt;
- Возможность подключиться к серверу в любой момент и присоединиться к чату;
- Отправка новых сообщений клиентам;
- Запись в log.txt всех отправленных через сервер сообщений с указанием имени пользователя и времени отправки. При каждом запуске файл дополняется

## [Описание клиента](src/main/java/client)

- Возможность выбора имени для участия в чате;
- Чтение настройки приложения (порт и хост) из файла настроек settings.txt;
- Подключение к указанному в настройках серверу;
- Для выхода из чата нужно набрать команду выхода - “exit”;