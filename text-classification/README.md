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
ilość wszystkich słów kluczowych w tym samym zdaniu. Dla każdego zdania otrzymamy iloczyn, następnie liczymy średnią z 
wszystkich iloczynów.

- **(AOSCKDS)** AvgOfSumCommonKeywordsDivideSentence

Dla każdego ze zdań liczymy ilość pospolitych słów kluczowych występujących w danym zdaniu,
następnie dzielimy każdą przez ogólną ilość słów występujących w tym samym zdaniu.
Ostatecznie liczymy średnią wartość z wcześniej otrzymanych iloczynów.

- **(AOSUKDP)** AvgOfSumUniqueKeywordsDivideParagraph

Dla każdego z akapitów liczymy ilość unikalnych słów kluczowych występujących w danym akapicie,
następnie dzielimy każdą z liczb przez ogólną ilość słów kluczowy występujących w tym samym akapicie.
Na koniec wyliczamy średnią wartość z wcześniej otrzymanych iloczynów.

- **(AOSCKDP)** AvgOfSumCommonKeywordsDivideParagraph

Dla każdego z akapitów liczymy ilość pospolitych słów kluczowych występujących w danym akapicie,
następnie dzielimy każdą z liczb przez ogólną ilość słów kluczowy występujących w tym samym akapicie.
Na koniec wyliczamy średnią wartość z wcześniej otrzymanych iloczynów.

- **(PUACK)** ProportionUniqueAndCommonKeywords

Proporcja ilości unikalnych słów kluczowych do pospolitych słów kluczowych w tekście.

- **(PUKIPOA)** ProportionUniqueKeywordsInPartOfArticle

Z artykułu wyciągamy pewien procent słów (20%),
liczymy proporcję ilości unikalnych słów kluczowych występujących w danej części tekstu i
ilości wszystkich słów w danej części tekstu.

- **(AKOP)** AmountKeywordsOnParagraph

Liczymy ilość słów kluczowych w każdym z akapitów i wyciągamy z tego średnią.

- **(AWWTNOSDAK)** AmountWordsWithTheNotOnStartDivideAmountKeywords

Liczymy ilość unikalnych słów kluczowych z przedimkiem "THE" i dzielimy przez ilość słów kluczowych

- **(AL)** AverageLevenshtein

Liczymy odległość Lewensztajna dla każdego unikalnego słowa kluczowego w artykule względem
innych słów niekluczowych (zbiór ten nie będzie zawierać duplikatów). Z uzyskanych odległości wyliczamy
średnią.


- **(AWBUK)** AmountWordsBetweenUniqueKeywords

Wyszukujemy słowa kluczowe w całym tekście, następnie dla każdego ze słów wyliczamy
ilość słów kluczowych pomiędzy resztą słów kluczowych.
Mając w tekście zbiór słów kluczowych {A, B, C, D, E, F, G},
którego podzbiór unikalnych słów kluczowych to {B, D, F} wyliczamy kolejno
ilości słów kluczowych pomiędzy każdą kombinacją słów unikalnych.
Z wszystkich kombinacji dla danego unikalnego słowa kluczowego wyciągamy średnią,
następnie z wszystkich średnich dla każdego z unikalnych słów kluczowych
również wyciągamy średnią - jest to ostateczna wartość cechy.