# Tres en Raya Server
# Rubén Abreu González

### Plan
- GET partidas?estado=espera -> partidas en espera -> devuelve la primera partida en espera -> si no hay, POST partidas y poner en espera [pulling]
- POST partidas/{id}/{pos} con la casilla que quieres poner, si no es tu turno -> partidas/{id} con id [pulling]
- cuando creas una partida y esperas al oponente, si le das a cancelar finaliza la partida (cambiar el estado)
- si uno de los oponentes se "sale" (le da al boton salir, no se actualiza la partida), el estado cambia a finalizado
- partidas/{id} para ver la partida [pulling]
- cada vez que haya una actualización en la partida hay un temporizador de 30 segundos y si no responden haces deletePartida