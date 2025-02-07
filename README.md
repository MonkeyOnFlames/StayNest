## StayNest
Group assignment Backend


# Säkerhetsanalysmall för Spring Boot-projekt

## 1. Projektöversikt

- Beskriv kort vad er applikation gör

Det är en hemsida där man kan hyra ett boende eller en tomt. Det finns funktioner för att visa hur miljövänligt det är.

 Lista huvudfunktionaliteterna

Man ska kunna:
- skapa en listing
- se en listing
- visa hur miljövänlig ens listing är
- hyra en listing
- skapa ett konto med email och lösenord
- logga in

 Identifiera vilka användare/roller som finns i systemet

- Admin
- User
- Landlord

## 2. Känslig Data

### 2.1 Identifiera känslig information

Kryssa i det som stämmer för er, fyll på med fler om det behövs.

- [x] Personuppgifter
- [x] Inloggningsuppgifter
- [x] Betalningsinformation
- [x] Annan känslig affärsdata

### 2.2 Dataskyddsåtgärder

Beskriv hur du skyddar den känsliga informationen:

- Kryptering (vilken data krypteras och hur?)

- Lösenord (salta och hasha)

- Säker datalagring

- Säker dataöverföring

## 3. Autentisering & Auktorisering

### 3.1 Inloggningssäkerhet

Kryssa i det som finns med/det ni har hanterar eller ska hantera i er applikation

- [x] Lösenordskrav (längd, komplexitet)
- [x] Hantering av misslyckade inloggningsförsök
- [x] Session hantering
- [x] JWT/Token säkerhet

### 3.2 Behörighetskontroll

Kryssa i det som finns med/det ni har hanterat eller ska hantera i er applikation

- [x] Olika användarnivåer/roller
- [x] Åtkomstkontroll för endpoints
- [x] Validering av användarrättigheter

## 4. API Säkerhet

### 4.1 Input Validering

Kryssa i det som finns med/det ni har hanterar eller ska hantera i er applikation. Kryssa i även om vissa är disabled men skriv inom parentes disabled

- [x] Validering av alla användarinput
- [x] Skydd mot SQL Injection
- [x] Skydd mot XSS 
- [x] Skydd mot CSRF (disabled)

### 4.2 API Endpoints

Kryssa i det som finns med/det ni har hanterat eller ska hantera i er applikation

- [x] HTTPS användning
- [x] Rate limiting
- [x] CORS konfiguration
- [x] Error handling (inga känsliga felmeddelanden)

## 5. Implementerade Säkerhetsåtgärder

Plocka ut det delar ur Spring Security som ni tycker är viktigast, räcker med ett par kod snuttar.
Lista konkreta säkerhetsimplementeringar:

```java
// Exempel på säkerhetskonfiguration
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Din säkerhetskonfiguration här
}
```

## 6. Kvarstående Risker

OBS! Kan fyllas i mot slutet av projektet

- Lista kända säkerhetsrisker som behöver åtgärdas
- Förslag på framtida förbättringar

## Tips för genomförande

1. Börja med att identifiera känslig data i systemet
2. Fokusera på de viktigaste säkerhetsaspekterna först
3. Dokumentera säkerhetsval och motivera dem

## Vanliga Spring Security-komponenter att överväga

- SecurityFilterChain
- UserDetailsService
- PasswordEncoder
- JwtAuthenticationFilter
