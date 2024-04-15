let checkAB = (a, b) => {
    return a === b ? 0 : ((parseFloat(a) > parseFloat(b)) ? 1 : -1);
}

alert(checkAB(prompt('Введите a'), prompt('Введите b')));