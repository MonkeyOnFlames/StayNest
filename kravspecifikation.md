# Kravspecifikation

## Funktionella krav
### Boka en listing
- FK-001: Det ska vara möjligt för en användare att boka en listing på valda datum.
- FK-002: Systemet ska se till så att det inte kan bli dubbelbokningar.

### Notifikationer
- FK-003: Systemet ska skicka en bokningsbekräftelse via epost när en bokning är gjord

## Icke-funktionella krav
### Prestanda
- IFK-001: Applicationen ska kunna hantera minst 200 samtidiga användare utan att  påverka prestanda.

### Säkerhet
- IFK-002: All personlig och akademisk data ska lagras krypterad.
- IFK-003: Applikationen ska följa GDPR för hantering av personuppgifter

### Användaerbarhet
- IFK-004: Användargränssnittet ska vara intuitivt och lättnavigerat

### Skalbarhet
- IFK-005: Koden ska vara skalbar och lättläslig
