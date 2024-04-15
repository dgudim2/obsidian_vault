let numArray = [5, 6, 7, 8];
let len = numArray.length;
let product = 1;
for (let i = 0; i < len; i++) {
    product *= numArray[i];
}

let obj = {
    'Минск': 'Беларусь',
    'Москва': 'Россия',
    'Киев': 'Украина'
}

for (key in obj) {
    document.write(key + " - это " + obj[key] + "<br>");
}