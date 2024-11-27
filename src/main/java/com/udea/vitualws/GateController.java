package com.udea.vitualws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/gates")
@CrossOrigin(origins = "http://localhost:3000")
public class GateController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public GateController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    //Recibir la informacion

    @MessageMapping("/updateGate")
    @SendTo("/topic/gates")
    public GateInfo updateGateInfo(GateInfo gateInfo) throws Exception {
        System.out.println("Actualizado " + gateInfo);
        messagingTemplate.convertAndSend("/topic/gates", gateInfo);

        // Devuelve suscriptores
        return new GateInfo(
                HtmlUtils.htmlEscape(gateInfo.getGate()),
                        HtmlUtils.htmlEscape(gateInfo.getFlightNumber()),
                                HtmlUtils.htmlEscape(gateInfo.getDestination()),
                                        HtmlUtils.htmlEscape(gateInfo.getDepartureTime()),
                                                HtmlUtils.htmlEscape(gateInfo.getStatus())
        );
    }

    //Metodo enviar actulizaciones programaticas
    public void sendUpdate(GateInfo gateInfo) {
        //enviar a todos los subs
        messagingTemplate.convertAndSend("/topic/gates", gateInfo);
    }

    //Metodo actualizar info puerta embarque
    private Map<String, GateInfo> gateData = new ConcurrentHashMap<>();
    @PostMapping("/update")
    public ResponseEntity<String> updateGate(@RequestBody GateInfo gate) throws Exception {
        //Actualizar datos puerta embargque
        gateData.put(gate.getGate(), gate);
        //enviar actu all subs
        messagingTemplate.convertAndSend("/topic/gates", gate);
        return ResponseEntity.ok("Puerta Embarque Actualizada ");
    }



    @GetMapping("/{gateNumber}")
    public ResponseEntity<GateInfo> getGateInfo(@PathVariable String gateNumber) {
        GateInfo gate = gateData.get(gateNumber);
        return ResponseEntity.ok(gate);
    }

    @PostMapping("/status")
    public ResponseEntity<String> updateFlightStatus(@RequestBody FlightStatus status) {
        // Aquí puedes procesar los datos (ej. guardarlos o enviarlos a otro sistema)
        System.out.println("Estado del vuelo recibido: " + status);
        return ResponseEntity.ok("Estado del vuelo actualizado correctamente");
    }
}
