# Del 2: Frontend

Her finner du koden for frontend-delen av kurset, og oppgavene som hører til.
Teknologi som berøres er:
- JavaScript
- HTML
- CSS/Less
- React
- Webpack

Når det bygges kjører klienten som en _Single Page Application (SPA)_
som kommuniserer med serveren over Rest.

## Installasjon

Dette prosjektet er et klient-prosjekt som er avhengig av serveren fra 
[backend-prosjektet](../backend/README.md). For å kunne vise data i nettleseren 
må serveren kjøre på <http://localhost:8080>.

### NPM install

For å bygge prosjektet må npm og [node](https://nodejs.org/en/) være installert.
For å verifisere om du har NPM, kan du be om versjonsnummeret. 
```console
npm version
```

NPM vil se etter filen `package.json` som beskriver prosjektet. 
Denne inneholder metadata og beskrivelse av avhengigheter.

For å installere alle avhengigheter i prosjektet skriver man 
```console
npm install
```
Alle pakker listet opp i `package.json` under 
`dependencies` (runtime-avhengigheter) 
og `devDependencies` (buildtime-avhengigheter)
vil bli lastet ned fra [npm registry](https://registry.npmjs.org/) og kopiert til 
mappen `node_modules`. Dette skjer også rekursivt med pakker disse pakkene 
er avhengig av. Så denne mappen kan bli nokså stor.

Om du ønsker å legge til nye pakker fra npm registry gjøres dette med 
kommandoen:
```console
npm install underscore --save      # installerer underscore og legger til i dependencies
npm install file-loader --save-dev # installerer file-loader og legger til i devDependencies
npm -g install madge               # installerer pakken madge globalt
                                   # npm uninstall fjerner pakken igjen
```

### NPM script

I `package.json` er det en seksjon som heter `scripts`. Disse kan kjøres av NPM.
I denne pakken er det definert tre script:
- **build**: Bygger prosjektet til en javascript-fil (_bundle_) som er leselig for en nettleser.
  Bundle og alle andre brukte ressursfiler blir kopiert inn i mappen `dist/`.
- **watch**: Gjør det samme, men lytter også på filsystemet, så en endring i en kildefil 
  vil starte et nytt bygg.
- **start**: Bygger løsningen og starter en mini-server som nettleseren kan nå på 
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

Webpack bruker ulike pakker i bygget:
- **Babel**: transpilerer javascript fra nyeste versjon
  til en versjon som kan leses av de fleste nettlesere
- **Css-loader**: kopierer css-filer inn i bundelen. 
- **Less-loader**: kopierer og transpilerer less-filer til css.
- **Dev-server**: kjører opp en lokal webserver i Node og 
  tilbyr bundle og statiske filer (innholder i `/dist`)
  fra <http://localhost:8081>    

### React

[React](https://reactjs.org/) er et rammeverk for å generere html. React-kode er 
ved en konvesjon lagret i `*.jsx`-filer. Jsx er et superset av Javascript.

### Less

Løsningen støtter [Less](http://lesscss.org/) i tillegg til CSS. Less er et superset 
av CSS som webpack vil transpilere til CSS i byggeprosessen.

# Oppgaver
Bygg backend-løsningen og sjekk at løsningen fungerer på <http://localhost:8080>.
Bygg frontend-løsningen (`npm run start`) og åpne nettleseren på <http://localhost:8081>

## Oppvarmingsoppgaver

De første oppgavene er nokså detaljert beskrevet, så det bør være greit å fullføre dem.
Målet med disse er først og fremst å bli kjent med de ulike delene av løsningen og
bli tvunget til å lese litt kode.

Forsøk å forstå hvordan de ulike delne henger sammen og hvordan de er koblet til hverandre.

### Sidetittel
Legg til en tittel på siden som vises av nettleseren (i tab/vindu/snarvei etc.).

- [ ] Rediger `index.html`: i tag-en `<head>` legges en tag for header:

    ```html
    <head>
        <meta charset="UTF-8"/>
        <title>Beer catalogue</title>
        <script src="/main.bundle.js" defer></script>
    </head>
    ```
- [ ] Refresh ↻ nettleseren etterpå for å se endringene.

### Favicon

For å vise et lite ikon ved siden av sidetittelen, 
må vi legge inn en headertag for dette også.

![Øl-ikon](../backend/src/main/resources/public/images/favicon.png)

- [ ] Kopier `favicon.png` fra [backend-koden](../backend/src/main/resources/public/images/)
  (evt en annen fil om du finner noe [sømmelig på nettet](https://www.iconfinder.com/search/?q=beer&price=free))
  til mappen `/static/`.
- [ ] Rediger `index.html`: i tag-en `<head>` legges en tag for header:

  ```html
  <head>
    <meta charset="UTF-8"/>
    <link rel="shortcut icon" type="image/png" href="favicon.png">
    <title>Beer catalogue</title>
    <script src="/main.bundle.js" defer></script>
  </head>
  ```
- [ ] Refresh ↻ nettleseren etterpå for å se endringene.

### Detaljvisning

Når man klikker en rad i tabellen skal man komme til en detaljside. 
For å vise denne må den «plugges inn» i koden.

> Routing er navnet vi bruker på konseptet som knytter url mot visning. Dette skjer både på server og på klient. Det er nesten alltid en del av en web-løsning.
> I dette tilfelle er det et bibliotek (react-router) som hjelper oss med dette.
> En rute [route] er en visning representert ved en url, og som derfor kan nås ved å klikke en lenke.
> Dette kan man bruke for å enkelt lage en navigasjonsmeny (se oppgaven under).

- [ ] I filen `App.jsx`, legg in referanse til siden `/pages/Details.jsx`:
  
  Øverst i filen blant linjene med `import`, legg inn 
  ```jsx harmony
  import Details from 'pages/Details';
  ```
- [ ] I filen `App.jsx`, legg in detaljer-siden som ny rute:
  
  Under rad 11 (`<Route exact path="/" component={Index} />`) legg inn en ny linje
  ```jsx harmony
  <Route path="/beer/:id(\d+)" component={Details} />
  ``` 
- [ ] I filen `/pages/Details.jsx`, legg inn referanse til 
  komponenten `/components/BeerDetails.jsx`:
  Øverst i filen blant linjene med `import`, legg inn
  ```jsx harmony
  import BeerDetails from 'components/BeerDetails';
  ```
- [ ] I filen `/pages/Details.jsx`, sett inn hvor detaljene skal vises på siden:
  Under `h1`-tagen legg inn BeerDetails-komponenten. Den returnerte objektet
  fra `render`-metoden skal være:
  ```jsx harmony
  <React.Fragment>
     <h1>
     Details
     </h1>
     <BeerDetails beer={beer} />
  </React.Fragment>
  ```

## Navigasjonsmeny

Navigasjonsmenyen (som nå vises øverst i siden), er definert i komponenten 
`/components/Navigation.jsx`. Den benytter seg av biblioteket `react-router`, og
navigasjonsstrukturen er definert i `App.jsx`. 
Dette gjør at man kan simulere webside-navigasjon
uten å forlate react-appen.

- [ ] Bytt navn på linken fra "Home" til "List":

  I filen `/components/Navigation.jsx` rediger teksten inne i `<NavLink>`-komponenten. 

For å style navigasjon, så den ser ut som i backend-løsningen, gjør vi følgende:
- [ ] Sett klassenavn på Navigation-komponenten:

  I filen `/components/Navigation.jsx` legg til et attributt
  på `<nav>`-elementet i linje 24 med klassenavn, så det blir:
  ```jsx harmony
    <nav className="main_menu"> 
  ```
- [ ] Erstatt innholdet i `/styles/navigation.less` med 
  ```less
  @import "common";
  
  // Variabler brukt nedenfor
  @menu-color: #9b99dd;
  @text-color: #626262;
  @hover-color: #aeb8b8;
  
  .linkOrPlaceholder {
    font-family: Arial, "Helvetica Neue", Helvetica, sans-serif;
    text-decoration: none;
    position: relative;
    padding: .2em .4em;
    transition: top .2s;
  }
  
  // Disse bestemmer utseendet på elementer som har korresponderende
  // klassenavn.
  .main_menu {
    position: fixed;
    width: 100%;
    height: 1em;
    bottom: 0;
    padding: .2em 3em .2em 3em;
    background-color: @menu-color;
    transition: height .1s ease-in-out;
  
    // Dette bestemmer utseendet når musepekeren peker på elementet.
    // em er en enhet som er relativ til skrifthøyden.
    &:hover {
      height: 2em;
  
      .main_menu_link {
        border: 1px solid @text-color;
        transition: background-color .5s;
        top: .5em;
  
        &:hover {
          background-color: @hover-color;
        }
      }
      .main_menu_placeholder {
        top: .5em;
      }
    }
  
    .main_menu_link {
      cursor: pointer;
      border-bottom: 1px solid @text-color;
      transition: border-top-color 1s;
      .linkOrPlaceholder();
    }
  
    .main_menu_placeholder {
      .linkOrPlaceholder();
    }
  }
  
  ```

## Videre oppgaveforslag
Disse neste oppgavene er mer åpne enn de foregående.
Det er forventet at du må lete litt på nettet etter svar (eller spørre noen i nærheten).
De er ikke listet i en spesiell rekkefølge, så de kan løses i den rekkefølgen
man ønsker.

Oppgavene er å forstå mer som aktivitetsforslag enn som ferdighetstester.
Kreative avvik fra oppgavene applauderes.

### Design
Websidene ser nokså kjedelige slik de er nå.
Ta utgangspunkt i [klientkoden i backend-prosjektet](../backend/src/main/resources/public/)
enten som inspirasjon eller som mal. Prøv å redesigne sidene så de ligner mer på dem.
Antagelig må også den genererte html-en endres noe for å få det til.
Husk at i React-kode må man bruke `className` i stedet for `class`for å angi
klasse på et html-element.

Mer konkrete foraslag:

#### Bakgrunnsbilde
> Alle elementer i html har en bakgrunn-egenskap. Dette kan være en farge eller et bilde.
> Om du vil ha en bakgrunn på hele siden, kan du legge det på body-elementet.

Filer som ligger i mappen `/static/` blir kopiert inn i `/dist/` ved bygg.
Legg bildefiler her om de skal brukes som bakgrunnsbilder eller på andre måter i designet.
Om du skal ha bakgrunnsbilde på hele siden, kan du legge dette i en stil på `<body>`-elementet.

#### Webfonts
> Tradisjonelt var det utfordrende å bruke ulike skrifttyper [fonter] på web,
> fordi man var avhengig av at brukerne hadde font-filene installert på sin maskin.
> Nå har vi muligheten til å ha skrifttyper lastet ned som en del av siden.

Å bruke egne fonter kan gjøres på én av to måter:

1. Referere til en font som ligger på en server.
   For eksempel har [Google fonts](https://fonts.google.com/) en rekke skrifttyper tilgjengelig for bruk.
   Importer en css fra Google inn i et stylesheet. Legg til &file=.css til slutt i url-en:
   ```less
   @import url("https://fonts.googleapis.com/css?family=Pacifico&file=.css");
   ```
   
   Deretter kan fonten brukes i stiler med `font-family: "Pacifico";`

2. Laste inn webfonts med webpack:
   - Kopiere inn fonter (f.eks fra [backend-koden](../backend/src/main/resources/public/fonts/))
     til en egnet mappe (for eksempel `/styles/fonts/`).
   - Legge til file-loader i løsningen for å kopiere filene over:
     ```console
     npm install file-loader --save-dev
     ```
   - Legg til file-loader i `webpack.config.js` under `modules.rules`:
     ```JavaScript
     {
       test: /\.(woff(2)?|ttf|eot|svg)(\?v=\d+\.\d+\.\d+)?$/,
       use: [{
         loader: 'file-loader',
         options: {
           name: '[name].[ext]',
           outputPath: 'fonts/'
         }
       }]
     }
     ```
   - Legge til definisjonen i stylesheet (f.eks `common.less`):
     ```less
     @font-face {
       font-family: "Roboto black";
       src: url("fonts/roboto-black-webfont.woff2") format("woff2");
       font-weight: bold;
       font-style: normal;
     }
     ```

   

### Navigasjon
Navigasjonslinken (øverst i siden) er definert i `/components/Navigation.jsx`og i 
`/styles/navigation.less`.
Navigasjonsrutene er definert i `App.jsx`;

Forsøk å legge til en ekstern link i listen, f.eks til <https://www.bekk.no/>.

### Detalj-visning
Når man klikker på en øl i tabellen kommer man til en detaljvisning. Foreløpig viser
den bare navnet på oppføringen.

Implementer visning av de andre feltene.

Hent inn all informasjonen om ølen fra serveren og vis også denne (inkludert bilde).
Det er en funksjon i `/apiClient/beerRepository.js` som kan brukes for å 
hente detaljer om en enkelt øl.

Gjør det mulig å redigere informasjon om ølen og sende den tilbake til serveren.

### Søk og filter
Det er implementert kun ett filter for å filtrere på navn.
Les koden og se om du kan finne ut hvordan det fungerer.
Implementer filtrering for de andre kolonnene også.

### Paginering
I tabellen vises nå alle radene, noe som kan være litt upraktisk. Implementer kontroller
med paging under tabellen, slik at:
    - Tabellen bar viser de første 20 radene
    - Knapper der man kan gå til de forrige eller de neste 20 radene.
Også fint om man kan velge hvor mange rader det er per side.

### Ølforslag
Lag en visning for `suggestions` som i [backend-koden](http://localhost:8080/suggestions.html).
- Lag en ny rute i `App.jsx`. 
- Lag en ny komponent i `/pages/`.
- Noen av komponentene brukt i `BeerCatalogue.jsx` bør kunne gjenbrukes.

### Enhetstester
Installer et testrammeverk for javascript (for eksempel [Jest](https://jestjs.io/)) og 
lag tester for funksjonaliteten i `/utils/`. Testene skal kunne kjøres med 
`npm run test`.

### Rate Beer

[RateBeer](https://www.ratebeer.com/) er en online database med mengder av data om mengder av øl.
De tilbyr en [API](https://www.ratebeer.com/api-documentation.asp) for å søke i dataene, om man
har en API-nøkkel. Formatet på spørringene er [GraphQL](https://graphql.org/).

Vis detaljer om en øl fra RateBeer på detaljer-siden, eller på en egen side.

Du trenger en API-nøkkel. For å unngå å legge hemmeligheter i kildekoden, 
sett _environment variable_ `rate_beer_api_key` til denne verdien i stedet. Denne plukkes opp av
Webpack og settes i `/config/common.js`. (Du kan også sette verdien rett i denne filen, men da bør du ikke
gjøre en commit av koden.)

På Mac kan du gjøre det på denne måten:
```console
launchctl setenv rate_beer_api_key "api key" #setter variabelen globalt
launchctl getenv rate_beer_api_key           #henter verdien fra variabelen
launchctl unsetenv rate_beer_api_key         #sletter variabelen
``` 

Kall til tjenesten håndteres i `/apiClient/rateBeerClient.js`. Du kan bruke komponenten
`/components/RateBeerInfo.jsx` som utgangspunkt for å vise informasjonen, eller lage din egen
komponent.
