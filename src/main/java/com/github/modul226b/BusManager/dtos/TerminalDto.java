package com.github.modul226b.BusManager.dtos;

import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.model.Terminal;
import com.github.modul226b.BusManager.model.TerminalType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class TerminalDto {
    private Integer id;
    private String displayName;
    private TerminalType type;
    private String station;
    private List<Integer> tripIds;

    public static TerminalDto toDto(DataManager dataManager, Terminal terminal) {
        return new TerminalDto(
                terminal.getId(),
                terminal.getDisplayName(),
                dataManager.getDataHandler().getTerminalType(terminal.getTypeName()),
                dataManager.getDataHandler().getStationForTerminal(terminal.getId()).getName(),
                terminal.getTripIds()
        );
    }
}
