package ru.omel.rm.data.entity;

import java.time.LocalDate;
import javax.persistence.Entity;

@Entity
public class ValuesMeters extends AbstractEntity {

    private Integer num;
    private String object;
    private String address;
    private String typeMD;
    private Integer numMD;
    private Integer ratio;
    private LocalDate dateRM;
    private Integer meter;

    public Integer getNum() {
        return num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    public String getObject() {
        return object;
    }
    public void setObject(String object) {
        this.object = object;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTypeMD() {
        return typeMD;
    }
    public void setTypeMD(String typeMD) {
        this.typeMD = typeMD;
    }
    public Integer getNumMD() {
        return numMD;
    }
    public void setNumMD(Integer numMD) {
        this.numMD = numMD;
    }
    public Integer getRatio() {
        return ratio;
    }
    public void setRatio(Integer ratio) {
        this.ratio = ratio;
    }
    public LocalDate getDateRM() {
        return dateRM;
    }
    public void setDateRM(LocalDate dateRM) {
        this.dateRM = dateRM;
    }
    public Integer getMeter() {
        return meter;
    }
    public void setMeter(Integer meter) {
        this.meter = meter;
    }

}
