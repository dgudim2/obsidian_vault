window.addEventListener('DOMContentLoaded', (event) => {
    let container = document.getElementById("container");
    let yacht = document.getElementById("yacht");
    let tower = document.getElementById("tower");

    let wave1 = document.getElementById("wave1");
    let wave2 = document.getElementById("wave2");
    let wave3 = document.getElementById("wave3");

    yacht.onclick = animate;

    function animate() {
        yacht.innerText = "Поплыли!";
        yacht.classList.add("animate_borders");
        tower.classList.add("animate_tower");
        setTimeout(function () {tower.classList.add("animate_sails");}, 300);
        setTimeout(function () {wave1.classList.add("animate_wave");}, 400);
        setTimeout(function () {wave2.classList.add("animate_wave");}, 500);
        setTimeout(function () {wave3.classList.add("animate_wave");}, 600);
        setTimeout(function () {container.classList.add("animate_yacht");}, 600);
    }
});




