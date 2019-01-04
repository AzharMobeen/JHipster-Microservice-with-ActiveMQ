package com.az.task.domain;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "UC_STOCK_LEVEL_IFD")
public class UCStockLevelIFD {
	
	@XmlElement(name = "CTRL_SEG")
	private List<ControlSegment> ctrlSegList;

	public List<ControlSegment> getCtrlSegList() {
		return ctrlSegList;
	}

	public void setCtrlSegList(List<ControlSegment> ctrlSegList) {
		this.ctrlSegList = ctrlSegList;
	}

    @Override
    public String toString() {
        return "UCStockLevelIFD{" +
            "ctrlSegList=" + ctrlSegList +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UCStockLevelIFD that = (UCStockLevelIFD) o;
        return Objects.equals(ctrlSegList, that.ctrlSegList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ctrlSegList);
    }
}
