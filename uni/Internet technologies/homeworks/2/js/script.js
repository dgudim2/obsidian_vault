const queryString = window.location.search; //get url query params
const urlParams = new URLSearchParams(queryString);
let lang = urlParams.get('lang'); //get lang attribute

if (lang === undefined) {
  lang = getLang().split('-')[0];
  document.location.search = `${document.location.search}?lang=${lang}`;
}

window.onload = () => {
  loadLocaleStrings();
  const swicth_ru = document.getElementById("lang-switch-ru");
  const swicth_en = document.getElementById("lang-switch-en");

  swicth_ru.onclick = () => {
    lang = "ru";
    document.location.search = `${document.location.search.split('?')[0]}?lang=${lang}`
  };

  swicth_en.onclick = () => {
    lang = "en";
    document.location.search = `${document.location.search.split('?')[0]}?lang=${lang}`
  };

  carouselAutoplay();
}

window.onscroll = () => { scroll(); highlightCenter() };

const header = document.getElementById("nav");
const content = document.getElementById("content");
const sticky = header.offsetTop;

function scroll() {
  if (window.scrollY > sticky) {
    header.classList.add("sticky");
    content.classList.add("content_sticky");
  } else {
    header.classList.remove("sticky");
    content.classList.remove("content_sticky");
  }
}

function isElementInViewport(el) {
  const rect = el.getBoundingClientRect();

  return (
    rect.top >= 0 &&
    rect.left >= 0 &&
    rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && /* or $(window).height() */
    rect.right <= (window.innerWidth || document.documentElement.clientWidth) /* or $(window).width() */
  );
}

function highlightCenter() {
  const articles = document.querySelectorAll(".useful_article");

  for (const article of articles) {
    if (isElementInViewport(article)) {
      article.classList.add("hover");
    } else {
      article.classList.remove("hover");
    }
  }
}

function getLang() {
  if (navigator.languages !== undefined)
    return navigator.languages[0];
  return navigator.language;
}

function carouselAutoplay() {
  const carouselButtonNext = document.querySelector(".carousel-control-next")
  setInterval(() => {
    carouselButtonNext.click();
  }, 5000);
}

function loadLocaleStrings() {
  const localizedElements = document.querySelectorAll(".localized");
  let currentLocale;
  switch (lang) {
    case "ru":
      currentLocale = lang_ru;
      break;
  }
  if (currentLocale !== undefined) {
    for (const localizedElement of localizedElements) {
      const value = currentLocale[localizedElement.getAttribute("data-key")];
      if (value !== undefined) {
        localizedElement.innerHTML = value;
      }
    }
  }
}