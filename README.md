**Giovanni Marcello Verduci**

EPICODE Capstone Project - FS0124

**06/06/2024**

**ARNO**

Advanced Repository for Nursing &Operations

**PRESENTAZIONE**

Da ex studente di Medicina ho sempre conservato un interesse particolare per il settore dell’healthcare e delle scienze in genere. L’idea che voglio proporre è dunque quella di una web app che metta a disposizione del personale clinico di un reparto ospedaliero un sistema di gestione del paziente. Avendo avuto esperienza diretta del lavoro in reparto, conosco in maniera abbastanza approfondita il flusso di dati che accompagna ogni paziente da quando viene inserito in lista d’attesa per il ricovero fino al momento delle dimissioni.

ARNO permetterà dunque al personale ospedaliero di:

- Accettare il ricovero di un nuovo paziente (che proverrà da una lista di attesa in cui è stato idealmente inserito post-visita presso ambulatori o specialisti esterni al reparto);
- Visualizzare eventuali documenti di accompagnamento al ricovero;
- Creare una cartella clinica che accompagnerà ogni paziente per ogni ricovero, contenendo tutti i dati relativi, e che verrà consegnata al paziente a fine degenza;
- Gestire il diario clinico del paziente durante il periodo di ricovero e dunque visualizzare/modificare tutti i vari ordini, consegne, terapie, note ecc.;
- Salvare in un database e poter richiamare, a seconda delle necessità, tutti i dati relativi a pazienti e personale clinico;
- Chiudere il ricovero, con la possibilità di generare foglio di dimissioni e documenti da rilasciare al paziente a partire da layout preimpostati;

**OBIETTIVI**

1. **Scalabilità**: ARNO andrà realizzato in modo quanto più modulare e scalabile possibile: la previsione è quella di continuare a lavorarci anche dopo la fine del corso Epicode, così da ampliarlo per poter essere utilizzato da un’intera struttura clinica invece che da un singolo reparto.
1. **Semplicità**: prevedendone l’uso da parte di personale ospedaliero, che difficilmente ha competenze in campo informatico che vadano oltre le basi dell’utente medio, ARNO dovrà avere un’interfaccia front-end molto semplice e intuitiva, piacevole e user-friendly ma senza elementi grafici distraenti, e consentire di effettuare tutte le operazioni necessarie senza risultare macchinoso all’utente;
1. **Data management**: ARNO dovrà consentire di recuperare in maniera efficiente i dati necessari alla gestione del paziente (ad es. precedenti ricoveri e relativa documentazione per un pazienta che torna in reparto per nuove cure) e gestirli per facilitare le operazioni del personale (ad es. stampare una lista delle terapie da somministrare in un dato giorno per ogni paziente).
1. **Controllo accessi e ruoli utente**: l’app dovrà gestire in maniera sicura l’accesso degli utenti (a maggior ragione dato che andrà a conservare ed esporre dati clinici sensibili) e consentire di assegnare quattro diversi ruoli:
- OSS: sola lettura;
- INFERMIERE: lettura e scrittura limitata (ad es. non potrà prescrivere terapie farmacologiche);
- MEDICO: lettura e scrittura limitata (non ha la possibilità di creare/eliminare/modificare utenti);
- ADMIN: lettura e scrittura.

**SPECIFICHE TECNOLOGICHE**

**Front-end**: Angular, SCSS, Bootstrap; **Back-end**: Java Spring Boot, Spring Data JPA, Spring Security, Maven; **Database**: PostgreSQL; **Gestione file**: Cloudinary.
