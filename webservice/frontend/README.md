# Frontend

Her finner du koden for frontenddelen av kurset, og oppgavene som hører til.

## Installasjon

Dette prosjektet er et klient-prosjekt som er avhengig av serveren fra 
[backend-prosjektet](../backend/README.md). For å kunne vise data i nettleseren 
må serveren kjøre på <http://localhost:8080>.

### NPM install

For å bygge prosjektet må npm og [node](https://nodejs.org/en/) være installert.
For å verifisere om du har NPM, kan du be om versjonsnummeret. 
```console
npm -v
```

NPM vil se etter filen `package.json` som beskriver prosjektet. 
Denne inneholder metadata og beskrivelse av avhengigheter.

For å installere alle avhengigheter på maskinen skriver man 
```console
npm install
```
Alle pakker listet opp i `package.json` vil bli lastet ned og kopiert til 
mappen `node_modules`. Dette skjer også rekursivt med pakker disse pakkene 
er avhengig av. Så denne mappen kan bli nokså stor.

### NPM script

I `package.json` er det en seksjon som heter scripts. Disse kan kjøres av NPM.
I denne er det definert tre script:
- build: Bygger prosjektet til en javascript-fil (_bundle_) som er leselig for en nettleser.
  Bundle og alle andre brukte ressursfiler blir kopiert inn i mappen `dist/`.
- watch: Gjør det samme, men lytter også på filsystemet, så en endring i en kildefil 
  vil starte et nytt bygg.
- start: Bygger løsningen og starter en mini-server som nettleseren kan nå på 
  <http://localhost:8081>. Den vil oppdatere kildekoden («hot-loading») ved endringer
  uten at det er nødvendig å oppdatere nettleseren.
  
For å kjøre det siste scriptet:
```console
npm run start
```

### Webpack

Webpack har jobben med å sette sammen løsningen til en javascript-fil nettleseren
kan forstå (en «bundle»). Bygget er beskrevet i filen `webpack.config.js`.

Her kan vi også se at _entrypoint_ for bygget er filen `src/index.js`;
Webpack vil lese denne og hente inn alle referanser fra denne til hele løsningen 
er bygget.

### React

[React](https://reactjs.org/) er et rammeverk for å generere html. React-kode er 
ved en konvesjon lagret i `*.jsx`-filer. Jsx er et superset av Javascript.

### Less

Løsningen støtter [Less](http://lesscss.org/) i tillegg til CSS. Less er et superset 
av CSS som webpack vil transpilere til CSS i byggeprosessen.

# Oppgaver
Bygg backend-løsningen og sjekk at løsningen fungerer på <http://localhost:8080>.
Bygg frontend-løsningen (`npm run start`) og åpne nettleseren på <http://localhost:8081>

## Design
Websidene ser nokså kjedelige slik de er nå.
Ta utgangspunkt i [klientkoden i backend-prosjektet](../backend/src/main/resources/public/)
og redesign sidene så de ligner mer på dem.
#### Bakgrunnsbilde

#### Webfonts

#### Navigasjon

