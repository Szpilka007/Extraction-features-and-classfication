# Klasyfikacja tekstów
OPIS

## Wymagania
- JDK 1.8

## Ekstrakcja cech

- **()**

- **(AOSUKDS)** AvgOfSumUniqueKeywordsDivideSentenceTest

Segregujemy artykuł na zdania, wyliczamy dla każdego zdania ilość unikalnych słów kluczowych i dzielimy ją przez
ilość wszystkich słów w tym samym zdaniu. Dla każdego zdania otrzymamy iloczyn, następnie liczymy średnią z 
wszystkich iloczynów.

- **(PUKIPOA)** ProportionUniqueKeywordsInPartOfArticle

Z artykułu wyciągamy pewien procent słów (20%),
liczymy proporcję ilości unikalnych słów kluczowych występujących w danej części tekstu i
ilości wszystkich słów w danej części tekstu.