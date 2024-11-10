const queryString = window.location.search; //get url query params
const urlParams = new URLSearchParams(queryString);
let lang = urlParams.get('lang'); //get lang attribute

if(lang == undefined){
  lang = getLang().split('-')[0];
  document.location.search += "?lang="+lang;
}

window.onload = () => {
  loadLocaleStrings();
}

window.onscroll = function () { scroll(); highlightCenter() };

var header = document.getElementById("nav");
var content = document.getElementById("content");
var sticky = header.offsetTop;

function scroll() {
  if (window.pageYOffset > sticky) {
    header.classList.add("sticky");
    content.classList.add("content_sticky");
  } else {
    header.classList.remove("sticky");
    content.classList.remove("content_sticky");
  }
}

window.slider_init = function () {

  var slider_options = {
    $AutoPlay: 0,
    $SlideWidth: 720,
    $ArrowNavigatorOptions: {
      $Class: $JssorArrowNavigator$
    },
    $BulletNavigatorOptions: {
      $Class: $JssorBulletNavigator$,
      $SpacingX: 16,
      $SpacingY: 16
    }
  };

  var slider = new $JssorSlider$("slider", slider_options);

  /*#region responsive code begin*/

  function ScaleSlider() {
    var containerElement = slider.$Elmt.parentNode;
    var containerWidth = containerElement.clientWidth;

    if (containerWidth) {
      slider.$ScaleWidth(containerWidth);
    }
    else {
      window.setTimeout(ScaleSlider, 30);
    }
  }

  ScaleSlider();

  $Jssor$.$AddEvent(window, "load", ScaleSlider);
  $Jssor$.$AddEvent(window, "resize", ScaleSlider);
  $Jssor$.$AddEvent(window, "orientationchange", ScaleSlider);
};

function isElementInViewport(el) {
  var rect = el.getBoundingClientRect();

  return (
    rect.top >= 0 &&
    rect.left >= 0 &&
    rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && /* or $(window).height() */
    rect.right <= (window.innerWidth || document.documentElement.clientWidth) /* or $(window).width() */
  );
}

function highlightCenter() {
  let articles = document.querySelectorAll(".useful_article");

  for (let i = 0; i < articles.length; i++) {
    if (isElementInViewport(articles[i])) {
      articles[i].classList.add("hover");
    } else {
      articles[i].classList.remove("hover");
    }
  }
}

function getLang() {
  if (navigator.languages != undefined)
    return navigator.languages[0]; 
  return navigator.language;
}

function loadLocaleStrings() {
  let localizedElements = document.querySelectorAll(".localized");
  let currentLocale;
  switch (lang) {
    case "ru":
      currentLocale = lang_ru;
      break;
  }
  if (currentLocale != undefined) {
    for (let i = 0; i < localizedElements.length; i++) {
      let value = currentLocale[localizedElements[i].getAttribute("data-key")];
      if(value != undefined){
        localizedElements[i].innerHTML = value;
      }
    }
  }
}