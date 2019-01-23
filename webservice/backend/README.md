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
 <http://localhost:8080/beer.html> i nettleseren, og se om du får opp en liste over øl og data om dem.

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

### Country Repository
Klassen CountryRepository har en getCountries()-funksjon. I denne funksjonen skal dere ta i bruk databasen som ligger i klassen
og gjøre et kall for å hente informasjon om landene i databasen.

##### Code, key, name
Hent listen over alle landene, samt deres landkode, landnøkkel(key) og navn(title). Populer en liste med Country-objekter og returner denne.
Dra gjerne inspirasjon fra lignende operasjoner andre steder i programmet. 
##### Continent
Gjør en join med continents-tabellen for å hente ut navnet på kontinentet landet ligger i og legg dette til objektene
##### Antall øl i landet
Prøv å finne ut hvor mange øl hvert land har, dette kan gjøres direkte i SQL-spørringen om ønskelig

### Filtere på getBeer
I beer repository finnes det en getBeer(Filter)-funksjon. Filteret beskriver et sett med måter å filtrere ølen på.
Skriv om funksjonen, så den har hensyn til filteret. Husk at du kan bygge opp SQL-spørringen enten som konkatinerte strenger,
 eller med builderen.
 
### Get beer details
getBeerDetails henter også øl, men denne gangen ønsker vi å populere med mer informasjon, for bruk i en detalj-side.
Ta utgangspunkt i BeerDetails-klassen og populer feltene i denne klassen for ølen det blir spurt om(id'en).
Her er det viktig å holde tunga rett i munnen, og det kan være nyttig å ta i bruk alias'er i SQL-spørringen("this AS that")

### Set beer details
Vi ønsker også muligheten for å oppdatere informasjonen vi har om ølen.
Desverre mangler vi endepunktet i BeerController

##### Endepunkt 
Implementer et endepunkt for å motta informasjon og skrive til serveren. Hva slags HTTP-header trenger vi?
Endepunktet skal ta et BeerDetailsForm-objekt i http-headeren.
Du kan teste endepunktet ved å kjøre opp front-enden, klikke deg inn på en øl og velge "edit beer" nede til venstre.

##### Oppdatere databasen
Skriv en funksjon i BeerRepository som oppdaterer databasen og kall på denne fra kontrolleren.

