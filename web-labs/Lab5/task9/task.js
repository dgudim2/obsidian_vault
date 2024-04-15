alert(checkHtml(prompt('Введите строку')));

function checkHtml(string) {
    if(string === null || string === undefined){
        return "Неверный ввод";
    }
    let http = string.startsWith("http://");
    let https = string.startsWith("https://");
    let html = string.endsWith(".html");
    let valid = (http || https) && html && string.length > 12 + (https ? 1 : 0);
    return "Начинается с 'http://' или 'https://': " + ((http || https) ? "да" : "нет") + "\n" + "Заканчивается '.html': " + (html ? "да" : "нет") + "\n" + "Валидный адрес: " + (valid ? "да" : "нет");
}