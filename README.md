# BusManager
Der Bus Manager ist eine Springbootapplikation für das Verwalten von mehreren Busstationen und den Reisen zwischen diesen Stationen.

## Grundsätzliche Funktionen
### Reisen
Es soll möglich sein, mit der Applikation eine Reise zu erfassen mit der man von einer Busstation zu einer anderen Busstation kommt.
Um eine solche Reise zu erfassen, soll der Benutzer, der diese erfasst nur beide Stationen, eine Zeit (Ankunft oder Abfahrt) und die Anzahl an Leuten angeben müssen. Aus diesen Daten muss dann automatisch der richtige Bus, am richtigen Ort, mit der richtigen Kapazität ausgesucht und reserviert werden. 

Um eine Reise zu erfassen braucht man folgende Attribute: 
- Start Station
- Endstation
- Ankunftszeit (or Abfahrtszeit)
- Abfahrtszeit (or Ankunftszeit)
- Anzahl Menschen

Alle anderen Attribute (der Bus und die Start- und Endgates) müssen berechnet werden können.

### Stammdaten erfassen
 Es sollte möglich sein mithilfe eines einfachen Formulars möglich sein, einige Stammdaten zu erfassen. Die folgenden Objekte sollten mit einem Formular erstellbar sein:
#### Depot
- name

#### BusStation
- name
- x
- y
- depotName

#### BusType
- name
- capacity
- recoveryTime
- maxRange
- distancePer100

#### Bus
- name
- type name
- depot name

#### GateType
- name
- capacity

#### Gate
- stationName
- Gate Type Name
- gate id



