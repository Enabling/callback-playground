package be.enabling.callbackplayground.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.enabling.callbackplayground.dto.CallbackDataDTO;
import be.enabling.callbackplayground.service.CallbackDataSaver;

@RestController
public class CallbackDataController {

    @Autowired
    private CallbackDataSaver saver;

    public CallbackDataController() {}
    public CallbackDataController(CallbackDataSaver saver) {
        this.saver = saver;
    }

    @RequestMapping(path = "/callbacks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CallbackDataDTO>> getAll(){
        return ResponseEntity.ok(saver.getAllCallbackData());
    }
}
