
```toc
```

## Slide 0 - Title slide

Hello, let's talk about document understanding and OCR in automation systems context

## Slide 1 - What is a document

Let's first get on the same page about what we consider a document.
In the real world, a document is usually some paper document, be it written or printed.

In the digital world, document is a broader class. A text file or a word document is a document, a table in excel is a document, so are presentations, pdfs, webpages. Even an image can be considered one.

## Slide 2 - Different types of data

In the real world, we use our brain and eyes to understand the documents. (So, only optically, or potentially auditory)

In the digital world we have more ways, and it depends of the document type and format we are trying to parse/understand. 
Some document types and formats are specifically made for computer understanding, some for human understanding, but ideally for both.

Examples of formats specifically made for computers:
- csv - coma separated values. This is analogous to an excel table
- json - Java Script Object Notation. This is like a regular text document, but structured and easily understandable by computers
- different binary formats like sqlite databases

Examples of hybrid formats:
- Websites, in 99% of the cases, websites are html under the hood. It's understandable by nature and so by our automation tools as well. 
- PDFs - PDFs are a widespread document sharing format, while not convenient for editing, they are widespread for the end-user consumption.
- Other mixed content documents (Word documents for example)

Examples of formats specifically made for human understanding:
- Images
- Video files
- Other purely visual documents

## Slide 3 - Getting data

To understand the document means to extract the data from it

### Slide 3.1 - Formats made for computers

There is not much to say here. These formats are already how we want the data to be, these are your best bet when it comes to automation, if you can get the data in one of these formats, it's a win. 

Examples of systems typically producing and providing such formats.

- Form filling systems (e.i. Google forms). Because the forms have a clear structure even with predefined choices in some cases.
- Government document systems. If you are working in a government institution and automating some KYC checks or something similar. You'll most likely be working with a database of structured documents most likely stored in a computer understandable format because the data needs to be 100% correct every time. (I.e: passports, driving licences, card details)
- E-shop systems/reservation systems/ticketing systems

### Slide 3.2 - Hybrid formats

If you can't get your hands on a fully structured format, but you have access to one of the mixed type documents. (For example, the forms your customers fill out are transferred as html files to you) Your next best bet is to extract text from it and try to stucturise it. 

- For html you can use css selectors to extract the relevant pieces of data. 

> [!example] 
> 
> You want to get data from a website, but their api is either really convoluted or does not exist at all or you need a quick proof-of-concept flow.

- In PDFs you can get the text layers by section designators/markers. 

> [!example] 
> 
> Extracting data from a monthly report you get from your bank in pdf.

- In word documents, you can get the text data as well and structure it based on titles/labels

> [!example]
> 
> Extracting data from documentation written in word docs

> [!note] 
> It's best to ask the source to provide the documents with the same structure if possible, so you can anchor to some title for consistency

> [!note] 
> 
> This process is less reliable than just getting the structured data from the get-go, but typically if something breaks here, you start getting fully garbage data (when website/pdf structure changes), so detecting a fail is quite easy 

#### Slide 3.2.1 - Hybrid format parsing. How


- For html you can use html parsing and automation libraries such as BeautifulSoap, Puppeteer, Playwright, Selenium
- For PDFs you can use py-pdf-parser for python, ITextSharp for C#, pdf-parse for Javascript, etc.
- For Word documents you can for example use docx-parser for python, but generally, any xml parsing library will be enough because word documents are archives of xml documents.

### Slide 3.3 - Visual formats


If all you can get is an image, you need need to extract the textual data from it somehow. 

Now, not all images are created equal. 

- If it is a screenshot or a rendering, then just doing OCR (Optical character recognition) should be enough because the fonts are not handwritten and the contrast is good in most of the cases. 
 
> [!example] 
> Screenshots in reports because people either forget that they can copy text or because of laziness.

- If it is a scan of some physical document, you have a little more problems. The images might be rotated/skewed, the contrast might be not ideal and the document might even be handwritten. 

>[!example]
>
 Scans of documents that are only available in physical form.

- If it is a picture of a document, it's even worse. You have even more visual distortions to worry about. A good way to minimize them is to ask the user taking the picture to alight it before sending it.
 
> [!example] 
> - Photos of documents for validation, E.i: Revolut
> - Car licence plate detection
> - Translating a label on a product with google translate

> [!note]
> 
> The OCR process is the least reliable of all 3 discussed options, some human intervention may be required from time-to-time

#### Slide 3.3.1 - Side tangent. What is OCR?

OCR or Optical Character Recognition is the process of detecting text in a image and turning it into actual text

![[Pasted image 20251012181651.png]]

#### Slide 3.3.2 Visual format parsing. How

- For basic OCR from screenshots (useful to use on a PC for example for copying text from somewhere without text selection) you can use Tesseract or Kraken OCR library.
- For scans, photos and handwritten text you may want to preprocess the image removing artifacts, distortions and maybe use a more specialized model. Foe example doctr or ocular.
- If the input images are all the same or specific structure (For example Passports) or class (Old documents). It may be a good option to use specialized models to parse that type of document for more reliably labeled output. (I.e: convolutional neural networks)

##### Technical explanation on how OCR works

1. Before running the recognition itself image cleanup is needed, so step 1 is cleanup.


- De-skewing - making the document perfectly horizontal or vertical.
- Despeckling - removing bright or dark spots, smoothing edges
- Binarization - applying a grayscale (+threshold) filter so the detection step has to work with less variable data and to improve contrast
- Line removal - Removing lines/boxes that are not letters
- Layout analysis or zoning - Identifying columns, paragraphs (important in multi-column layouts and tables)
- Line and word detection - Establishment of a baseline for word and character shapes, separating words as necessary.
- Character isolation or segmentation - (For per-character OCR) - Multiple characters that are connected due to image artifacts must be separated; single characters that are broken into multiple pieces due to artifacts must be connected.
- Normalization of aspect ratio and scale

2. After the cleanup we can do the recognition itself

There are 2 major types of OCR algorithms
- _Matrix matching_ - matching glyphs on a pixel-by-pixel basis to a dataset
- _Feature extraction_ - decomposing glyphs into "features" like lines, closed loops, line direction, and line intersections and matching those. Some classifier model is used (for example k-nearest neighbors algorithm)

Modern OCR software uses *feature extraction* with some tricks to improve the accuracy like using neural networks trained for line recognition instead of single character or automatically splitting a document into sections based on the page layout.

3. After scanning, some post-processing can improve the result even further

We can fix typos by using Levenshtein distance algorithm. Knowing language grammar and having a dictionary of valid words and how often they occur in the target language helps as well.

### Slide 3.4 - Getting data - TLDR

So the TLDR is

- Try to get structured format from the start
- If not possible, try to get a semi-structured one and parse it/structure it, try to get the documents with consistent structure for reliable parsing
- It not possible, use OCR on the images. Try to get images with the least amount of distortions and good contrast, use specialized models for handwritten text.

## Slide 4 - Using the data

After you get your data, you need to sanitize it and make sense of it.

### Slide 4.1 - Data cleanup (post)

- If you got the data in a structured format, it was validated by the party you are getting this from, so no cleanup is needed, you already have everything you need, you can use it in you code.

- If you extracted the data from a hybrid format, some validation may be required to make sure that the values you extracted and roughly what you want. But generally, the data is also not hard to work with.

- And last, if you extracted the data from images, more validation and cleanup is required, for example, making sure that the phone numbers, dates and other validatable values are indeed valid, removing invalid characters/garbage data, fixing typos

### Slide 4.2 - Using the data


After all the validation and cleanup steps, you can finally use your data. 

If the data you got is itself structured or semi-structured and not just text, you can use it as-is or at least parts of it

> [!example]
> - Data extracted from forms with only multiple choice questions, you want to get the number of people who answered yes
> - Data extracted from passports, you want to validate the passport and the users eligibility
> - Data extracted from car licence plates, you want to record the number for violation
> - Data extracted from shop receipts, you want to know how much you spend and on what

For the unstructured data, you may want to use a classifier or even a specialized LLM, depending on what you want to achieve

> [!example] 
> 
> - Reviews: unstructured, you want to get the general type of review (positive, negative, neutral, feature request, spam) -> Use a classifier
> - Emails: unstructured, you want to determine if it's important or not or maybe is it spam or not -> Use a classifier
> - CVs/Emails/Long texts: unstructured, you want a gist of it -> use an LLM

> [!note] 
> 
> When using any kind of unstructured processing, some human intervention is required, classifiers can misclassify, LMMs cam produce nonsense, make up data and get promt-injected

### Slide 4.3 - Using the data - TLDR


- If the data is structured, use it as is
- If the data is semi-structured, use the structured parts as-is
- Use LLMs or classifiers for the unstructured data, but with caution

## Last slide - references

- A Comprehensive Approach to Misspelling Correction with BERT and Levenshtein Distance - https://arxiv.org/abs/2407.17383
- E-commerce Review Classification - https://github.com/dilekkeskin/ecommerce-review-classification
- Classification of Shopify App User Reviews Using Novel Multi Text Features - https://ieeexplore.ieee.org/abstract/document/8988264
- Image Processing Based Scene-Text Detection and Recognition with Tesseract - https://arxiv.org/abs/2004.08079
- MRZ code extraction from visa and passport documents using convolutional neural networks - https://arxiv.org/abs/2009.05489
- Comparing OCR Pipelines for Folkloristic Text Digitization - https://arxiv.org/abs/2507.19092
- Document rectification and illumination correction using a patch-based CNN - https://dl.acm.org/doi/10.1145/3355089.3356563
