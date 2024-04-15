let intitialMoney = prompt('Введите сумму зарплаты');
intitialMoney *= 1.15;
confirm('Премия 15%, на руки ' + moneyToStr(intitialMoney));
intitialMoney *= 0.9;
confirm('Налоги 10%, итого ' + moneyToStr(intitialMoney));
intitialMoney -= 90;
confirm('Закупился транзисторами на 90 рублей, итого ' + moneyToStr(intitialMoney));
intitialMoney /= 2;
confirm('Жена забрала половину на косметику, осталось ' + moneyToStr(intitialMoney));

function moneyToStr(money) {
    rub = Math.floor(money);
    left = money - rub;
    if(left === 0){
        return rub + " рублей"
    }
    return rub + " рублей, " + Math.floor(left * 100) + " копеек";
}