function square(num) {
    return num * num;
}

function sum(a, b) {
    return a + b;
}

function idk(a, b, c) {
    return (a - b) / c;
}

function dayOfWeek(day) {
    switch (day) {
        case 1: return 'Понедельник'
        case 2: return 'Вторник'
        case 3: return 'Среда'
        case 4: return 'Четверг'
        case 5: return 'Пятница'
        case 6: return 'Суббота'
        case 7: return 'Воскресенье'
        default: return 'no such day'
    }
}