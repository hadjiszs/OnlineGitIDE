package com.multimif.controller;

import com.multimif.dao.EntityFactoryManager;
import com.multimif.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Controller par défaut de l'API
 */
@RestController
@RequestMapping("/")
public class APIController {

    /**
     * Page par défault de l'API
     * @return Un json contenant le statut de l'API
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<String> get(){
        return new ResponseEntity<>(JsonUtil.convertStringToJson("API","OK"), HttpStatus.OK);
    }

    /**
     * Initialise la persistance de l'API
     */
    @PostConstruct
    public void init() {
        EntityFactoryManager.persistance();
    }

    /**
     * Ferme la persistance de l'API
     */
    @PreDestroy
    public void destroy(){
        EntityFactoryManager.close();
    }
}