# Smart Shopping List

## Description
Мобильное приложение для управления списком покупок.  
Позволяет добавлять товары, отмечать купленные, удалять по одному или очищать весь список.

## Authors
| Имя | Роль |
|-----|--------|------|
| Мацкевич М.Н. | Backend, Database, Logic |
| Гончаревич В.В. | UI, Adapters, Testing |

## Technologies
- Java 17
- Room Database (SQLite)
- RecyclerView + CardView
- SharedPreferences
- ConstraintLayout

## Installation
1. Скачать APK из [Releases](https://github.com/fpmi-pmvs2026/pmvs10b-project-oranges/actions) (после сборки CI)
2. Или клонировать и собрать в Android Studio:

git clone https://github.com/fpmi-pmvs2026/pmvs10b-project-oranges.git

## Usage
Действие  Результат
Нажать +	Добавить товар
Нажать чекбокс	Отметить купленный
Долгое нажатие	Удалить товар
Кнопка "Очистить купленные"	Удалить все купленные
Кнопка "Очистить всё"	Удалить весь список
