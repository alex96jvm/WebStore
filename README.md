# WebStore API

## Описание

WebStore API - это REST web-сервис, который предоставляет возможность клиентам выбирать и заказывать товары, а администратору получать информацию о продажах. 

## Технологии

- Java / Spring Framework
- Jackson / MapStruct
- JdbcTemplate / Hibernate 
- Liquibase / PostgreSQL
- JUnit5 / Mockito / H2 Database (для тестирования)

## Эндпоинты

Продукты (/v1/products)

    GET - получить список всех продуктов.
    GET /{id} - получить информацию о продукте по его ID.

Заказы (/v1/orders)

    GET - получить список заказов по имени клиента.
    POST - создать новый заказ.
    PUT - обновить заказ.
    DELETE - удалить заказ.

Продажи (/v1/sales)

    POST /customer - получить продажи по имени клиента.
    POST /product - получить продажи по ID продукта.
