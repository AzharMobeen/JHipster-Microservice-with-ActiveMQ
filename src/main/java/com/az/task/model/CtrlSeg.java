package com.az.task.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "CTRL_SEG")
public class CtrlSeg {

	public CtrlSeg() {
		// TODO Auto-generated constructor stub
	}

    public CtrlSeg(String trnName, String trnver, String uuid, String warehouseId, String clientId, String ctryName, String requestId, String routeId) {
        this.trnName = trnName;
        this.trnver = trnver;
        this.uuid = uuid;
        this.warehouseId = warehouseId;
        this.clientId = clientId;
        this.ctryName = ctryName;
        this.requestId = requestId;
        this.routeId = routeId;
    }

    @XmlElement(name="TRNNAM")
    private String trnName;
    
    @XmlElement(name="TRNVER")
    private String trnver;
    
    @XmlElement(name="UUID")
    private String uuid;
    
    @XmlElement(name="WH_ID")
    private String warehouseId;
    
    @XmlElement(name="CLIENT_ID")
    private String clientId;
    
    @XmlElement(name="ISO_2_CTRY_NAME")
    private String ctryName;
    
    @XmlElement(name="REQUEST_ID")
    private String requestId;
    
    @XmlElement(name="ROUTE_ID")
    private String routeId;

    public String getTrnName() {
        return trnName;
    }

    public void setTrnName(String trnName) {
        this.trnName = trnName;
    }

    public String getTrnver() {
        return trnver;
    }

    public void setTrnver(String trnver) {
        this.trnver = trnver;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCtryName() {
        return ctryName;
    }

    public void setCtryName(String ctryName) {
        this.ctryName = ctryName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return "CtrlSeg{" +
            "trnName='" + trnName + '\'' +
            ", trnver='" + trnver + '\'' +
            ", uuid='" + uuid + '\'' +
            ", warehouseId='" + warehouseId + '\'' +
            ", clientId='" + clientId + '\'' +
            ", ctryName='" + ctryName + '\'' +
            ", requestId='" + requestId + '\'' +
            ", routeId='" + routeId + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CtrlSeg ctrlSeg = (CtrlSeg) o;
        return Objects.equals(trnName, ctrlSeg.trnName) &&
            Objects.equals(trnver, ctrlSeg.trnver) &&
            Objects.equals(uuid, ctrlSeg.uuid) &&
            Objects.equals(warehouseId, ctrlSeg.warehouseId) &&
            Objects.equals(clientId, ctrlSeg.clientId) &&
            Objects.equals(ctryName, ctrlSeg.ctryName) &&
            Objects.equals(requestId, ctrlSeg.requestId) &&
            Objects.equals(routeId, ctrlSeg.routeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trnName, trnver, uuid, warehouseId, clientId, ctryName, requestId, routeId);
    }
}
