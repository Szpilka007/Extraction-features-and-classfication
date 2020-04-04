# Klasyfikacja tekstów
OPIS

## Wymagania
- JDK 1.8

## Ekstrakcja cech

- **(SUKDAW)** SumUniqueKeywordsDivideAmountWords

Suma ilości unikalnych słów kluczowych w całym artykule podzielona przez ilość wszystkich słów w artykule.

- **(SCKDAW)** SumCommonKeywordsDivideAmountWords

Suma ilości pospolitych słów kluczowych w całym artykule podzielona przez ilość wszystkich słów w artykule.

- **(AOSUKDS)** AvgOfSumUniqueKeywordsDivideSentence

Segregujemy artykuł na zdania, wyliczamy dla każdego zdania ilość unikalnych słów kluczowych i dzielimy ją przez
ilość wszystkich słów w tym samym zdaniu. Dla każdego zdania otrzymamy iloczyn, następnie liczymy średnią z 
wszystkich iloczynów.

- **(AOSCKDS)** AvgOfSumCommonKeywordsDivideSentence

Dla każdego ze zdań liczymy ilość pospolitych słów kluczowych występujących w danym zdaniu,
następnie dzielimy każdą przez ogólną ilość słów występujących w tym samym zdaniu.
Ostatecznie liczymy średnią wartość z wcześniej otrzymanych iloczynów.

- **(PUACK)** ProportionUniqueAndCommonKeywords

Proporcja ilości unikalnych słów kluczowych do pospolitych słów kluczowych w tekście.

- **(PUKIPOA)** ProportionUniqueKeywordsInPartOfArticle

Z artykułu wyciągamy pewien procent słów (20%),
liczymy proporcję ilości unikalnych słów kluczowych występujących w danej części tekstu i
ilości wszystkich słów w danej części tekstu.