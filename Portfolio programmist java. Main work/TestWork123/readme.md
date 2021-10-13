написать свои настройки в файле aplication.properties. предварительно создайте базу данных socks в mysql c настройками utf-8, utf-general ci
примеры запросов: 1) GET http://localhost:8080//api/socks?color=white&operation=lessThan&cottonPart=6
2) POST http://localhost:8080//api/socks/2/outcome и в парметрах text/plain например: 50
3) POST http://localhost:8080//api/socks/income и в теле запроса JSON формат: 
{
"color" : "red",
"cottonPart" : 50,
"quantity" : 50
}

