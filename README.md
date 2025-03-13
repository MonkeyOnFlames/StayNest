# StayNest
Group assignment Backend


# Säkerhetsanalys för Spring Boot-projekt

## 1. Projektöversikt

- Beskriv kort vad er applikation gör

Det är en hemsida där man kan hyra ett boende eller en tomt. Det finns funktioner för att visa hur miljövänligt det är.


 Lista huvudfunktionaliteterna

Man ska kunna:
- skapa en listing
- se en listing
- visa hur miljövänlig ens listing är
- hyra en listing
- skapa ett konto med ett användarnamn och lösenord
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

```java
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //CORS config
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //CSRF, disable in dev
                //OBS! should not be disabled in production
                .csrf(csrf -> csrf.disable())
                //define URL based rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/landlord/**").hasAnyRole("LANDLORD", "ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "LANDLORD", "ADMIN")
                        .requestMatchers("/api/users/**").permitAll()
                        .requestMatchers("/api/bookings/**").permitAll()
                        .requestMatchers("/api/listings/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        //any other requests the user need to be logged
                        .anyRequest().authenticated()
                )
                //disable session due to jwt statelessness
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //add jwt filter before standard filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//vi använder method security för users, bookings och listings
```

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
- [ ] Skydd mot SQL Injection
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
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //CORS config
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //CSRF, disable in dev
                //OBS! should not be disabled in production
                .csrf(csrf -> csrf.disable())
                //define URL based rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/landlord/**").hasAnyRole("LANDLORD", "ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "LANDLORD", "ADMIN")
                        .requestMatchers("/api/users/**").permitAll()
                        .requestMatchers("/api/bookings/**").permitAll()
                        .requestMatchers("/api/listings/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        //any other requests the user need to be logged
                        .anyRequest().authenticated()
                )
                //disable session due to jwt statelessness
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //add jwt filter before standard filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
```
- @PreAuthorize

```java
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
```
```
    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
            throw new UnauthorizedException("User is not authenticated");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user;
    }
```


## 6. Kvarstående Risker

https://www.canva.com/design/DAGdp2EWCWI/0hALU1ehCQrZlyx02ViUxQ/edit

- Lista kända säkerhetsrisker som behöver åtgärdas
- Kortnummer: 3, 6, 7, 10, 14, 15, 16
  
- Förslag på framtida förbättringar
- 3: Blockera användarkonton efter många anslutningsförsök. Ställ in en stark autentiserings-sätt (tvåfaktorsautentisering).
- 6: Definiera en white list över file extensions som är tillåtna och neka alla andra filtyper.
- 7: Tillåt endast krypterad kommunikation med webbserver, genom att distribuera ett SSL-certifikat och aktivera HSTS-alternativet.
Omdirigera port 80 till 443.
Kontrollera regelbundet vilka krypteringsalgoritmer som stöds av servern för att säkerställa att de alla är uppdaterade enligt bästa säkerhetspraxis.
- 10: Lägga till någon form av Captcha vid inlogg. Maximera antal listings (ex 3st) en användare kan göra per dag.
- 14: Sätt in säkerhet så endast admin kan uppdatera roll på user.
- 15: DTO för user.
- 16: Implementera access logging för all känslig data. Säkerställ att inga personuppgifter skrivs till loggar. Implementera automatisk data retention - radera data
som inte längre behövs. 

## Tips för genomförande

1. Börja med att identifiera känslig data i systemet
2. Fokusera på de viktigaste säkerhetsaspekterna först
3. Dokumentera säkerhetsval och motivera dem

## Vanliga Spring Security-komponenter att överväga

- SecurityFilterChain
- UserDetailsService
- PasswordEncoder
- JwtAuthenticationFilter
