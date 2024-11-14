addEventListener("DOMContentLoaded", () => {
    const body = document.querySelector('#header-bg');
    const template = document.querySelector('.template.shine');


    let size = 'small';
    const createStar = () => {
        const cloned = template.cloneNode(true)
        cloned.id = undefined;
        cloned.setAttribute("style", `top: ${Math.random() * 60}%; left: ${Math.random() * 100}%;`);
        cloned.classList.add(size);
        body.append(cloned);
        setTimeout(() => {
            cloned.remove();
        }, 5000)
    };

    setInterval(() => {
        const rand = Math.random();
        
        if (rand > 0.3) {
            size = 'small';
        } else if (rand > 0.6) {
            size = 'medium';
        } else {
            size = 'large';
        }

        createStar();
    }, 100);
})