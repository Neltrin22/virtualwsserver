package com.udea.vitualws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class FlightStatusController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/updateFlightStatus")
    public void updateFlightStatus(FlightStatus flightStatus) {
        // Enviar la información de vuelo a todos los suscriptores del canal "/topic/flightStatus"
        messagingTemplate.convertAndSend("/topic/flightStatus", flightStatus);
    }
}
