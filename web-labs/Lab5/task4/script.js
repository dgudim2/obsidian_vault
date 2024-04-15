let time = 39;
let quater = Math.ceil((time + 1) / 15);

let weekdays_ru = ['Понедельник', 'Вторник', 'Среда', 'Четверг', 'Пятница', 'Суббота', 'Воскресенье'];
let weekdays_en = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
let lang = 'ru';
let weekdays;
switch (lang) {
    case 'ru':
        weekdays = weekdays_ru;
        break;
    case 'en':
    default:
        weekdays = weekdays_en;
        break;
}