let my_name = "Данила";
let len = my_name.length;
for (let i = 0; i < len; i++) {
    document.write(my_name[i]+'<br>');
}

let num = '12345';
let n_len = num.length;
let product = 1;
for(let i = 0; i < n_len; i++) {
    product *= num[i];
}
document.write(product);