# Backend

##Om applikasjonen
Dette er backend-delen av web-applikasjonen vår. Den kjører separat fra frontenden og må
være kjørende for at frontenden skal kunne fungere riktig. Backenden har som ansvar å motta REST-kall
fra frontenden, tolke dem, formulere SQL-spørringer mot databasen og sende tilbake et formatert resultat til frontenden.

####Struktur
Backenden består av blant annet: 

+   Controllere som er knyttepunktet utover. De mottar REST-kall og sender requesten videre.
+   Repositories som er knyttepunktene mot databasen og håndterer SQL'en
+   Databasen som inneholder all informasjonen om ølen
+   Domeneobjekter
+   Enhetstester for klassene som skal brukes i oppgavene

##Installasjon

####Importere prosjektet


Denne mappen inneholder kildekoden som vil fungere som utgangspunkt for å utvikle backenden i vår web-applikasjon.


Start med å importere denne mappen inn i intelliJ. IntelliJ vil da automatisk oppdage at dette er
et prosjekt satt opp med byggeverktøyet maven, og dermed importere det på riktig måte. 

Om du får en pop-up nede i høyre hjørne som spør om du ønsker
 å skru på auto-import i maven-prosjektet er det anbefalt å gjøre dette

 
Kildekoden finner du da under `./src`. Prosjektet følger standard oppsett for java-applikasjoner, som betyr
at du finner det meste av koden under `./src/main/java/...` mens testene finnes under `./src/test/java/...`.

####Maven og avhengigheter
Vi bruker maven for å håndtere alle avhengigheter i backenden. Du kan åpne pom.xml-fila for å se 
hvilke avhengigheter og versjonene vi bruker. Maven kan også kjøre en full kompilering, nedlasting av avhengigheter
og kjøring av testsuiten ved å kjøre "mvn clean install" i rotmappen til backenden

####Kjøring

For å starte applikasjonen kan du navigere til `./src/main/java/no/bekk/sommerskole/Application.class`.
Åpne opp filen og trykk på `Run \ Run 'Application'` i menylinjen øverst. Du vil da se at det nederst i vinduet dukker 
opp masse logger. Denne skal slutte med `Started Application in...` som betyr at applikasjonen nå kjører. Prøv å åpne opp
 <http://localhost:8080/beer> i nettleseren, og se om du får opp en liste over øl og data om dem.

####Spring
Backenden vår er bygget rundt et rammeverk kalt Spring, spesifikt Spring Boot. Denne hjelper oss å enkelt sette opp 
REST-endepunktene, ta i bruk Spring sitt inversion of control system og interfacing mot databasen.



##Oppgaver
