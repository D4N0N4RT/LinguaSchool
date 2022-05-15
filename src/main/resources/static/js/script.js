function clicker(elem){ // Функция открытия мобильного меню
    let str = elem.firstChild;
    let menu = document.querySelector('.menu-overlay'); // Берем блок меню
    let attr=menu.getAttribute('id'); // Проверяем id блока
    if (attr === null) { // Если id='active' нет у меню
        str.innerText = "close"; // Меняем иконку меню
        menu.setAttribute('id','active'); // Добавляем id, благодаря чему меняется стиль меню
    } else { // Иначе
        menu.removeAttribute('id'); // Убираем id
        str.innerText = "menu"; // Меняем иконку меню
    }
}