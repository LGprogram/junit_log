package com.kaishengit.dto;

import java.util.List;

/**
 * Created by liu on 2017/2/17.
 */
public class DeviceRentDto {

    /**
     * deviceArray : [{"id":"1","name":"拖拉机","unit":"辆","price":"500","num":"1","total":500}]
     * fileArray : ["2077ff9f-817a-4bba-894f-8d2681f7139d.txt"]
     * companyName : 张三的公司
     * tel : 11001
     * rentDate : 2017-02-17
     * linkMan : 张三
     * address : 北京
     * cardNum : 1435255
     * fax : 4324-232
     * totalDays : 11
     */

    private String companyName;
    private String tel;
    private String rentDate;
    private String backDate;
    private String linkMan;
    private String address;
    private String cardNum;
    private String fax;
    private Integer totalDays;
    private List<DeviceArrayBean> deviceArray;
    private List<DocBean> fileArray;

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public List<DeviceArrayBean> getDeviceArray() {
        return deviceArray;
    }

    public void setDeviceArray(List<DeviceArrayBean> deviceArray) {
        this.deviceArray = deviceArray;
    }

    public List<DocBean> getFileArray() {
        return fileArray;
    }

    public void setFileArray(List<DocBean> fileArray) {
        this.fileArray = fileArray;
    }
    public static class DocBean{
        private String sourceName;
        private String newName;

        public String getSourceName() {
            return sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }

        public String getNewName() {
            return newName;
        }

        public void setNewName(String newName) {
            this.newName = newName;
        }
    }
    public static class DeviceArrayBean {
        /**
         * id : 1
         * name : 拖拉机
         * unit : 辆
         * price : 500
         * num : 1
         * total : 500
         */

        private Integer id;//此处id为设备ID
        private String name;
        private String unit;
        private float price;
        private float num;
        private float total;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public float getNum() {
            return num;
        }

        public void setNum(float num) {
            this.num = num;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }
    }
}
