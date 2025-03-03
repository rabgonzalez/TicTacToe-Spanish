# Tres en Raya 
# Rubén Abreu Gónzalez

## Índice
- [Tres en Raya](#tres-en-raya)
- [Rubén Abreu Gónzalez](#rubén-abreu-gónzalez)
  - [Índice](#índice)
    - [Definicion de clases](#definicion-de-clases)
      - [Partida](#partida)
      - [Persona](#persona)
      - [Persona \> Jugador](#persona--jugador)

### Definicion de clases
#### Partida
- **tablero:** string[]([]);
- **jugador1:** Jugador;
- **jugador2:** Jugador;
- **espectadores:** Persona[];
- **turno:** Jugador;
- **estado:** esperando | activa | finalizada;
- **jugadas:** number;
- **ganador:** Jugador;

#### Persona
- **id:** number;
- **nombre:** string;
- **password:** string;

#### Persona > Jugador
- **historial:** Partida[];
- **símbolo:** string (Jugador1= "X | Jugador2: "O");