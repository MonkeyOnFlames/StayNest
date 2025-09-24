# Inledning
Kort beskrivning:
Ny funktionalitet: Email notifikationer (bokningsbekräftelse)
Refaktorering: Refactor på BookingService
Refaktorering: Refactor på ListingService.

## Avgränsning: vad ingår och vad ingår inte?

### Email notifikation: 
Vi håller oss till bokningsbekräftelse. Vi tar inte med andra typer av notifikationer, men gör dem möjliga att lägga 
till i framtiden utan att modifiera det vi gjort.

### Refactor på BookingService: 
Vårt mål är att förenkla createBooking så att den inte påverkar någon annan service eller gör för mycket i en metod. 
För att göra BookingService så enkel som möjligt behåller vi endast CRUD och flyttar ut resten. 
Tanken är att låta read, update och delete vara som de är.

### Refactor på ListingService: 
Vårt mål är att förenkla createListing så att den inte är i behov av någon annan service. 
För att göra ListingService så enkel som möjligt behåller vi endast CRUD och flyttar ut resten. 
Tanken är att låta read, update och delete vara som de är.

## Utgångsläge (före / problem)
Refaktorering: beskriv vilka problem ni såg t.ex. svagheter i designen, tecken på dålig struktur, 
brister i ansvarsfördelning eller beroenden m.m. Ge exempel med antingen kodexempel (printscreen) eller enkel förklaring.
### Refactor BookingService
createBooking påverkar en annan service som den inte borde och gör för mycket. 
Därför vill vi förenkla metoden och till exempel skapa en bookingFactory som har hand om hur booking objektet ser ut.

### Refactor ListingService 
createListing är i behov av en annan service som den inte borde. 
Målet är att förenkla metoden till exempel skapa en listingFactory som har hand om hur listing objektet ser ut.

Ny funktionalitet: beskriv behovet/problemet, vilket use case eller krav saknades?

### Email notifikationer (bokningsbekräftelse) 
Vi anser att när man gjort en bokning borde man minst få en bekräftelse via email att bokningen är genomförd.


## Designval (principer/mönster)
Lista vilka principer och/eller mönster ni valt.
Motivera varför just dessa passar för ert problem.
### Single Responsibility 
Implementera denna på Listing- och BookingService så att de inte hanterar för mycket.

### Open-Closed 
Implementera denna i Email notifikationer så att det är möjligt i framtiden att lägga till fler notifikationstyper, 
utan att ändra grundkoden.

### Factory Pattern
För både listing- och booking objekt.
ListingService/BookningService kan inte förutsäga hur objektet ska se ut när den skapas. 
Den behöver en underklass som ska specificera de objekt den skapar.

### Chain of Responsibility 
Vår validateAndUpdateAvailability() är en funktion som validerar för många saker samtidigt. 
Vi tänker att man kan dela upp den i flera valideringsmetoder och kedja ihop dem. 
Varje validator kollar en specifik sak och skickar vidare till nästa om allt är ok, eller kasta exception om något är fel.

## Koppla till UML, visa t.ex. klassdiagram före/efter eller sekvensdiagram för nytt beteende.
Se canva (länk i readMe).

## Lösning (efter)
Beskriv den nya designen: hur ser ansvars- och rollfördelningen ut?

BookingFactory skapar ett bokningsobjekt med hjälp av hjälpklasserna ConvertToBookingDTO, UpdateAvailability och 
CalculateTotalAmount och skickar det till BookingService som skapar bokningen.

RequestValidator är huvudklassen för våra validatorer. 
Den ser till att det är möjligt att välja nästa validator och har metoden för om en validator blir godkänd eller inte.

ValidatorProcessor skapar en kedja av de olika validatorerna: 
BookingValidator → DateValidator → AvailabilityValidator → ConflictValidator → AuthorizationValidator. 
Om någon av dem kastar ett fel, bryts kedjan.

BookingValidator använder ValidateBooking för att processa om all information som bokningsobjektet behöver ha med när en bokning görs.

DateValidator använder ValidateDate för att processa om datumen i bokningen är i det förflutna och om startdatumet är före slutdatumet.

AvailabilityValidator använder ValidateAvailability för att processa om listingen har en Availability som matchar start- och sludatum.

ConflictValidator använder ValidateConflict för att se om det finns andra bokningar på den listingen för bokningsspannet.

AuthorizationValidator använder ValidateAuthorization för att se om användaren är inloggad.

## Förklara hur principer/mönster har implementerats.

Vi har ett factory pattern för att skapa bokningsobjekt.

Chain of Responsibility använder vi för vår validering av ett bokningsobjekt.

Vår createBoooking har nu bara hand om att skapa en bokning.

## Analys & konsekvenser (gemensam)
Hur förändringen förbättrade koden? t.ex. har förändringen gjort koden enklare att förstå? Har den gjort det enklare att underhålla koden?

Koden är mer övergriplig och det är lättare att lägga till fler valideringar och utöka bokningar med mer information. 
Det är lättare att hantera fel och underhålla koden.

## Eventuella nackdelar  t.ex. ökad komplexitet, fler klasser, etc. och varför ni tycker det är värt det.
Ni kan ta stöd av UML diagram om kodexempel.

Det är mycket mer klasser (se canva). Men det gör att det är enklare att förstå vad som händer. 
Det är enklare att lägga till mer valideringar eller utöka bokningar nu. Koden ser också mycket snyggare och bättre ut.
