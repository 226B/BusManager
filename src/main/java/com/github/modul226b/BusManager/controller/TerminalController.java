package com.github.modul226b.BusManager.controller;

import com.github.modul226b.BusManager.dtos.TerminalDto;
import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.BusStation;
import com.github.modul226b.BusManager.model.Terminal;
import com.github.modul226b.BusManager.model.TerminalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/terminal/")
@CrossOrigin(origins = {"*"})
public class TerminalController {
    private DataManager dataManager;
    private BusManager busManager;

    @Autowired
    public TerminalController(DataManager dataManager, BusManager busManager) {
        this.dataManager = dataManager;
        this.busManager = busManager;
    }

    @GetMapping("get")
    public List<TerminalDto> getAll() {
        return this.dataManager.getDataHandler().getAllTerminals()
                .stream().map(terminal -> TerminalDto.toDto(dataManager, terminal))
                .collect(Collectors.toList());
    }

    @GetMapping("get/{terminalId}")
    public TerminalDto getAll(@PathVariable Integer terminalId) {
        return TerminalDto.toDto(dataManager, this.dataManager.getDataHandler().getTerminal(terminalId));
    }

    @GetMapping("type/get")
    public List<TerminalType> getAllTypes() {
        return this.dataManager.getDataHandler().getAllTerminalTypes();
    }

    @GetMapping("type/get/{terminalTypeId}")
    public TerminalType getAllTypes(@PathVariable String terminalTypeId) {
        return this.dataManager.getDataHandler().getTerminalType(terminalTypeId);
    }

    @PostMapping("type/add/")
    public void addType(@PathVariable TerminalType type) {
        if (type.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name darf nicht null sein");
        }

        if (type.getCapacity() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Capacity darf nicht null sein");
        }

        dataManager.getDataHandler().addTerminalType(type);
    }

    @PostMapping("add/")
    public void add(@PathVariable TerminalDto terminalDto) {
        if (terminalDto.getType() == null || terminalDto.getType().getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Type darf nicht null sein.");
        }

        BusStation station = dataManager.getDataHandler().getStationForTerminal(terminalDto.getId());

        if (station == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "StationController " + terminalDto.getStation() + " existiert nicht.");
        }

        Integer id = terminalDto.getId();

        if (id == null) {
            id = dataManager.getDataHandler().getNextTerminalId();
        }

        dataManager.getDataHandler().addTerminal(
                new Terminal(
                        id,
                        terminalDto.getDisplayName(),
                        dataManager.getDataHandler().getTerminalType(terminalDto.getType().getName())
                )
        );
    }
}
