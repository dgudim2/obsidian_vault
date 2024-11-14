// Example starter JavaScript for disabling form submissions if there are invalid fields
addEventListener("DOMContentLoaded", () => {

    const forms = document.querySelectorAll('.needs-validation')
    const dialog = document.querySelector('#some-parts-are-invalid')
    const i_love_cats = document.querySelector('#i-love-cats')
    const the_cat = document.querySelector('#vibing_cat')

    i_love_cats.addEventListener('change', function () {

        for (let opacity = 0; opacity < 200; opacity += 1) {
            setTimeout(() => { the_cat.style.opacity = (this.checked ? opacity : (200 - opacity)) / 2000; }, 100 * (opacity / 80 + 0.8))
        }

    });

    for (const form of forms) {
        form.addEventListener('submit', event => {
            event.preventDefault()
            event.stopPropagation()

            if (!form.checkValidity()) {
                form.classList.add('was-validated')
                dialog.showModal()
                return
            }

            window.location.assign("index.html");

        }, false)
    }
})