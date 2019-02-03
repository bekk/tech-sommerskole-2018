# Backend

## Om applikasjonen
Dette er backend-delen av web-applikasjonen vår. Den kjører separat fra frontenden og må
være kjørende for at frontenden skal kunne fungere riktig. Backenden har som ansvar å motta REST-kall
fra frontenden, tolke dem, formulere SQL-spørringer mot databasen og sende tilbake et formatert resultat til frontenden.

#### Struktur
Backenden består av blant annet: 

+   Controllere som er knyttepunktet utover. De mottar REST-kall og sender requesten videre.
+   Repositories som er knyttepunktene mot databasen og håndterer SQL'en
+   Databasen som inneholder all informasjonen om ølen
+   Domeneobjekter
+   Enhetstester for klassene som skal brukes i oppgavene

## Installasjon

#### Importere prosjektet


Denne mappen inneholder kildekoden som vil fungere som utgangspunkt for å utvikle backenden i vår web-applikasjon.


Start med å importere denne mappen inn i intelliJ. IntelliJ vil da automatisk oppdage at dette er
et prosjekt satt opp med byggeverktøyet maven, og dermed importere det på riktig måte. 

Om du får en pop-up nede i høyre hjørne som spør om du ønsker
 å skru på auto-import i maven-prosjektet er det anbefalt å gjøre dette

 
Kildekoden finner du da under `./src`. Prosjektet følger standard oppsett for java-applikasjoner, som betyr
at du finner det meste av koden under `./src/main/java/...` mens testene finnes under `./src/test/java/...`.

#### Maven og avhengigheter
Vi bruker maven for å håndtere alle avhengigheter i backenden. Du kan åpne pom.xml-fila for å se 
hvilke avhengigheter og versjonene vi bruker. Maven kan også kjøre en full kompilering, nedlasting av avhengigheter
og kjøring av testsuiten ved å kjøre "mvn clean install" i rotmappen til backenden

#### Kjøring

For å starte applikasjonen kan du navigere til `./src/main/java/no/bekk/sommerskole/Application.class`.
Åpne opp filen og trykk på `Run \ Run 'Application'` i menylinjen øverst. Du vil da se at det nederst i vinduet dukker 
opp masse logger. Denne skal slutte med `Started Application in...` som betyr at applikasjonen nå kjører. Prøv å åpne opp
 <http://localhost:8080/beer> i nettleseren, og se om du får opp en liste over øl og data om dem.

#### Spring
Backenden vår er bygget rundt et rammeverk kalt Spring, spesifikt Spring Boot. Denne hjelper oss å enkelt sette opp 
REST-endepunktene, ta i bruk Spring sitt inversion of control system og interfacing mot databasen.



## Oppgaver

### The Suggester
Vår produkteier, Herr Von Gluggenheimer har lenge ment at vi burde gi brukerene våre
en mulighet til å få anbefalte øl basert på sine preferanser, og hvor viktig hver preferanse er for dem.
Han har derfor bedt oss om å implementere en slik funksjonalitet i applikasjonen vår.


Filen SuggesterControllerTest inneholder flere tester, basert på de forskjellige funksjonalitetene
som er ønsket i Suggesteren. Den tar i bruk SuggesterController-klassen, som igjen tar i bruk Suggester-klassen.

Den bruker en forhåndslaget liste med øl for å teste på, og tar ikke i bruk ølen som ligger i databasen.

Måten Von Gluggenheimer ser for seg at dette skal fungere er det han kaller en "verdifunksjon".
Den bruker en vektet sum av de forskjellige preferansene for å finne en score for hver øl, og returnere 
top 10 øl basert på denne scoren

S(Beer) = weight_1 * F_1(property_1) + weight_2 * F_2(property_2) ....


weight: brukerens input av vekt, et tall mellom 0 og 1

F: Funksjoner som tar en en property og regner ut score-kontribusjonen til propertien

property: En egenskap til en øl, om det er byen, alkoholinnhold eller bryggeriet
 
#### Oppgaven: 
I Suggester-klassen finner du en tom funksjon som returnerer en tom liste. I SuggesterControllerTest-filen finner du testene for denne oppgaven. Kjør alle testene. 

Som forventet... ingen fungerte. Prøv å returner variabelen "beer" istedet for Emptylist, gikk dette bedre? Jobb deg nedover testene og fyll kravene for hver property.

##### ABV
Scoren skal baseres lineært på hvor nærme en øl sitt ABV-innhold er ABV-propertien


##### Country
Scoren skal baseres på om ølen er brygget i et av landene som blir gitt som input.
Hvor scoren er 1 om ølen er brygget i landet, og 0 om den ikke er det

##### City
Herr Gluggenheimer mener at sannsynligheten for at vi har en øl brygget i nøyaktig brukeren sin ønskede by er liten, 
dette er et problem, da brukeren vil bli skuffet over vårt dårlige utvalg.
Han har dermed ønsket seg en matching-algoritme som git en score basert på hvor likt bynavnet er den ønskede byen. 
Han har lest om en "Levenshein distance"-algoritme i et IT-magasin han synes høres perfekt ut.
Scoren baseres på den negative scoren til en Levensthein-distance algoritme.
https://en.wikipedia.org/wiki/Levenshtein_distance

### SQL-Oppgaver

#### Opg 1: GetBreweries
La oss se hva som er feil: Kjør applikasjonen og åpne websiden på port 8080. Klikk på en tilfeldig øl i listen slik at du ser bildet og detaljene. Nederst på denne siden finner du "Back" "Edit beer" og "Suggestions". Trykk på "Edit beer". Her ser du en mulighet for å endre hvilket bryggeri en øl hører til. Men om du trykker på nedtrekslisten er den tom! La oss fikse dette. 

Nederst på denne samme siden kan du også se noen feilmeldinger. Den ene snakker om countries, den skal vi ta senere. Men den andre som snakker om breweries er vårt første clue. Åpne filen BreweryRepository og se om du kan finne ut hva som mangler i SQL-spørringen. Du kan finne flere clues i klassen Brewery(som vi prøver å fylle) og funksjonen "mapToBreweries" i DBHelpers.


#### Opg 2: GetCountries
Nå som du vet hvordan man henter ut spesifikke kolonner fra databasen og putter dem inn i Java-objekter, la oss se hvordan man heter informasjon fra flere tables i samme spørring. Husker du feilmeldingen om countries fra sist oppgave i "Edit beer"? Den burde fortsatt være der. Vi kan også se at det ikke er noen land i nedtrekslisten.

Opg a) Se på feilmeldingen, den burde si noe om at vi mangler continents. Prøv å finne dette i databasen. Bruk så LEFT JOIN statementet for å hente informasjon fra flere tabeller

Opg b) Countries har også en beerCount verdi som nå mangler. SQL har en fin statement for å telle antall rader man får i svaret(mao, antall øl). Populer beerCount med antall øl i hvert land.

Om alt gikk bra burde du nå ha listen over land.

#### Opg 3: 
La oss gå tilbake til forsiden. Legg merke til at vi nå har en liste med land(oooh og kontinenter!) til høyre vi kan filtrere på. Men om vi trykker på dem skjer det ingenting. Vi har også felter for "Min abv" og "Max abv", endrer vi på disse skjer det heller ingenting. I klassen BeerRepository i funksjonen getBeer har vi en nesten helt ferdig spørring, den fungerer, men den bruker ikke filteret som kommer inn i funksjonen.

Opg a) Legg til mulighet for å filtrere på verdiene max/min abv og countries. Husk at du kan konkatinere SQL-strenger. 

Opg b) Tilbake på forsiden: Legg merke til at du kan trykke øverst i tabellen, på "Name", "Brewery" osv, og det kommer opp piler som peker opp eller ned. Implementer en sortering basert på filteret sin "sort type". Hint: Legg merke til at sortType har en "sql"-verdi, denne kan være nyttig.

Du burde nå kunne filtrere på min/max abv, countries og sortere resultatet på det du ønsker.

#### Opg 4:
Nå har vi lest nok fra databasen. La oss prøve å endre den litt! Husker du Edit Beer siden på hver øl? La oss prøve å submitte en endring. Velg en øl, endre landet og klikk "Submit". Fikk du en feilmelding? 


Opg a) Feilmeldingen sier at "Request method POST not supported". Åpne filen "BeerController". Her er alle HTTP-endepunktene vi har som omhandler øl. Leggg merke til at vi alerede har 2 GET-funksjoner på request-mappingen "beer". Lag et POST-endepunkt, som tar BeerDetailsForm inn som en ModelAttribute og kjør funksjonen "setBeerDetails" i BeerRepository med denne formen.

Opg b) I setBeerDetails har vi en tom SQL-streng. Skriv nå en full update-streng, som bruker verdiene som ligger i formen og populerer den korrekte ølen i databasen. Hint: country_id kommer fra countries-tabellen, visste du at du kan skrive SQL-statements INNI SQL-statements? Whoaaaa.


