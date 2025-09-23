# StayNest
Group assignment Backend


## 1. Projektöversikt

Det är en hemsida där man kan hyra ett boende eller en tomt. Det finns funktioner för att visa hur miljövänligt det är.


### Lista huvudfunktionaliteterna

Man ska kunna:
- skapa en listing
- se en listing
- visa hur miljövänlig ens listing är
- boka en listing
- skapa ett konto med ett användarnamn och lösenord
- logga in

### Användare/roller som finns i systemet

- Admin
- User
- Landlord

## 2. Vårt fokus

### Refaktor
Vi har valt att göra en refaktor på vår createBooking funktion, då den gör för mycket och är beroende av andra servicar.

### Ny funktionalitet
Vi valde att försöka lägga till bokningsbekräftelse via e-post.

## 3. Hur man bygget och kör koden
Du behöver koppla projektet till en mongoDB databas via en application.properties fil. Sedan går det att bygga och köra koden.

## 4 Översikt
### Vilka principer/mönster har använts och varför
#### Factory pattern
Vi använde factory pattern för att skapa en BookingFactory. Detta då vår BookingService gjorde för mycket saker och var kopplat mot en annan service. 
Detta gjorde också koden bättre, enklare att förstå och underhålla.  

### Chain of Responsibility
Från början hade vi endast metoden validateAndUpdateAvailability i vår createBooking vilket hade alldeles för mycket ansvar. 
De valideringar som fanns i den valde vi att använda i en kedja av validatorer.

### Single Responsibility
Vi såg till att vår createBooking endast har hand om att skapa en bokning, och inget annat.

### Open-Closed
När vi jobbade med våra andra principer/mönster valde vi att jobba på ett sådant sätt att det ska vara lätt att lägga till fler saker som t.ex. valideringar i vår chain, utan att behöva ändra mycket annat.

## 5. Länk till UML diagram
[StayNest UML](https://www.canva.com/design/DAGx6NmYeb8/psLjO5kTwwoR3vei3hCq5Q/edit?ui=e30)