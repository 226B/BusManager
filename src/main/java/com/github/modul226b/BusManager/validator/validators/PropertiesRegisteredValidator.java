package com.github.modul226b.BusManager.validator.validators;

import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.*;
import com.github.modul226b.BusManager.validator.internal.ValidationResult;
import com.github.modul226b.BusManager.validator.internal.ValidationState;
import com.github.modul226b.BusManager.validator.internal.Validator;

public class PropertiesRegisteredValidator extends Validator<IValidatable> {

    public PropertiesRegisteredValidator(DataManager dataManager, BusManager busManager, TripManager tripManager) {
        super(dataManager, busManager, tripManager);
    }

    @Override
    public ValidationResult validate(IValidatable validation) {
        if (validation instanceof Bus) {
            return checkBus(validation);
        } else if (validation instanceof BusStation) {
            return checkBusStation(validation);
        } else if (validation instanceof BusType) {
            return ValidationResult.create(ValidationState.SUCCESS, "");
        } else if (validation instanceof Depot) {
            return checkDepot(validation);
        } else if (validation instanceof Location) {
            return ValidationResult.create(ValidationState.SUCCESS, "");
        } else if (validation instanceof Terminal) {
            return checkTerminal(validation);
        } else if (validation instanceof TerminalType) {
            return ValidationResult.create(ValidationState.SUCCESS, "");
        } else if (validation instanceof Trip) {
            return checkTrip(validation);
        }else {
            return ValidationResult.create(ValidationState.ERROR, "unknown type " + validation.getClass().getSimpleName());
        }
    }

    private ValidationResult checkBus(IValidatable validatable) {
        Bus bus = (Bus) validatable;
        if (this.getDataManager().getDataHandler().getBusType(bus.getTypeName()) == null) {
            return ValidationResult.create(ValidationState.ERROR, "BusType " + bus.getTypeName() + " muss registiert sein.");
        } else {
            return ValidationResult.create(ValidationState.SUCCESS, "");
        }
    }

    private ValidationResult checkBusStation(IValidatable validatable) {
        BusStation station = (BusStation) validatable;

        StringBuilder builder = new StringBuilder();
        if (this.getDataManager().getDataHandler().getLocation(station.getLocationId()) == null) {
            builder.append("Location ").append(station.getLocationId().toString()).append(" muss registiert sein. \n");
        }
        if (this.getDataManager().getDataHandler().getDepot(station.getDepotName()) == null) {
            builder.append("Depot ").append(station.getLocationId().toString()).append(" muss registiert sein. \n");
        }

        for (Integer terminalId : station.getTerminalIds()) {
            if (this.getDataManager().getDataHandler().getTerminal(terminalId) == null) {
                builder.append("Terminal ").append(terminalId).append(" muss registiert sein. \n");
            }
        }

        if (builder.toString().equalsIgnoreCase("")) {
            return ValidationResult.create(ValidationState.SUCCESS, "");
        } else {
            return ValidationResult.create(ValidationState.ERROR, builder.toString());
        }

    }

    private ValidationResult checkDepot(IValidatable validatable) {
        Depot depot = (Depot) validatable;

        StringBuilder builder = new StringBuilder();

        for (String busName : depot.getBusNames()) {
            if (this.getDataManager().getDataHandler().getBus(busName) == null) {
                builder.append("Bus ").append(busName).append(" muss registiert sein.\n");
            }
        }

        if (builder.toString().equalsIgnoreCase("")) {
            return ValidationResult.create(ValidationState.SUCCESS, "");
        } else {
            return ValidationResult.create(ValidationState.ERROR, builder.toString());
        }

    }

    private ValidationResult checkTerminal(IValidatable validatable) {
        Terminal terminal = (Terminal) validatable;

        StringBuilder builder = new StringBuilder();

        for (int tripId : terminal.getTripIds()) {
            if (this.getDataManager().getDataHandler().getTerminal(tripId) == null) {
                builder.append("Trip ").append(tripId).append(" muss registiert sein.\n");
            }
        }

        if (builder.toString().equalsIgnoreCase("")) {
            return ValidationResult.create(ValidationState.SUCCESS, "");
        } else {
            return ValidationResult.create(ValidationState.ERROR, builder.toString());
        }
    }

    private ValidationResult checkTrip(IValidatable validatable) {
        Trip trip = (Trip) validatable;

        StringBuilder builder = new StringBuilder();
        if (this.getDataManager().getDataHandler().getLocation(trip.getStartId()) == null) {
            builder.append("Location ").append(trip.getStartId()).append(" muss registiert sein.\n");
        }
        if (this.getDataManager().getDataHandler().getLocation(trip.getEndId()) == null) {
            builder.append("Location ").append(trip.getEndId()).append(" muss registiert sein.\n");
        }

        if (builder.toString().equalsIgnoreCase("")) {
            return ValidationResult.create(ValidationState.SUCCESS, "");
        } else {
            return ValidationResult.create(ValidationState.ERROR, builder.toString());
        }
    }

    @Override
    public Class<? extends IValidatable> getType() {
        return IValidatable.class;
    }
}
