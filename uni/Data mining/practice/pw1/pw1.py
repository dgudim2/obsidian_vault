#!/usr/bin/env -S uv run --script

# /// script
# dependencies = [
#   "scrapy",
#   "numpy",
#   "scipy",
#   "seaborn"
# ]
# ///
import json
import logging
from typing import cast
import scrapy
from scrapy.crawler import CrawlerProcess
from scrapy.selector import Selector
from scrapy.utils.project import get_project_settings
from pathlib import Path
import numpy
import seaborn
import matplotlib.pyplot as plt
from scipy import stats

logging.getLogger("matplotlib").setLevel(level=logging.INFO)
logging.getLogger("scrapy").setLevel(level=logging.INFO)
logging.getLogger("PIL").setLevel(level=logging.INFO)

book_json = Path("./books.json")
book_json.unlink(missing_ok=True)

quotes_json = Path("./quotes.json")
quotes_json.unlink(missing_ok=True)


def map_star_rating(classnames: str):
    rating_classname = classnames.split(" ")[-1]

    match rating_classname:
        case "One":
            return 1
        case "Two":
            return 2
        case "Three":
            return 3
        case "Four":
            return 4
        case "Five":
            return 5

    return 0


def preprocess_data[T](data: list[T | None], placeholder: T) -> list[T]:
    if None not in data:
        print("No missing values")
        return cast(list[T], data)

    return [el or placeholder for el in data]


def flatten[T](xss: list[list[T]]):
    return [x for xs in xss for x in xs]


class BooksSpider(scrapy.Spider):
    name = "quotes"
    start_urls = [
        "https://books.toscrape.com/",
    ]

    custom_settings = {
        "FEEDS": {book_json.name: {"format": "json"}},
        "EXTENSIONS": {"scrapy.extensions.closespider.CloseSpider": 500},
        "CLOSESPIDER_PAGECOUNT": 100,
    }

    def parse(self, response):
        for book_element in response.css(".row li:has(.image_container)"):
            yield {
                "book_url": cast(Selector, book_element.css(".image_container a")).attrib["href"],
                "image_url": cast(Selector, book_element.css(".image_container a img")).attrib["src"],
                "title": cast(Selector, book_element.css("h3 a")).attrib["title"],
                "price": cast(Selector, book_element.css(".product_price .price_color::text")).get(),
                "rating": map_star_rating(cast(Selector, book_element.css(".star-rating")).attrib["class"]),
            }

        next_page = response.css('ul.pager li.next a::attr("href")').get()
        if next_page is not None:
            yield response.follow(next_page, self.parse)


class QuotesSpider(scrapy.Spider):
    name = "quotes"
    start_urls = [
        "https://quotes.toscrape.com/",
    ]

    custom_settings = {
        "FEEDS": {quotes_json.name: {"format": "json"}},
        "EXTENSIONS": {"scrapy.extensions.closespider.CloseSpider": 500},
        "CLOSESPIDER_PAGECOUNT": 100,
    }

    def parse(self, response):
        for quote_element in response.css(".quote"):
            yield {
                "quote": cast(Selector, quote_element.css(".text::text")).get(),
                "author": cast(Selector, quote_element.css("small.author::text")).get(),
                "tags": cast(Selector, quote_element.css(".tag::text")).getall(),
            }

        next_page = response.css('ul.pager li.next a::attr("href")').get()
        if next_page is not None:
            yield response.follow(next_page, self.parse)


def scrape_books():
    process = CrawlerProcess(get_project_settings())
    process.crawl(BooksSpider)
    process.start()

    loaded_books_json = json.loads(book_json.read_text(encoding="utf-8"))

    all_ratings = preprocess_data([el["rating"] for el in loaded_books_json], 0)
    all_prices = preprocess_data([float(el["price"].replace("£", "").strip()) for el in loaded_books_json], 0.0)

    print("\n\n-------------------------")

    print(f"            Median of ratings: {numpy.median(all_ratings)}")
    print(f"              Mean or ratings: {numpy.mean(all_ratings)}")
    print(f"              Mode or ratings: {stats.mode(all_ratings).mode}")
    print(f"Standard deviation of ratings: {numpy.std(all_ratings)}")

    seaborn.set_theme()

    seaborn.displot(all_ratings, binwidth=1)
    plt.title("Ratings")
    plt.show()

    seaborn.displot(all_prices, binwidth=5)
    plt.title("Prices")
    plt.show()

    seaborn.scatterplot(x=all_ratings, y=all_prices)
    plt.title("Rating to price correlation")
    plt.show()


def scrape_quotes():
    process = CrawlerProcess(get_project_settings())
    process.crawl(QuotesSpider)
    process.start()

    loaded_quotes_json = json.loads(quotes_json.read_text(encoding="utf-8"))

    all_authors = preprocess_data([el["author"] for el in loaded_quotes_json], 0)
    num_tags_per_quote = preprocess_data([len(el["tags"]) for el in loaded_quotes_json], 0)

    all_tags = preprocess_data(flatten([el["tags"] for el in loaded_quotes_json]), 0)
    unique_tags = list(set(all_tags))
    tag_counts = [len([t for t in all_tags if t == ut]) for ut in unique_tags]

    unique_tags_more_than_once = [tag for i, tag in enumerate(unique_tags) if tag_counts[i] > 1]
    tag_counts_more_than_once = [t for t in tag_counts if t > 1]

    print("\n\n-------------------------")

    print(f"            Median of tag count per quote: {numpy.median(num_tags_per_quote)}")
    print(f"              Mean or tag count per quote: {numpy.mean(num_tags_per_quote)}")
    print(f"              Mode or tag count per quote: {stats.mode(num_tags_per_quote).mode}")
    print(f"Standard deviation of tag count per quote: {numpy.std(num_tags_per_quote)}")

    seaborn.barplot(x=unique_tags_more_than_once, y=tag_counts_more_than_once)
    plt.title("Tags with counts > 1")
    plt.show()
    
    seaborn.displot(num_tags_per_quote)
    plt.title("Tag distribution per quote")
    plt.show()
    
    seaborn.boxplot(x=all_authors, y=num_tags_per_quote)
    plt.title("Tag distribution per author")
    plt.show()
        
    # Note. Most of the graphs here are pretty crap since there is not enough data
    
# scrape_books()
scrape_quotes()
