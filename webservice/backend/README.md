# Backend

Denne mappen inneholder kildekoden som vil fungere som utgangspunkt for å utvikle backenden i får web-applikasjon.

Start med å importere denne mappen inn i intelliJ. IntelliJ vil da automatisk oppdage at dette er
et prosjekt satt opp med byggeverktøyet gradle, og dermed importere det på riktig måte. Kildekoden
finner du da under `./src`. Prosjektet følger standard oppsett for java-applikasjoner, som betyr
at du finner det meste av koden under `./src/main/java/...` mens testene finnes under `./src/test/java/...`.

For å starte applikasjonen kan du navigere til `./src/main/java/no/bekk/sommerskole/Application.class`.
(Tips: Trykk `CTRL + N` i IntelliJ og søk etter `Application`, da skal den dukke opp.). Åpne opp filen
og trykk på `Run \ Run 'Application'` i menylinjen øverst. (Hotkey for dette er `Shift+F10` ). Du vil
da se at det nederst i vinduet dukker opp masse logger. Denne skal slutte med `Started Application in...` som betyr
at applikasjonen nå kjører. Prøv å åpne opp http://localhost:8080/greeting og http://localhost:8080/greeting?name=DittNavn 
i nettleseren, og se om du får opp en liten hilsen. 
