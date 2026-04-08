#!/usr/bin/env -S uv run --script

# /// script
# dependencies = [
#   "polars",
#   "scikit-learn",
#   "nltk",
#   "xgboost-cpu",
#   "transformers",
#   "torch",
#   "contractions",
#   "plotly",
#   "pandas",
#   "matplotlib"
# ]
#
# ///

import polars as pl
import re
import string
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
import nltk
from nltk.stem import WordNetLemmatizer
import contractions
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn import metrics
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
import xgboost as xgb

import os
import torch
from torch import nn
from torch.optim import AdamW
from torch.utils.data import DataLoader, Dataset
from transformers import BertTokenizer, BertModel, get_linear_schedule_with_warmup
from sklearn.model_selection import train_test_split
from sklearn.metrics import ConfusionMatrixDisplay, accuracy_score, classification_report
from sklearn.manifold import TSNE
import plotly.express as px
import matplotlib.pyplot as plt

nltk.download('stopwords')
nltk.download('wordnet')
nltk.download('punkt_tab')

lemmatizer = WordNetLemmatizer()
stop_words = set(stopwords.words('english'))

def clean_text(text: str):
    text = text.lower()  
    text = re.sub(r'\d+', '', text) 
    text = text.translate(str.maketrans('', '', string.punctuation))  
    text = re.sub(r'\W+', ' ', text) 
    text = re.sub(r'\s+', ' ', text).strip() 
    return text

print(set(pl.read_csv("./cyberbullying_tweets.csv")["cyberbullying_type"]))

category_map = {
    'gender' : 0,
    'ethnicity': 1,
    'religion': 2,
    'age': 3,
    'other_cyberbullying': 4,
    'not_cyberbullying': 5
}

dataset = pl.read_csv("./cyberbullying_tweets.csv")
# .sample(n=5000, shuffle=True, seed=10)
dataset = dataset.with_columns(
    pl.Series("cyberbullying_type", [category_map[cat] for cat in dataset['cyberbullying_type']]).cast(pl.UInt32)
).sort("cyberbullying_type") # https://stackoverflow.com/questions/71996617/invalid-classes-inferred-from-unique-values-of-y-expected-0-1-2-3-4-5-got

print(dataset)

def split_and_clean(text: str):
    text = clean_text(text)
    text_tokens = word_tokenize(text)

    lemmed = (lemmatizer.lemmatize(word) for word in text_tokens)
    fixed = " ".join(contractions.fix(word) for word in lemmed if word not in stop_words)

    return fixed


# https://www.geeksforgeeks.org/nlp/text-preprocessing-for-nlp-tasks/
dataset = dataset.with_columns(pl.Series("tweet_text", [split_and_clean(t) for t in dataset["tweet_text"]]))

print(dataset)

# https://scribe.rip/@abhishekjainindore24/tf-idf-in-nlp-term-frequency-inverse-document-frequency-e05b65932f1d
# https://www.geeksforgeeks.org/machine-learning/understanding-tf-idf-term-frequency-inverse-document-frequency/
# https://www.geeksforgeeks.org/nlp/bag-of-words-vs-tf-idf/
# https://scribe.rip/analytics-vidhya/fundamentals-of-bag-of-words-and-tf-idf-9846d301ff22

X = dataset["tweet_text"]
Y = dataset["cyberbullying_type"]

# https://scribe.rip/@gaurishah143/xg-boost-for-text-classification-9c8b1f8f24aa
# https://codesignal.com/learn/courses/introduction-to-modeling-techniques-for-text-classification/lessons/mastering-random-forest-for-text-classification

X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.3, random_state=42, stratify=Y)

vectorizer = TfidfVectorizer()

X_train_tfid = vectorizer.fit_transform(X_train)
X_test_tfid = vectorizer.transform(X_test)

# https://medium.com/@RobuRishabh/clustering-text-data-with-k-means-and-visualizing-with-t-sne-9bc1fe7d8fed
# https://www.datacamp.com/tutorial/introduction-t-sne

tsne = TSNE(n_components=2, random_state=42)
X_tsne = tsne.fit_transform(vectorizer.fit_transform(X))
fig = px.scatter(x=X_tsne[:, 0], y=X_tsne[:, 1], color=Y)
fig.update_layout(
    title="t-SNE visualization of Cyberbullying dataset",
    xaxis_title="First t-SNE",
    yaxis_title="Second t-SNE",
)
fig.show()

random_forest_model = RandomForestClassifier(n_estimators=100, random_state=42)

random_forest_model.fit(X_train_tfid, Y_train)
y_pred_forest = random_forest_model.predict(X_test_tfid)

accuracy_forest = metrics.accuracy_score(Y_test, y_pred_forest)
print(f"Accuracy of Random Forest: {accuracy_forest * 100:.2f}")
print(metrics.classification_report(Y_test, y_pred_forest))

disp = ConfusionMatrixDisplay.from_estimator(
    random_forest_model,
    X_test_tfid,
    Y_test,
    display_labels=category_map.keys(),
    normalize='true',
)
disp.ax_.set_title("Confusion matrix for random forest")
plt.show()

xgboost_model = xgb.XGBClassifier(n_jobs=-1)
xgboost_model.fit(X_train_tfid, Y_train)

y_pred_xgboost = xgboost_model.predict(X_test_tfid)

accuracy_xgboost = metrics.accuracy_score(Y_test, y_pred_xgboost)
print(f"Accuracy of XgBoost: {accuracy_xgboost * 100:.2f}%")
print(metrics.classification_report(Y_test, y_pred_xgboost))

disp = ConfusionMatrixDisplay.from_estimator(
    xgboost_model,
    X_test_tfid,
    Y_test,
    display_labels=category_map.keys(),
    normalize='true',
)
disp.ax_.set_title("Confusion matrix for xgboost")
plt.show()

# https://scribe.rip/@khang.pham.exxact/text-classification-with-bert-7afaacc5e49b

class TextClassificationDataset(Dataset):
    def __init__(self, texts, labels, tokenizer, max_length):
        self.texts = texts
        self.labels = labels
        self.tokenizer = tokenizer
        self.max_length = max_length

    def __len__(self):
        return len(self.texts)

    def __getitem__(self, idx):
        text = self.texts[idx]
        label = self.labels[idx]
        encoding = self.tokenizer(text, return_tensors='pt', max_length=self.max_length, padding='max_length', truncation=True)
        return {'input_ids': encoding['input_ids'].flatten(), 'attention_mask': encoding['attention_mask'].flatten(), 'label': torch.tensor(label)}


class BERTClassifier(nn.Module):
    def __init__(self, bert_model_name: str, num_classes: int):
        super(BERTClassifier, self).__init__()
        self.bert = BertModel.from_pretrained(bert_model_name)
        self.dropout = nn.Dropout(0.1)
        self.fc = nn.Linear(self.bert.config.hidden_size, num_classes)

    def forward(self, input_ids, attention_mask):
        outputs = self.bert(input_ids=input_ids, attention_mask=attention_mask)
        pooled_output = outputs.pooler_output
        x = self.dropout(pooled_output)
        logits = self.fc(x)
        return logits

def train(model: BERTClassifier, data_loader: DataLoader, optimizer: AdamW, scheduler, device: str):
    model.train()
    for batch in data_loader:
        optimizer.zero_grad()
        input_ids = batch['input_ids'].to(device)
        attention_mask = batch['attention_mask'].to(device)
        labels = batch['label'].to(device)
        outputs = model(input_ids=input_ids, attention_mask=attention_mask)
        loss = nn.CrossEntropyLoss()(outputs, labels)
        loss.backward()
        optimizer.step()
        scheduler.step()

def evaluate(model: BERTClassifier, data_loader: DataLoader, device: str):
    model.eval()
    predictions = []
    actual_labels = []
    with torch.no_grad():
        for batch in data_loader:
            input_ids = batch['input_ids'].to(device)
            attention_mask = batch['attention_mask'].to(device)
            labels = batch['label'].to(device)
            outputs = model(input_ids=input_ids, attention_mask=attention_mask)
            _, preds = torch.max(outputs, dim=1)
            predictions.extend(preds.cpu().tolist())
            actual_labels.extend(labels.cpu().tolist())

    return accuracy_score(actual_labels, predictions), classification_report(actual_labels, predictions)


def predict_sentiment(text: str, model: BERTClassifier, tokenizer: BertTokenizer, device: str, max_length: int = 128):
    model.eval()
    encoding = tokenizer(text, return_tensors='pt', max_length=max_length, padding='max_length', truncation=True)
    input_ids = encoding['input_ids'].to(device)
    attention_mask = encoding['attention_mask'].to(device)

    with torch.no_grad():
        outputs = model(input_ids=input_ids, attention_mask=attention_mask)
        _, preds = torch.max(outputs, dim=1)

        return list(category_map.keys())[preds.item()]


bert_model_name = 'bert-base-uncased'
num_classes = len(category_map)
max_length = 128
batch_size = 16
num_epochs = 2
learning_rate = 2e-5

tokenizer = BertTokenizer.from_pretrained(bert_model_name)
train_dataset = TextClassificationDataset(X_train, Y_train, tokenizer, max_length)
val_dataset = TextClassificationDataset(X_test, Y_test, tokenizer, max_length)
train_dataloader = DataLoader(train_dataset, batch_size=batch_size, shuffle=True)
val_dataloader = DataLoader(val_dataset, batch_size=batch_size)

device = "cuda"

model = BERTClassifier(bert_model_name, num_classes).to(device)

optimizer = AdamW(model.parameters(), lr=learning_rate)
total_steps = len(train_dataloader) * num_epochs
scheduler = get_linear_schedule_with_warmup(optimizer, num_warmup_steps=0, num_training_steps=total_steps)


for epoch in range(num_epochs):
    print(f"Epoch {epoch + 1}/{num_epochs}")
    train(model, train_dataloader, optimizer, scheduler, device)
    accuracy, report = evaluate(model, val_dataloader, device)
    print(f"Validation Accuracy: {accuracy:.4f}")
    print(report)


# test_text = "Hello, test"
# sentiment = predict_sentiment(test_text, model, tokenizer, device)
# print(test_text)
# print(f"Predicted sentiment: {sentiment}")
