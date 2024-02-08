package main;

import lombok.Getter;
import lombok.Setter;

/**
 * Holds all parameters from the pysical printer as an object
 */

public class Printer {

    // hours since last service
    @Getter
    @Setter
    String lastService;  // SH
    @Getter
    @Setter
    String serialNumber;  /// VS
    @Getter
    @Setter
    String pressure;  // HS
    @Getter
    @Setter
    String diaphragmPosition; // uses HS
    @Getter
    @Setter
    String actualVisco; //HS
    @Getter
    @Setter
    String currentCounter; // CC
    @Getter
    @Setter
    String inkLevel; // SL
    @Getter
    @Setter
    String solventLevel; // SL


}