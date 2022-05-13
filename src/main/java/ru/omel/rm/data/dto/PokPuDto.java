package ru.omel.rm.data.dto;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
public class PokPuDto implements Serializable {
    private String objName;
    private String objAddress;
    private String typeDevice;
    private String numDevice;
    private String ratio;
    private String date;
    private String meter;

    public PokPuDto() {
    }

    public PokPuDto(String objName
            , String objAddress
            , String numDevice
            , String typeDevice
            , String ratio
            , String date
            , String meter) {
        this.objName = objName;
        this.objAddress = objAddress;
        this.numDevice = numDevice;
        this.typeDevice = typeDevice;
        this.ratio = ratio;
        this.date = date;
        this.meter = meter;
    }

    public String getObjName() {
        return objName;
    }

    public String getObjAddress() {
        return objAddress;
    }

    public String getTypeDevice() {
        return typeDevice;
    }

    public String getNumDevice() {
        return numDevice;
    }

    public String getRatio() {
        return ratio;
    }

    public String getDate() {
        return date;
    }

    public String getMeter() {
        return meter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokPuDto entity = (PokPuDto) o;
        return Objects.equals(this.objName, entity.objName) &&
                Objects.equals(this.objAddress, entity.objAddress) &&
                Objects.equals(this.numDevice, entity.numDevice) &&
                Objects.equals(this.typeDevice, entity.typeDevice) &&
                Objects.equals(this.ratio, entity.ratio) &&
                Objects.equals(this.date, entity.date) &&
                Objects.equals(this.meter, entity.meter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objName, objAddress, numDevice, typeDevice, ratio, date, meter);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "objName = " + objName + ", " +
                "objAddress = " + objAddress + ", " +
                "numDevice = " + numDevice + ", " +
                "typeDevice = " + typeDevice + ", " +
                "ratio = " + ratio +
                "date = " + date +
                "meter = " + meter + ")";
    }
}
