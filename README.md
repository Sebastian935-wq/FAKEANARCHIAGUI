# Fake Anarchia GUI — Fabric 1.21.4

Pierwsza wersja projektu moda client-side. Otwieranie GUI: **N**. Zamykanie: **ESC**.

## Co jest w tej wersji
- Menu w stylu ciemnych, minecraftowych ustawień.
- Zakładki: Klucze, Czeki, Statystyki, Eventówki.
- Przykładowe atrapy oparte na przesłanych screenach: Klucz do epickiej skrzynki, Czek pieniężny, Totem ułaskawienia, Snoczy miecz, Kosa, Wyrzutnia Hydro Klatki.
- Czeki pokazują pełne kwoty: `$1000`, `$10000`, `$100000` (bez skrótów k/M).
- Przedmioty są wyłącznie lokalnymi wizualnymi atrapami. Nie tworzą prawdziwych itemów, nie zmieniają ekwipunku i nie komunikują się z Anarchia.gg.

## Budowanie
Wymagane: Java 21 i Gradle 8.10.x.

W folderze projektu uruchom:

```bash
gradle build
```

Gotowy plik JAR będzie w `build/libs/`.

## Uwaga
To wersja startowa na podstawie sześciu przesłanych przykładów. Zakładka statystyk jest przygotowana, ale celowo nie zawiera wymyślonych danych. Po otrzymaniu kolejnych screenów można uzupełnić dokładne nazwy, opisy, enchanty, wartości i ikony.
