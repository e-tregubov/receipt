Console app implements the functionality of a store receipt

- run: java CheckRunner <options>
- single argument help shows using examples
- arguments format: ID-QTY (product identifier - product quantity)
- additional arguments: pl-filename, dc-filename cn-number (product list, discount card list, discount card number)
- run example: java CheckRunner pl-pl.csv dc-dc.csv 3-15 16-8 34-1 cn-1025
- positions limiter

The check form implements:

- scaling in width
- displaying an auto-centered header with store data
- counter of limited receipts (increasing with each subsequent call)
- current date and time
- lines of commodity items
- position quantity limiter
- promotional positions indicating the percentage of the discount and the amount minus it
- the amount total, discount card percentage and amount, result total
- trimming fields if the length is exceeded
- save to file
- HTTP output

Check calculation implements:

- calculation of all positions based on the transmitted data in the parameters
- calculation of promotional positions based on the provided data of the product list
- calculation of the total discount on the provided discount card with exception of promotional positions
- calculation of the total positions amount, discount card's amount and the final result amount
- rounding the position amounts and totals to the cent for buyer's interest

Additionally:

- test files generation + saving of product lists and discount cards
- check for the existence of arguments files specified
- checking product's and discount card existence
- full check of arguments for correctness and repetitions
- main methods and calculation's test coverage

-------------------------------------------------------------------------------------------------------------------

Консольное приложение реализующее функционал формирования магазинного чека:

- запуск: java CheckRunner <параметры>
- единственный параметр help выводит примеры использования
- формат цифровых параметров: ID-Quantity, где ID идентификатор товара, а Quantity его количество
- дополнительные параметры: pl-имя файла списка товаров, dc-имя файла списка скидкарт, cn-номер скидкарты
- пример запуска: java CheckRunner pl-pl.csv dc-dc.csv cn-1025 3-15 16-8 34-1
- ограничитель позиций

Форма чека реализует:

- масштабирование по ширине
- вывод автоцентрированной шапки с данными магазина
- вывод счётчика чеков с ограничением (увеличивающийся с каждым последующим вызовом)
- вывод текущей даты и времени
- ограничитель количества товара в позиции
- вывод строк товарных позиций
- вывод строк акционных позиций с указанием процента скидки и суммы за её вычетом
- вывод строк итогов с указанием суммы и процента скидки по скидкарте
- обрезку полей при условии превышения длины
- сохранение в файл
- HTTP вывод

Калькуляция чека реализует:

- расчёт всех позиций исходя из переданных данных в параметрах
- расчёт акционных позиций исходя из предоставляемых данных списка товаров
- расчёт общей скидки по предоставленной скидкарте за исключением акционных позиций
- расчёт общей суммы позиций, суммы скидки по скидкарте и конечной суммы оплаты
- округление сумм позиций и итогов до цента в сторону покупателя

Дополнительно:

- генерирование и сохранение тестовых файлов списков товаров и скидкарт
- проверка существования файлов указанных в параметрах
- проверка существования товара и скидкарты
- полная проверка параметров на корректность и повторы
- покрытие тестами калькуляции и ключевых методов