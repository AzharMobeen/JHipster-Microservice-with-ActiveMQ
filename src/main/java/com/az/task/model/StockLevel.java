package com.az.task.model;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "UC_STOCK_LEVEL_IFD")
public class StockLevel {
	
	@XmlElement(name = "CTRL_SEG")
	private List<CtrlSeg> ctrlSegList;


    public List<CtrlSeg> getCtrlSegList() {
        return ctrlSegList;
    }

    public void setCtrlSegList(List<CtrlSeg> ctrlSegList) {
        this.ctrlSegList = ctrlSegList;
    }

    @Override
    public String toString() {
        return "StockLevel{" +
            "ctrlSegList=" + ctrlSegList +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockLevel that = (StockLevel) o;
        return Objects.equals(ctrlSegList, that.ctrlSegList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ctrlSegList);
    }
}
